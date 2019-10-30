package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.UserCheckInPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/23]
 */
public interface UserCheckInMapper {
    /**
     * 插入一条登录记录
     *
     * @param userCheckInPO
     * @return
     */
    Integer insertUserCheckIn(@Param("userCheckInPO") UserCheckInPO userCheckInPO);

    /**
     * 分页查询根据用户签到记录
     * @param username
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserCheckInPO> queryUserCheckInByUsernameDescLimit(@Param("username") String username,
                                                            @Param("startIndex")Integer startIndex,
                                                            @Param("pageSize") Integer pageSize);

}
