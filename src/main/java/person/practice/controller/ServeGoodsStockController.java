package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.Stock;
import person.practice.repository.GoodsRepository;


/**
 * Created by Evan Hung on 2016/12/27.
 */
@RestController
public class ServeGoodsStockController {
    private GoodsRepository goodsRepository;

    @Autowired
    public ServeGoodsStockController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/goods/cartstock/{goodsName}", method = RequestMethod.GET)
    public Stock getCartStock(@PathVariable String goodsName) {
        Stock stock = new Stock();
        stock.setCartStock(goodsRepository.findOne(goodsName).getCartStock());
        return stock;
    }

    @RequestMapping(value = "/goods/stock/{goodsName}", method = RequestMethod.GET)
    public Stock getStock(@PathVariable String goodsName) {
        System.out.print(goodsName+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Stock stock = new Stock();
        stock.setStock(goodsRepository.findOne(goodsName).getStock());
        return stock;
    }
}
