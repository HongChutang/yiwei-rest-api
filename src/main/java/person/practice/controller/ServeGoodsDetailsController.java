package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.Goods;
import person.practice.repository.GoodsRepository;

import java.util.List;

/**
 * Created by Evan Hung on 2016/12/27.
 */
@RestController
public class ServeGoodsDetailsController {
    private GoodsRepository goodsRepository;

    @Autowired
    public ServeGoodsDetailsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/goods/details/{goodsName}", method = RequestMethod.GET)
    public Goods getGoodsDetails(@PathVariable("goodsName")String goodsName) {
        Goods existGoods = goodsRepository.findOne(goodsName);
        if (existGoods == null) {
            throw new IllegalArgumentException("没有这件商品!");
        }
        return existGoods;
    }
}
