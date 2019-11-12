package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.UserCheckInMapper;
import com.fjut.cf.pojo.po.UserCheckInPO;
import com.fjut.cf.service.UserCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
@Service
public class UserCheckInServiceImpl implements UserCheckInService {
    @Autowired
    UserCheckInMapper userCheckInMapper;

    @Override
    public List<UserCheckInPO> queryAllUserCheckInByUsername(String username) {
        return userCheckInMapper.queryAllUserCheckInByUsername(username);
    }

    @Override
    public Integer queryTodayUserCheckInCountByUsername(String username) {
        return userCheckInMapper.queryTodayUserCheckInCountByUsername(username);
    }

    @Override
    public List<UserCheckInPO> queryUserCheckInByUsername(String username, Integer startIndex, Integer pageSize) {
        return userCheckInMapper.queryUserCheckInByUsernameDescLimit(username, startIndex, pageSize);
    }

    @Override
    public Integer insertUserCheckIn(UserCheckInPO userCheckInPO) {
        return userCheckInMapper.insertUserCheckIn(userCheckInPO);
    }
}
