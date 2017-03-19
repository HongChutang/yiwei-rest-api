package person.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import person.practice.model.Banner;
import person.practice.model.Goods;
import person.practice.model.GoodsType;
import person.practice.repository.GoodsTypeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan Hung on 2016/12/27.
 */
@RestController
public class ServeGoodsTypeController {
    private GoodsTypeRepository goodsTypeRepository;

    @Autowired
    public ServeGoodsTypeController(GoodsTypeRepository goodsTypeRepository) {
        this.goodsTypeRepository = goodsTypeRepository;
    }
    @RequestMapping(value = "/goods/types/slider", method = RequestMethod.GET)
    public List<GoodsType> getGoodsTypes1() {
        List<GoodsType> goodsTypeList = new ArrayList<>();
        goodsTypeList.add(goodsTypeRepository.findOne("hot"));
        goodsTypeList.add(goodsTypeRepository.findOne("reco"));
        goodsTypeList.add(goodsTypeRepository.findOne("disc"));
        return goodsTypeList;
    }

    @RequestMapping(value = "/goods/types/block", method = RequestMethod.GET)
    public List<GoodsType> getGoodsTypes2() {
        List<GoodsType> goodsTypeList = new ArrayList<>();
        goodsTypeList.add(goodsTypeRepository.findOne("lit"));
        goodsTypeList.add(goodsTypeRepository.findOne("life"));
        goodsTypeList.add(goodsTypeRepository.findOne("youth"));
        goodsTypeList.add(goodsTypeRepository.findOne("tech"));
        goodsTypeList.add(goodsTypeRepository.findOne("manage"));
        goodsTypeList.add(goodsTypeRepository.findOne("health"));
        return goodsTypeList;
    }

    @RequestMapping(value = "/goods/banner/{type}", method = RequestMethod.GET)
    public Banner getGoodsBanner(@PathVariable String type) {
        GoodsType goodsType = goodsTypeRepository.findOne(type);
        Banner banner = new Banner();
        banner.setBannerImg(goodsType.getBanner());
        banner.setTitle(goodsType.getTypeLabel());
        banner.setSubTitle(goodsType.getIntro());
        return banner;
    }

}
