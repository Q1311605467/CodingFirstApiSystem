package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.JudgeStatusPO;
import com.fjut.cf.pojo.vo.StatusCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
public interface JudgeStatusMapper {

    /**
     * 分页查询带昵称的用户评测信息
     *
     * @param nick
     * @param problemId
     * @param result
     * @param language
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<JudgeStatusPO> queryJudgeStatusWithNickDescLimit(
            @Param("nick") String nick,
            @Param("problemId") Integer problemId,
            @Param("result") Integer result,
            @Param("language") Integer language,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize);


    /**
     * 查询最近 days 天的提交统计
     *
     * @param days
     * @return
     */
    List<StatusCountVO> queryStatusCountByDayAsc(@Param("days") int days);
}
