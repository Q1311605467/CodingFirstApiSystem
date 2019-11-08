package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ProblemInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/22]
 */
public interface ProblemInfoMapper {

    /**
     * 插入一条题目基本信息记录
     *
     * @param problemInfoPO
     * @return
     */
    Integer insertProblemInfo(@Param("problemInfoPO") ProblemInfoPO problemInfoPO);

    /**
     * 根据 problemId 删除 题目基本信息
     *
     * @param problemId
     * @return
     */
    Integer deleteProblemInfoByProblemId(@Param("problemId") Integer problemId);

    /**
     * 更新题目提交总数+1
     *
     * @param problemId
     * @return
     */
    Integer updateProblemInfoTotalSubmitAddOne(@Param("problemId") Integer problemId);

    /**
     * 更新题目提交AC总数+1
     *
     * @param problemId
     * @return
     */
    Integer updateProblemInfoTotalAcAddOne(@Param("problemId") Integer problemId);

    /**
     * 更新题目提交用户总数+1
     *
     * @param problemId
     * @return
     */
    Integer updateProblemInfoTotalSubmitUserAddOne(@Param("problemId") Integer problemId);

    /**
     * 更新提交提交用户的AC总数+1
     *
     * @param problemId
     * @return
     */
    Integer updateProblemInfoTotalAcUserAddOne(@Param("problemId") Integer problemId);

    /**
     * 带条件查询题目总数
     *
     * @param title
     * @param tagId
     * @return
     */
    Integer queryProblemInfoCount(@Param("title") String title, @Param("tagId") Integer tagId);

    /**
     * 查找 pageSize 页的题目基本信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ProblemInfoPO> queryProblemInfoDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 根据题目ID查询题目基本信息
     *
     * @param problemId
     * @return
     */
    ProblemInfoPO queryProblemInfoByProblemId(@Param("problemId") Integer problemId);

}
