package com.fjut.cf.service;

import com.fjut.cf.pojo.po.MallGoodsPO;

import java.util.List;

/**
 * @author axiang [2019/11/12]
 */
public interface MallGoodsService {
    /**
     * 分页查询商品信息记录
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<MallGoodsPO> queryAllMallGoods(Integer startIndex, Integer pageSize);

    /**
     * 查询商品总数
     *
     * @return
     */
    Integer queryMallGoodsCount();

    /**
     * 根据ID查询商品
     *
     * @param id
     * @return
     */
    MallGoodsPO queryMallGoodsByGoodsId(Integer id);

}
