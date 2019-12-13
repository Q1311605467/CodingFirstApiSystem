package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.UserMessageMapper;
import com.fjut.cf.pojo.po.UserMessagePO;
import com.fjut.cf.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public Integer insertUserMessage(UserMessagePO userMessagePO) {
        return userMessageMapper.insertUserMessage(userMessagePO);
    }

    @Override
    public Integer updateUserMessageStatusSetReadByUsernameAndId(String username, Integer id) {
        return userMessageMapper.updateUserMessageStatusSetReadByUsernameAndId(username, id);
    }

    @Override
    public Integer updateUserMessageStatusSetReadByUsername(String username) {
        return userMessageMapper.updateUserMessageStatusSetAllReadByUsername(username);
    }

    @Override
    public List<UserMessagePO> queryUserMessageByUsernameDescLimit(String username, Integer startIndex, Integer pageSize) {
        return userMessageMapper.queryUserMessageByUsernameDescLimit(username, startIndex, pageSize);
    }

    @Override
    public Integer queryUserMessageCountByUsername(String username) {
        return userMessageMapper.queryUserMessageCountByUsername(username);
    }

    @Override
    public List<UserMessagePO> queryUserMessageUnreadByUsernameDescLimit(String username, Integer startIndex, Integer pageSize) {
        return userMessageMapper.queryUserMessageUnreadByUsernameDescLimit(username, startIndex, pageSize);
    }

    @Override
    public Integer queryUserMessageUnreadCountByUsername(String username) {
        return userMessageMapper.queryUserMessageUnreadCountByUsername(username);
    }
}
