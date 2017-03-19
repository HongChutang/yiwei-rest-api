package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import person.practice.helper.GetCurDateHelper;
import person.practice.model.CartGoods;
import person.practice.model.Goods;
import person.practice.model.OrderGoods;
import person.practice.repository.CartGoodsRepository;
import person.practice.repository.GoodsRepository;
import person.practice.repository.OrderGoodsRepository;
import person.practice.repository.UserInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Evan Hung on 2016/12/29.
 */
@RestController
public class SumUpController {
    private OrderGoodsRepository orderGoodsRepository;
    private GoodsRepository goodsRepository;
    private UserInfoRepository userInfoRepository;
    private CartGoodsRepository cartGoodsRepository;

    @Autowired
    public SumUpController(CartGoodsRepository cartGoodsRepository, OrderGoodsRepository orderGoodsRepository, GoodsRepository goodsRepository, UserInfoRepository userInfoRepository) {
        this.orderGoodsRepository = orderGoodsRepository;
        this.goodsRepository = goodsRepository;
        this.userInfoRepository = userInfoRepository;
        this.cartGoodsRepository = cartGoodsRepository;
    }

    @RequestMapping(value = "/user1/{email}/cart", method = RequestMethod.PUT, headers = {
            "Content-Type=application/json"})
    public void sumUp(@RequestBody CartGoods cartGoods, @PathVariable String email) {
        //展览区购物车库存恢复
        String goodsName = cartGoods.getGoodsName();
        Goods goods = goodsRepository.findOne(goodsName);
        goods.setCartStock(goods.getStock()-cartGoods.getAmount());//有问题
        goodsRepository.save(goods);

        //从购物车中删除
        List<CartGoods> cartGoodsList = cartGoodsRepository.findByUserEmail(email);
        CartGoods target = null;
        for (CartGoods cartGoods1 : cartGoodsList) {
            if (cartGoods1.getGoodsName().equals(goodsName)) {
                target = cartGoods1;
            }
        }
        cartGoodsRepository.delete(target);

        //开始工作
        if (goods.getStock() >= cartGoods.getAmount()) {//库存够
            if (cartGoods.getChecked() == true) {//要下单的
                    //生成订单
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setAmount(cartGoods.getAmount());
                orderGoods.setDate(GetCurDateHelper.getCurDate());
                orderGoods.setGoodsName(cartGoods.getGoodsName());
                orderGoods.setImage(cartGoods.getImage());
                orderGoods.setPrice(cartGoods.getPrice());
                orderGoods.setUserEmail(email);
                orderGoodsRepository.save(orderGoods);

                    //更新展示区商品库存
                goods.setStock(goods.getStock() - cartGoods.getAmount());
                goodsRepository.save(goods);
                } else {//不下单的
                    //更新购物车
                    cartGoods.setUserEmail(email);
                    cartGoodsRepository.save(cartGoods);
                }

            } else {//结算时库存不够了 回存到购物车并抛出异常
            cartGoods.setUserEmail(email);
            cartGoodsRepository.save(cartGoods);
            throw new IllegalArgumentException("库存不足！");
            }
        }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
