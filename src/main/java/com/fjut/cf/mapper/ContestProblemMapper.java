package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ContestProblemPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/21]
 */
public interface ContestProblemMapper {
    /**
     * 根据比赛ID查询比赛题目
     *
     * @param contestId
     * @return
     */
    List<ContestProblemPO> queryContestProblemByContestId(@Param("contestId") Integer contestId);
}
