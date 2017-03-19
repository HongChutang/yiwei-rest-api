package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.Goods;
import person.practice.repository.GoodsRepository;

/**
 * Created by Evan Hung on 2016/12/27.
 */
@RestController
public class UpdateGoodsController {
    private GoodsRepository goodsRepository;

    @Autowired
    public UpdateGoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/admin/goods", method = RequestMethod.PUT, headers = {
            "Content-Type=application/json"})
    public void updateGoods(@RequestBody Goods goods) {
                //对比新旧信息进行更新
        Goods existGoods = goodsRepository.findOne(goods.getGoodsName());
        if (goods.getPrice() != existGoods.getPrice()) {
            existGoods.setPrice(goods.getPrice());
        }
        if (goods.getStock() != existGoods.getStock()) {
            existGoods.setStock(goods.getStock());
        }
        if (!goods.getDescription().equals(existGoods.getDescription())) {
            existGoods.setDescription(goods.getDescription());
        }
        if (!goods.getType().equals(existGoods.getType())) {
            existGoods.setType(goods.getType());
            String type = goods.getType();
            if (type.equals("hot")) existGoods.setTypeLabel("近期热门");
            if (type.equals("reco")) existGoods.setTypeLabel("店家推荐");
            if (type.equals("disc")) existGoods.setTypeLabel("优惠回馈");
            if (type.equals("lit")) existGoods.setTypeLabel("文学长廊");
            if (type.equals("life")) existGoods.setTypeLabel("生活拾贝");
            if (type.equals("tech")) existGoods.setTypeLabel("科技博览");
            if (type.equals("manage")) existGoods.setTypeLabel("经管视野");
            if (type.equals("youth")) existGoods.setTypeLabel("健康同行");
        }
        goodsRepository.save(existGoods);
    }
}
