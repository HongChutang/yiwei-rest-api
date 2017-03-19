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
public class ServeGoodsController {
    private GoodsRepository goodsRepository;

    @Autowired
    public ServeGoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/goods/{type}", method = RequestMethod.GET)
    public List<Goods> getGoods(@PathVariable String type) {
        return goodsRepository.findByType(type);
    }
}
