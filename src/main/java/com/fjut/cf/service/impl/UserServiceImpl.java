package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.UserAuthMapper;
import com.fjut.cf.mapper.UserBaseInfoMapper;
import com.fjut.cf.mapper.UserCheckInMapper;
import com.fjut.cf.mapper.UserCustomInfoMapper;
import com.fjut.cf.pojo.po.UserAuthPO;
import com.fjut.cf.pojo.po.UserBaseInfoPO;
import com.fjut.cf.pojo.po.UserCustomInfoPO;
import com.fjut.cf.pojo.vo.UserAcNumBorderVO;
import com.fjut.cf.pojo.vo.UserAcbBorderVO;
import com.fjut.cf.pojo.vo.UserInfoVO;
import com.fjut.cf.pojo.vo.UserRatingBorderVO;
import com.fjut.cf.service.UserService;
import com.fjut.cf.util.SHAUtils;
import com.fjut.cf.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author axiang [2019/10/11]
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserCustomInfoMapper userCustomInfoMapper;

    @Autowired
    UserCheckInMapper userCheckInMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean registerUser(UserBaseInfoPO userBaseInfoPO, String password) {

        UserAuthPO userAuthPO = new UserAuthPO();
        Date currentTime = new Date();
        // 得到随机盐值
        String salt = UUIDUtils.getUUID32();
        // 加盐密码
        String newPassword = salt + password;
        // 对加盐密码使用SHA1加密
        String encryptedPwd = SHAUtils.SHA1(newPassword);
        userAuthPO.setUsername(userBaseInfoPO.getUsername());
        userAuthPO.setSalt(salt);
        userAuthPO.setPassword(encryptedPwd);
        userAuthPO.setAttemptLoginFailCount(0);
        userAuthPO.setLocked(0);
        userAuthPO.setUnlockTime(currentTime);
        userAuthPO.setLastLoginTime(currentTime);
        int ans1 = userBaseInfoMapper.insertUserBaseInfo(userBaseInfoPO);
        int ans2 = userAuthMapper.insertUserAuth(userAuthPO);
        return ans1 == 1 && ans2 == 1;
    }

    @Override
    public Boolean doUserLogin(String username, String password) {
        // 最大登录尝试次数
        int maxAttemptTimes = 5;
        // 取出数据库中盐值
        String salt = userAuthMapper.queryUserAuthSaltByUsername(username);
        String newPwd = salt + password;
        // 对明文密码和盐值做加密
        String encryptPwd = SHAUtils.SHA1(newPwd);
        // 查询用户名密码是否对应上
        Integer ans = userAuthMapper.queryUserAuthByUsernameAndPassword(username, encryptPwd);
        // 登录成功
        if (1 == ans) {
            // 重置尝试失败次数为0
            userAuthMapper.updateUserAuthAttemptFailSetZeroByUsername(username);
            return true;
        }
        // 登录失败
        else {
            //更新登录失败次数+1
            userAuthMapper.updateUserAuthAttemptFailByUsername(username, 1);
            //查询登录失败次数
            Integer failLoginCount = userAuthMapper.queryUserAuthAttemptNumberByUsername(username);
            //超过最大登录失败次数，则账号锁定五分钟
            if (failLoginCount >= maxAttemptTimes) {
                //设置时间为现在时间的五分钟后
                long timeLaterFiveMin = System.currentTimeMillis() + (60 * 5 * 1000);
                userAuthMapper.updateUserAuthUnlockTimeByUsername(new Date(timeLaterFiveMin), username);
            }
            return false;
        }
    }

    @Override
    public Date queryUserUnlockTimeByUsername(String username) {
        return userAuthMapper.queryUserAuthUnlockTimeByUsername(username);
    }

    @Override
    public Boolean queryUserExistByUsername(String username) {
        Integer num1 = userAuthMapper.queryUserAuthCountByUsername(username);
        Integer num2 = userBaseInfoMapper.queryUserBaseInfoCountByUsername(username);
        return (num1 == 1) && (num2 == 1);
    }

    @Override
    public Integer queryUserAuthAttemptNumberByUsername(String username) {
        return userAuthMapper.queryUserAuthAttemptNumberByUsername(username);
    }

    @Override
    public List<UserBaseInfoPO> queryAllUserBaseInfo(int startIndex, int pageSize) {
        return userBaseInfoMapper.queryAllUserBaseInfo(startIndex, pageSize);
    }

    @Override
    public UserInfoVO queryUserInfoByUsername(String username) {
        UserBaseInfoPO userBaseInfoPO = userBaseInfoMapper.queryUserBaseInfoByUsername(username);
        UserCustomInfoPO userCustomInfoPO = userCustomInfoMapper.queryUserCustomInfoByUsername(username);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserBaseInfoPO(userBaseInfoPO);
        userInfoVO.setUserCustomInfoPO(userCustomInfoPO);
        return userInfoVO;
    }

    @Override
    public UserCustomInfoPO queryUserCustomInfoByUsername(String username) {
        return userCustomInfoMapper.queryUserCustomInfoByUsername(username);
    }

    @Override
    public List<UserAcbBorderVO> queryAcbBorder(int startIndex, int pageSize) {
        List<UserAcbBorderVO> userAcbBorderVOS = new ArrayList<>();
        List<UserBaseInfoPO> userBaseInfoPOS = userBaseInfoMapper.queryAcbTopDescLimit(startIndex, pageSize);
        for (UserBaseInfoPO userBase : userBaseInfoPOS) {
            UserAcbBorderVO userAcbBorderVO = new UserAcbBorderVO();
            userAcbBorderVO.setUsername(userBase.getUsername());
            userAcbBorderVO.setNick(userBase.getNick());
            userAcbBorderVO.setAcb(userBase.getAcb());
            userAcbBorderVOS.add(userAcbBorderVO);
        }
        return userAcbBorderVOS;
    }

    @Override
    public List<UserAcNumBorderVO> queryAcNumBorder(int startIndex, int pageSize) {
        List<UserAcNumBorderVO> userAcNumBorderVOS = new ArrayList<>();
        List<UserBaseInfoPO> userBaseInfoPOS = userBaseInfoMapper.queryAcNumTopDescLimit(startIndex, pageSize);
        for (UserBaseInfoPO userBase : userBaseInfoPOS) {
            UserAcNumBorderVO userAcbBorderVO = new UserAcNumBorderVO();
            userAcbBorderVO.setUsername(userBase.getUsername());
            userAcbBorderVO.setNick(userBase.getNick());
            userAcbBorderVO.setAcNum(userBase.getAcNum());
            userAcNumBorderVOS.add(userAcbBorderVO);
        }
        return userAcNumBorderVOS;
    }

    @Override
    public List<UserRatingBorderVO> queryRatingBorder(int startIndex, int pageSize) {
        List<UserRatingBorderVO> userRatingBorderVOS = new ArrayList<>();
        List<UserBaseInfoPO> userBaseInfoPOS = userBaseInfoMapper.queryRatingTopDescLimit(startIndex, pageSize);
        for (UserBaseInfoPO userBase : userBaseInfoPOS) {
            UserRatingBorderVO userRatingBorderVO = new UserRatingBorderVO();
            userRatingBorderVO.setUsername(userBase.getUsername());
            userRatingBorderVO.setNick(userBase.getNick());
            userRatingBorderVO.setRating(userBase.getRating());
            userRatingBorderVOS.add(userRatingBorderVO);
        }
        return userRatingBorderVOS;
    }


}
