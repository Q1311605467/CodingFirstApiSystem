package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.BorderHonorRankPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface BorderHonorRankMapper {
    /**
     * 分页查询榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<BorderHonorRankPO> queryBorderHonorRankDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询榜单记录数
     *
     * @return
     */
    Integer queryBorderHonorRankCount();
}
