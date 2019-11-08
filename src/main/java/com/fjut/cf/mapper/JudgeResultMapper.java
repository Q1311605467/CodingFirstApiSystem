package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.JudgeResultPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author axiang [2019/11/8]
 */
public interface JudgeResultMapper {
    /**
     * 插入一条评测结果记录
     *
     * @param judgeResultPO
     * @return
     */
    Integer insertJudgeResult(@Param("judgeResultPO") JudgeResultPO judgeResultPO);
}
