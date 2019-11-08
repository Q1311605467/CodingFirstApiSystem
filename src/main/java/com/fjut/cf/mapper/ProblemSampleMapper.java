package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ProblemSamplePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/7]
 */
public interface ProblemSampleMapper {
    /**
     * 根据题目ID查询题目样例
     * @param problemId
     * @return
     */
    List<ProblemSamplePO> queryProblemSampleByProblemId(@Param("problemId") Integer problemId);
}
