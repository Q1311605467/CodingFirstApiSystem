package com.fjut.cf.mapper;

import com.fjut.cf.pojo.vo.ChallengeBlockProblemVO;
import com.fjut.cf.pojo.vo.UserChallengeBlockVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface ChallengeBlockProblemMapper {

    /**
     * 分页查询模块内的题目
     *
     * @param blockId
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ChallengeBlockProblemVO> queryChallengeBlockProblemByBlockIdAscLimit(@Param("blockId") Integer blockId,
                                                                              @Param("startIndex") Integer startIndex,
                                                                              @Param("pageSize") Integer pageSize);

    /**
     * 查询模块内题目的总数量
     *
     * @param blockId
     * @return
     */
    Integer queryChallengeBlockProblemCountByBlockId(@Param("blockId") Integer blockId);

    /**
     * 获取用户模块解锁分数
     *
     * @param username
     * @return
     */
    List<UserChallengeBlockVO> queryChallengeBlockScoredByUsername(@Param("username") String username);

    /**
     * 查询题目隶属哪个挑战模式模块
     *
     * @param problemId
     * @return
     */
    Integer queryChallengeBlockByProblemId(@Param("problemId") Integer problemId);

    /**
     * 查询用户在某个模块下的得分情况
     *
     * @param username
     * @param blockId
     * @return
     */
    Integer queryBlockScoredByBlockIdAndUsername(@Param("blockId") Integer blockId, @Param("username") String username);

    /**
     * 查询模块的全部积分
     *
     * @param blockId
     * @return
     */
    Integer queryBlockTotalScoreByBlockId(@Param("blockId") Integer blockId);

}
