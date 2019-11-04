package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ViewJudgeStatusPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/31]
 */
public interface ViewJudgeStatusMapper {
    /**
     * 分页查询评测视图
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ViewJudgeStatusPO> queryViewJudgeStatusDescLimit(@Param("startIndex") Integer startIndex,
                                                          @Param("pageSize") Integer pageSize);

    Integer queryViewJudgeStatusCount();
}
