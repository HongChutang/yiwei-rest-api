package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import person.practice.repository.GoodsRepository;

/**
 * Created by Evan Hung on 2016/12/27.
 */
@RestController
public class DeleteGoodsController {
    private GoodsRepository goodsRepository;

    @Autowired
    public DeleteGoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/admin/goods/{goodsName}", method = RequestMethod.DELETE)
    public void deleteGoods(@PathVariable String goodsName) {
        goodsRepository.delete(goodsName);
    }
}
