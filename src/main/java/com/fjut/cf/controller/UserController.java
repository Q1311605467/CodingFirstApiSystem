package com.fjut.cf.controller;

import com.fjut.cf.interceptor.LoginRequired;
import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.pojo.UserBaseInfoPO;
import com.fjut.cf.pojo.UserInfoVO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.service.UserService;
import com.fjut.cf.token.TokenManager;
import com.fjut.cf.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author axiang [2019/10/11]
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenManager tokenManager;

    @PostMapping("/login")
    public ResultJsonVO userLogin(@RequestParam("username") String username,
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
    public ResultJsonVO userRegister(@RequestParam("username") String username,
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
        if(isExist)
        {
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
        if(ans)
        {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS, "用户注册成功！");
        }
        else{
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL, "用户注册失败！");
        }
        return resultJsonVO;
    }

    @GetMapping("/get")
    @LoginRequired
    public ResultJsonVO getUserInfoByUsername(@RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        UserInfoVO userInfoVO = userService.queryUserInfoByUsername(username);
        resultJsonVO.addInfo(userInfoVO);
        return resultJsonVO;
    }
}
