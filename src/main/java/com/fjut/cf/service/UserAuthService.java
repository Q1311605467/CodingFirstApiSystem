package com.fjut.cf.service;

/**
 * @author axiang [2019/11/28]
 */
public interface UserAuthService {
    /**
     * 查询用户是否锁定
     *
     * @param username
     * @return
     */
    Integer queryUserAuthLockedByUsername(String username);
}
