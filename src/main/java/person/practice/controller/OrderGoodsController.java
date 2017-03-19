package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import person.practice.helper.GetCurDateHelper;
import person.practice.model.CartGoods;
import person.practice.model.Goods;
import person.practice.model.OrderGoods;
import person.practice.model.UserInfo;
import person.practice.repository.CartGoodsRepository;
import person.practice.repository.GoodsRepository;
import person.practice.repository.OrderGoodsRepository;
import person.practice.repository.UserInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class OrderGoodsController {
    private OrderGoodsRepository orderGoodsRepository;
    private GoodsRepository goodsRepository;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public OrderGoodsController(OrderGoodsRepository orderGoodsRepository, GoodsRepository goodsRepository, UserInfoRepository userInfoRepository) {
        this.orderGoodsRepository = orderGoodsRepository;
        this.goodsRepository = goodsRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @RequestMapping(value = "/user1/{email}/order", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public void createCartGoods(@RequestBody OrderGoods orderGoods, @PathVariable String email) {
        // 获取展示区该商品并判断库存是否足够
        Goods existGoods = goodsRepository.findOne(orderGoods.getGoodsName());
        if (existGoods.getStock() < orderGoods.getAmount()) {
            throw new IllegalArgumentException("库存不足！");
        }
        //获取用户信息并判断用户信息正确性
        UserInfo userInfo =  userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            throw new IllegalArgumentException("获取用户信息失败！");
        }
        //保存订单信息
        orderGoods.setDate(GetCurDateHelper.getCurDate());
        orderGoods.setUserEmail(email);
        orderGoodsRepository.save(orderGoods);

        //更新展示区商品库存
        existGoods.setCartStock(existGoods.getStock() - orderGoods.getAmount());//有问题
        existGoods.setStock(existGoods.getStock() - orderGoods.getAmount());
        goodsRepository.save(existGoods);
    }

    @RequestMapping(value = "/user1/{email}/order", method = RequestMethod.GET)
    public List<OrderGoods> serveCartGoods(@PathVariable String email) {
        return orderGoodsRepository.findByUserEmail(email);
    }

    @ExceptionHandler
    public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
