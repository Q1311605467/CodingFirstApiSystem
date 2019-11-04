package com.fjut.cf.service;

import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.StatusCountVO;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
public interface JudgeStatusService {
    /**
     *
     * 查询最近 days 天的提交统计
     *
     * @param days
     * @return
     */
    List<StatusCountVO> queryStatusCountByDayAsc(int days);

    /**
     * 分页查询评测列表
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<JudgeStatusVO> queryJudgeStatusDescLimit(Integer startIndex,Integer pageSize);

    /**
     * 查询评测列表视图的大小
     * @return
     */
    Integer queryViewJudgeStatusCount();
}
