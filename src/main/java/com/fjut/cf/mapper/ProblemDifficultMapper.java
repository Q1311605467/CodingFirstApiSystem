package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ProblemDifficultPO;
import com.fjut.cf.pojo.po.ProblemTypeCountPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/22]
 */
public interface ProblemDifficultMapper {
    /**
     * 插入一条难度记录
     *
     * @param problemDifficult
     * @return
     */
    Integer insertProblemDifficult(@Param("problemDifficult") ProblemDifficultPO problemDifficult);

    /**
     * 根据题目ID 删除一条难度记录
     *
     * @param problemId
     * @return
     */
    Integer deleteProblemDifficultByProblemId(@Param("problemId") Integer problemId);

    /**
     * 查找 pageSize 页的题目难度信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ProblemDifficultPO> queryProblemDifficultDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询题目类型总计
     * @return
     */
    List<ProblemTypeCountPO> queryProblemTypeCount();


}
