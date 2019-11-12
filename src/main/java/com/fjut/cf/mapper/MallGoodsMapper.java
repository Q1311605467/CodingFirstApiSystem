package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.MallGoodsPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/12]
 */
public interface MallGoodsMapper {
    /**
     * 分页查询商品信息记录
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
   List<MallGoodsPO> queryAllMallGoods(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

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
    MallGoodsPO queryMallGoodsByGoodsId(@Param("id") Integer id);

}
