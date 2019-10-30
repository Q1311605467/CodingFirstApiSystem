package com.fjut.cf.controller;

import com.fjut.cf.component.interceptor.LoginRequired;
import com.fjut.cf.component.interceptor.PrivateRequired;
import com.fjut.cf.pojo.vo.*;
import com.fjut.cf.pojo.po.UserBaseInfoPO;
import com.fjut.cf.pojo.po.UserCheckInPO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.service.UserCheckInService;
import com.fjut.cf.service.UserService;
import com.fjut.cf.component.token.TokenManager;
import com.fjut.cf.component.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author axiang [2019/10/11]
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserCheckInService userCheckInService;

    @Autowired
    TokenManager tokenManager;

    @PostMapping("/login")
    public ResultJsonVO postUserLogin(@RequestParam("username") String username,
                                      @RequestParam("password") String password) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Date currentDate = new Date();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "用户名或者密码为空！");
            return resultJsonVO;
        }
        // 用户名不存在
        if (!userService.queryUserExistByUsername(username)) {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "登录失败！用户不存在！");
            return resultJsonVO;
        }
        // 查询登录权限的解锁时间
        Date unlockTime = userService.queryUserUnlockTimeByUsername(username);
        // 如果当前时间小于解锁时间，则表示账号还在锁定期，无法登录
        if (0 > currentDate.compareTo(unlockTime)) {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "您的账号已暂时被锁定，请稍后登录。如有疑问，请联系管理员");
            return resultJsonVO;
        }
        if (userService.doUserLogin(username, password)) {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS, "登录成功！");
            TokenModel tokenModel = tokenManager.createToken(username);
            String auth = tokenManager.createAuth(tokenModel);
            resultJsonVO.addInfo(username);
            resultJsonVO.addInfo(auth);
        } else {
            Integer attemptCount = userService.queryUserAuthAttemptNumberByUsername(username);
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "登录失败！账号或密码不正确！您还有 " + (Math.max(5 - attemptCount, 0)) + "次机会");
        }
        return resultJsonVO;
    }

    @PostMapping("/register")
    public ResultJsonVO postUserRegister(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("nick") String nick,
                                         @RequestParam(value = "gender", required = false) Integer gender,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("motto") String motto,
                                         @RequestParam(value = "school", required = false) String school,
                                         @RequestParam(value = "faculty", required = false) String faculty,
                                         @RequestParam(value = "major", required = false) String major,
                                         @RequestParam(value = "cla", required = false) String cla,
                                         @RequestParam(value = "studentId", required = false) String studentId,
                                         @RequestParam(value = "graduationDate", required = false) String graduationDateStr
    ) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Boolean isExist = userService.queryUserExistByUsername(username);
        if (isExist) {
            resultJsonVO.setStatus(ResultJsonCode.SYSTEM_ERROR, "注册的用户已存在！");
            return resultJsonVO;
        }
        UserBaseInfoPO userBaseInfo = new UserBaseInfoPO();
        userBaseInfo.setUsername(username);
        userBaseInfo.setNick(nick);
        userBaseInfo.setGender(gender);
        userBaseInfo.setEmail(email);
        userBaseInfo.setPhone(phone);
        userBaseInfo.setMotto(motto);
        userBaseInfo.setRegisterTime(new Date());
        userBaseInfo.setSchool(school);
        userBaseInfo.setFaculty(faculty);
        userBaseInfo.setMajor(major);
        userBaseInfo.setCla(cla);
        userBaseInfo.setStudentId(studentId);
        userBaseInfo.setGraduationYear(graduationDateStr);
        Boolean ans = userService.registerUser(userBaseInfo, password);
        if (ans) {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS, "用户注册成功！");
        } else {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "用户注册失败！");
        }
        return resultJsonVO;
    }

    @GetMapping("/info/get")
    @LoginRequired
    public ResultJsonVO getUserInfoByUsername(@RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        UserInfoVO userInfoVO = userService.queryUserInfoByUsername(username);
        resultJsonVO.addInfo(userInfoVO);
        return resultJsonVO;
    }

    @GetMapping("/check_in/get")
    @PrivateRequired
    public ResultJsonVO getUserCheckInByUsername(@RequestParam("username") String username,
                                                 @RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 30;
        }
        Integer startIndex = (pageNum - 1) * pageSize;
        List<UserCheckInPO> userCheckInPOS = userCheckInService.queryUserCheckInByUsername(username, startIndex, pageSize);
        resultJsonVO.addInfo(userCheckInPOS);
        return resultJsonVO;
    }

    @GetMapping("/border/get")
    public ResultJsonVO getUserBorder(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Integer startIndex = (pageNum - 1) * pageSize;
        List<UserRatingBorderVO> userRatingBorderVOS = userService.queryRatingBorder(startIndex, pageSize);
        List<UserAcNumBorderVO> userAcNumBorderVOS = userService.queryAcNumBorder(startIndex, pageSize);
        List<UserAcbBorderVO> userAcbBorderVOS = userService.queryAcbBorder(startIndex, pageSize);
        resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        resultJsonVO.addInfo(userRatingBorderVOS);
        resultJsonVO.addInfo(userAcNumBorderVOS);
        resultJsonVO.addInfo(userAcbBorderVOS);
        return resultJsonVO;
    }

}




