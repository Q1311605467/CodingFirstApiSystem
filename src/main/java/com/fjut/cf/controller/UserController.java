package com.fjut.cf.controller;

import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.pojo.UserInfoVO;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.redis.TokenManager;
import com.fjut.cf.redis.TokenModel;
import com.fjut.cf.service.UserService;
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

    @GetMapping("/get")
    public ResultJsonVO getUserInfoByUsername(@RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        UserInfoVO userInfoVO = userService.queryUserInfoByUsername(username);
        resultJsonVO.addInfo(userInfoVO);
        return resultJsonVO;
    }
}
