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
public class ServeGoodsNamesController {
    private GoodsRepository goodsRepository;

    @Autowired
    public ServeGoodsNamesController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/goods/names/{type}", method = RequestMethod.GET)
    public String[] getGoodsNames(@PathVariable("type")String type) {
        List<Goods> goodsList;
        if (type.equals("all")) {
            goodsList = goodsRepository.findAll();
        } else {
            goodsList = goodsRepository.findByType(type);
        }
        String[] strings = new String[goodsList.size()];
        for (int i=0; i<goodsList.size(); i++) {
            strings[i] = goodsList.get(i).getGoodsName();
        }
        return strings;
    }
}
