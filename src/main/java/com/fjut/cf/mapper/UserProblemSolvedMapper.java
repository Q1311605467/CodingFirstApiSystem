package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ProblemTypeCountPO;
import com.fjut.cf.pojo.po.UserProblemSolvedPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/14]
 */
public interface UserProblemSolvedMapper {
    /**
     * 插入一条解题记录
     *
     * @param userProblemSolvedPO
     * @return
     */
    Integer insertUserProblemSolved(@Param("userProblemSolvedPO") UserProblemSolvedPO userProblemSolvedPO);

    /**
     * 根据用户名和题目ID更新尝试次数+1
     *
     * @param username
     * @param problemId
     * @return
     */
    Integer updateUserProblemSolvedTryCountAddOne(@Param("username") String username, @Param("problemId") Integer problemId);

    /**
     * 根据用户名和题目ID更新AC次数+1
     *
     * @param username
     * @param problemId
     * @return
     */
    Integer updateUserProblemSolvedSolvedCountAddOne(@Param("username") String username, @Param("problemId") Integer problemId);

    /**
     * 根据用户名和题目ID更新第一次解决的时间
     *
     * @param username
     * @param problemId
     * @return
     */
    Integer updateUserProblemSolvedFirstSolvedTime(@Param("username") String username, @Param("problemId") Integer problemId);

    /**
     * 根据用户名和题目ID更新最后尝试时间
     *
     * @param username
     * @param problemId
     * @return
     */
    Integer updateUserProblemSolvedLastTryTime(@Param("username") String username, @Param("problemId") Integer problemId);

    /**
     * 根据用户名查找对应的用户解答记录
     *
     * @param username
     * @return
     */
    List<UserProblemSolvedPO> queryUserProblemSolvedByUsername(@Param("username") String username);

    /**
     * 根据用户名和题目ID查找对应记录
     *
     * @param username
     * @param problemId
     * @return
     */
    UserProblemSolvedPO queryUserProblemSolvedByUsernameAndProblemId(@Param("username") String username, @Param("problemId") Integer problemId);

    /**
     * 根据用户名和题目ID查找对应记录数量
     *
     * @param username
     * @param problemId
     * @return
     */
    Integer queryUserProblemSolvedCountByUsernameAndProblemId(@Param("username") String username, @Param("problemId") Integer problemId);


    /**
     * 查询用户完成题目中的题目类型总计
     * @return
     */
    List<ProblemTypeCountPO> queryProblemTypeCountByUsername(@Param("username") String username);
}
