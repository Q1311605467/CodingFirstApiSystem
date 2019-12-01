package com.fjut.cf.service.impl;

import com.fjut.cf.component.email.EmailTool;
import com.fjut.cf.mapper.*;
import com.fjut.cf.pojo.bo.SendEmailBO;
import com.fjut.cf.pojo.po.*;
import com.fjut.cf.pojo.vo.*;
import com.fjut.cf.service.UserInfoService;
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
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserCustomInfoMapper userCustomInfoMapper;

    @Autowired
    UserCheckInMapper userCheckInMapper;

    @Autowired
    UserSealMapper userSealMapper;

    @Autowired
    UserTitleMapper userTitleMapper;

    @Autowired
    EmailTool emailTool;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean registerUser(UserBaseInfoPO userBaseInfoPO, String password, String avatarUrl) {
        // 插入用户基本信息
        int ans1 = userBaseInfoMapper.insertUserBaseInfo(userBaseInfoPO);
        // 插入用户权限信息
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
        // 新注册用户进行锁定
        userAuthPO.setLocked(1);
        userAuthPO.setUnlockTime(currentTime);
        userAuthPO.setLastLoginTime(currentTime);
        int ans2 = userAuthMapper.insertUserAuth(userAuthPO);
        // 插入头像路径
        UserCustomInfoPO userCustomInfoPO = new UserCustomInfoPO();
        userCustomInfoPO.setUsername(userBaseInfoPO.getUsername());
        userCustomInfoPO.setAvatarUrl(avatarUrl);
        Integer ans3 = userCustomInfoMapper.insertUserCustomInfo(userCustomInfoPO);
        SendEmailBO sendEmailBO = new SendEmailBO();
        sendEmailBO.setTo(userBaseInfoPO.getEmail());
        sendEmailBO.setSubject("一码当先acm.fjutcoder.com注册激活邮件");
        sendEmailBO.setText(userBaseInfoPO.getUsername() + "您好：\n"+"欢迎注册一码当先，请点击以下链接激活账号："
        +"http://xxxxxxxx");
        emailTool.sendEmail(sendEmailBO);
        return ans1 == 1 && ans2 == 1 && ans3 == 1;
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
        return userBaseInfoMapper.queryUserBaseInfoDescLimit(startIndex, pageSize);
    }

    @Override
    public UserBaseInfoPO queryUserInfoByUsername(String username) {
        UserBaseInfoPO userBaseInfoPO = userBaseInfoMapper.queryUserBaseInfoByUsername(username);
        return userBaseInfoPO;
    }

    @Override
    public UserCustomInfoVO queryUserCustomInfoByUsername(String username) {
        UserCustomInfoPO userCustomInfoPO = userCustomInfoMapper.queryUserCustomInfoByUsername(username);
        if (null == userCustomInfoPO) {
            return new UserCustomInfoVO();
        }
        UserCustomInfoVO result = new UserCustomInfoVO();
        result.setId(userCustomInfoPO.getId());
        result.setUsername(userCustomInfoPO.getUsername());
        result.setAvatarUrl(userCustomInfoPO.getAvatarUrl());
        if (userCustomInfoPO.getAdjectiveId() != null) {
            UserTitlePO userAdjTitle = userTitleMapper.queryUserTitleById(userCustomInfoPO.getAdjectiveId());
            result.setAdjectiveTitle(userAdjTitle.getName());
        }
        if (userCustomInfoPO.getArticleId() != null) {
            UserTitlePO userArtTitle = userTitleMapper.queryUserTitleById(userCustomInfoPO.getArticleId());
            result.setArticleTitle(userArtTitle.getName());
        }
        if (userCustomInfoPO.getSealId() != null) {
            UserSealPO userSealPO = userSealMapper.queryUserSealById(userCustomInfoPO.getSealId());
            result.setSealUrl(userSealPO.getPictureUrl());
        }
        return result;
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

    /**
     * TODO:
     *
     * @param username
     * @return
     */
    @Override
    public List<UserRadarVO> queryUserRadarByUsername(String username) {
        return null;
    }

}
