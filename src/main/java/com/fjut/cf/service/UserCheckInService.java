package com.fjut.cf.service;


import com.fjut.cf.pojo.po.UserCheckInPO;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
public interface UserCheckInService {
    /**
     * 查询用户全部的签到记录
     *
     * @param username
     * @return
     */
    List<UserCheckInPO> queryAllUserCheckInByUsername(String username);

    /**
     * 根据用户名查询用户今天的签到次数
     *
     * @param username
     * @return
     */
    Integer queryTodayUserCheckInCountByUsername(String username);

    /**
     * 分页查询用户的签到记录
     *
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserCheckInPO> queryUserCheckInByUsername(String username, Integer startIndex, Integer pageSize);


    /**
     * 插入一条签到记录
     *
     * @param userCheckInPO
     * @return
     */
    Integer insertUserCheckIn(UserCheckInPO userCheckInPO);
}
