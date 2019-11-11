package com.fjut.cf.service;

import com.fjut.cf.pojo.po.UserMessagePO;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface UserMessageService {

    /**
     * 设置用户的特定消息已读
     *
     * @param username
     * @param id
     * @return
     */
    Integer updateUserMessageStatusSetReadByUsernameAndId(String username, Integer id);

    /**
     * 设置用户的特定消息已读
     *
     * @param username
     * @return
     */
    Integer updateUserMessageStatusSetReadByUsername(String username);

    /**
     * 根据用户名分页查询用户消息记录
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserMessagePO> queryUserMessageByUsernameDescLimit(String username, Integer startIndex, Integer pageSize);

    /**
     * 根据用户名查询用户消息
     *
     * @param username
     * @return
     */
    Integer queryUserMessageCountByUsername(String username);

    /**
     * 根据用户名分页查询用户未读消息记录
     *
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserMessagePO> queryUserMessageUnreadByUsernameDescLimit(String username, Integer startIndex, Integer pageSize);

    /**
     * 根据用户名查询用户消息
     *
     * @param username
     * @return
     */
    Integer queryUserMessageUnreadCountByUsername(String username);
}
