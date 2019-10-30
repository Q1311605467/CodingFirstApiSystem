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
     * 带条件查询题目总数
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

}
