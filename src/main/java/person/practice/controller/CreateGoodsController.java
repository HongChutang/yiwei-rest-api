package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.helper.GeneImgHelper;
import person.practice.model.Goods;
import person.practice.repository.GoodsRepository;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@RestController
public class CreateGoodsController {
    private GoodsRepository goodsRepository;

    @Autowired
    public CreateGoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @RequestMapping(value = "/admin/goods", method = RequestMethod.POST, headers = {
            "Content-Type=application/json"})
    public void createGoods(@RequestBody Goods goods) {
        // 查看是否已经有该商品，如果已有则加上已有库存并更新其他商品信息
        Goods existGoods = goodsRepository.findOne(goods.getGoodsName());
        if (existGoods!= null) {
            goods.setStock(goods.getStock()+existGoods.getStock());
            if (existGoods.getCartStock() != null)
                goods.setCartStock(existGoods.getCartStock());
            goodsRepository.delete(goods.getGoodsName());
        }
        goods.setImage(GeneImgHelper.generateImage(goods.getImage()));
        goods.setCartStock(goods.getStock());
        String type = goods.getType();
        if (type.equals("hot")) goods.setTypeLabel("近期热门");
        if (type.equals("reco")) goods.setTypeLabel("店家推荐");
        if (type.equals("disc")) goods.setTypeLabel("优惠回馈");
        if (type.equals("lit")) goods.setTypeLabel("文学长廊");
        if (type.equals("life")) goods.setTypeLabel("生活拾贝");
        if (type.equals("tech")) goods.setTypeLabel("科技博览");
        if (type.equals("manage")) goods.setTypeLabel("经管视野");
        if (type.equals("youth")) goods.setTypeLabel("健康同行");
        goodsRepository.save(goods);
    }
}
