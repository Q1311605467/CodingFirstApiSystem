package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.UserBaseInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/10/11]
 */
public interface UserBaseInfoMapper {
    /**
     * 增加一条用户基本信息
     *
     * @param userBaseInfo
     * @return
     */
    Integer insertUserBaseInfo(@Param("userBaseInfo") UserBaseInfoPO userBaseInfo);

    /**
     * 根据用户名删除一条用户基本信息
     *
     * @param username
     * @return
     */
    Integer deleteUserBaseInfoByUsername(@Param("username") String username);

    /**
     * 根据用户名修改一条用户基本消息
     *
     * @param username
     * @param userBaseInfo
     * @return
     */
    Integer updateUserBaseInfoByUsername(@Param("username") String username, @Param("userBaseInfo") UserBaseInfoPO userBaseInfo);

    /**
     * 更新用户AC题数+1
     *
     * @param username
     * @return
     */
    Integer updateUserBaseInfoAcNumAddOneByUsername(@Param("username") String username);

    /**
     * 查找用户基本信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserBaseInfoPO> queryAllUserBaseInfo(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 根据用户名查找用户基本信息
     *
     * @param username
     * @return
     */
    UserBaseInfoPO queryUserBaseInfoByUsername(@Param("username") String username);

    /**
     * 根据用户名查询用户基本信息数量
     *
     * @param username
     * @return
     */
    Integer queryUserBaseInfoCountByUsername(@Param("username") String username);

    /**
     * 分页查询ACB榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserBaseInfoPO> queryAcbTopDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 分页查询AC题数榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserBaseInfoPO> queryAcNumTopDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 分页查询积分榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<UserBaseInfoPO> queryRatingTopDescLimit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
