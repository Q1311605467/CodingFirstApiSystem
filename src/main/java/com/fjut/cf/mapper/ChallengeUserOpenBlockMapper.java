package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ChallengeUserOpenBlockPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface ChallengeUserOpenBlockMapper {
    /**
     * 插入一个解锁记录
     * @param challengeUserOpenBlockPO
     * @return
     */
    Integer insertChallengeUserOpenBlock(@Param("challengeUserOpenBlockPO") ChallengeUserOpenBlockPO challengeUserOpenBlockPO);

    /**
     * 获取用户已开放的挑战模块ID
     * @param username
     * @return
     */
    List<Integer> queryChallengeUserOpenBlockByUsername(@Param("username") String username);
}
