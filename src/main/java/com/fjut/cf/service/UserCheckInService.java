package com.fjut.cf.service;


import com.fjut.cf.pojo.po.UserCheckInPO;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
public interface UserCheckInService {
    /**
     * 查询用户的签到记录
     *
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserCheckInPO> queryUserCheckInByUsername(String username, Integer startIndex, Integer pageSize);
}
