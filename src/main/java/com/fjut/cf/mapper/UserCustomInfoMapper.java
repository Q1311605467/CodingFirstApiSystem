package com.fjut.cf.mapper;

import com.fjut.cf.pojo.UserCustomInfoPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author axiang [2019/10/14]
 */
public interface UserCustomInfoMapper {
    /**
     * 根据用户名 查询用户个性化信息
     * @param username
     * @return
     */
    UserCustomInfoPO queryUserCustomInfoByUsername(@Param("username") String username);
}
