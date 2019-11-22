package com.fjut.cf.service;

import com.fjut.cf.pojo.po.JudgeStatusPO;
import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.StatusCountVO;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
public interface JudgeStatusService {
    /**
     * 插入一条评测记录
     *
     * @param judgeStatusPO
     * @return
     */
    Integer insertJudgeStatus(JudgeStatusPO judgeStatusPO);

    /**
     * 如果提交代码成功更新数据库内容
     * 更新题目表
     * 更新用户解答记录表
     *
     * @param judgeStatusPO
     * @return
     */
    Boolean updateJudgeStatusWhenSubmitSuccess(JudgeStatusPO judgeStatusPO);

    /**
     * 如果提交代码失败更新数据库内容
     *
     * @param judgeId
     * @return
     */
    Boolean updateJudgeStatusWhenSubmitFail(Integer judgeId);

    /**
     * 查询本地评测机中的结果，异步做法
     *
     * @param judgeStatusPO
     * @throws Exception
     */
    void queryResultFromLocalJudgeAsync(JudgeStatusPO judgeStatusPO) throws Exception;

    /**
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
     * @param nick
     * @param problemId
     * @param result
     * @param language
     * @return
     */
    List<JudgeStatusVO> queryJudgeStatusByConditionsDescLimit(Integer startIndex, Integer pageSize, Integer contestId, String nick, Integer problemId, Integer result, Integer language);

    /**
     * 查询评测列表视图的大小
     *
     * @param nick
     * @param problemId
     * @param result
     * @param language
     * @return
     */
    Integer queryViewJudgeStatusCountByConditions(Integer contestId, String nick, Integer problemId, Integer result, Integer language);

    /**
     * 根据评测ID查询评测列表视图
     *
     * @param id
     * @return
     */
    JudgeStatusVO queryViewJudgeStatusById(Integer id);

    /**
     * 根据用户名查询评测记录条数
     *
     * @param username
     * @return
     */
    Integer queryJudgeStatusCountByUsername(String username);

}
