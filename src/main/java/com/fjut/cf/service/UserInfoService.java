package com.fjut.cf.service;

import com.fjut.cf.pojo.po.UserBaseInfoPO;
import com.fjut.cf.pojo.vo.*;

import java.util.Date;
import java.util.List;

/**
 * @author axiang [2019/10/11]
 */
public interface UserInfoService {
    /**
     * 注册用户
     *
     * @param userBaseInfoPO
     * @param password
     * @param avatarUrl
     * @return
     */
    Boolean registerUser(UserBaseInfoPO userBaseInfoPO, String password, String avatarUrl);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    Boolean doUserLogin(String username, String password);

    /**
     * 查询用户登录解锁时间
     *
     * @param username
     * @return
     */
    Date queryUserUnlockTimeByUsername(String username);

    /**
     * 根据用户名查询该用户名是否存在
     *
     * @param username
     * @return
     */
    Boolean queryUserExistByUsername(String username);

    /**
     * 查询用户的登录失败次数
     *
     * @param username
     * @return
     */
    Integer queryUserAuthAttemptNumberByUsername(String username);

    /**
     * 查找全部用户基础信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserBaseInfoPO> queryAllUserBaseInfo(int startIndex, int pageSize);

    /**
     * 根据用户名查找用户所有信息
     *
     * @param username
     * @return
     */
    UserBaseInfoPO queryUserInfoByUsername(String username);

    /**
     * 根据用户名查询用户个性化信息
     *
     * @param username
     * @return
     */
    UserCustomInfoVO queryUserCustomInfoByUsername(String username);

    /**
     * 查询ACB榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserAcbBorderVO> queryAcbBorder(int startIndex, int pageSize);

    /**
     * 查询AC题数榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserAcNumBorderVO> queryAcNumBorder(int startIndex, int pageSize);

    /**
     * 查询积分榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserRatingBorderVO> queryRatingBorder(int startIndex, int pageSize);

    /**
     * 根据用户名查询用户能力雷达
     * TODO：目前没做加权，只是简单计算解答数量！
     *
     * @param username
     * @return
     */
    List<UserRadarVO> queryUserRadarByUsername(String username);


}
