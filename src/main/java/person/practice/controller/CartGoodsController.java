package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import person.practice.model.CartGoods;
import person.practice.model.Goods;
import person.practice.model.UserInfo;
import person.practice.repository.CartGoodsRepository;
import person.practice.repository.GoodsRepository;
import person.practice.repository.UserInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class CartGoodsController {
    private CartGoodsRepository cartGoodsRepository;
    private GoodsRepository goodsRepository;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public CartGoodsController(CartGoodsRepository cartGoodsRepository, GoodsRepository goodsRepository, UserInfoRepository userInfoRepository) {
        this.cartGoodsRepository = cartGoodsRepository;
        this.goodsRepository = goodsRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @RequestMapping(value = "/user1/{email}/cart", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public void createCartGoods(@RequestBody CartGoods cartGoods, @PathVariable String email) {
                // 获取展示区该商品并判断库存是否足够
       Goods existGoods = goodsRepository.findOne(cartGoods.getGoodsName());
        if (existGoods.getCartStock() < cartGoods.getAmount()) {
            throw new IllegalArgumentException("库存不足！");
        }
        //获取用户信息并判断用户信息正确性
        UserInfo userInfo =  userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            throw new IllegalArgumentException("获取用户信息失败！");
        }
        //遍历当前用户下的列表判断是否购物车中已有 有则更新数量
        List<CartGoods> cartGoodsList = cartGoodsRepository.findByUserEmail(email);
        boolean flag = false;
        if (cartGoodsList != null) {
            for (CartGoods cartGoods1 : cartGoodsList) {
                if (cartGoods1.getGoodsName().equals(cartGoods.getGoodsName())) {
                    cartGoods1.setAmount(cartGoods1.getAmount() + cartGoods.getAmount());
                    cartGoodsRepository.save(cartGoods1);
                    flag = true;
                }
            }
        }
        if (flag == false) {
            cartGoods.setUserEmail(email);
            cartGoodsRepository.save(cartGoods);
        }

        //更新展示区商品库存
        existGoods.setCartStock(existGoods.getCartStock()-cartGoods.getAmount());
        goodsRepository.save(existGoods);
    }

    @RequestMapping(value = "/user1/{email}/cart", method = RequestMethod.GET)
    public List<CartGoods> serveCartGoods(@PathVariable String email) {
        return cartGoodsRepository.findByUserEmail(email);
    }

    @RequestMapping(value = "/user1/{email}/cart/{goodsName}", method = RequestMethod.DELETE)
    public void deleteCartGoods(@PathVariable String email, @PathVariable String goodsName) {
        Goods goods = goodsRepository.findOne(goodsName);
        List<CartGoods> cartGoodsList = cartGoodsRepository.findByUserEmail(email);
        CartGoods target = null;
        for (CartGoods cartGoods : cartGoodsList) {
            if (cartGoods.getGoodsName().equals(goodsName)) {
                target = cartGoods;
            }
        }
        goods.setCartStock(goods.getCartStock()+target.getAmount());
        cartGoodsRepository.delete(target);
    }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
