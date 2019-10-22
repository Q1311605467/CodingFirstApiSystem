package com.fjut.cf.controller;

import com.fjut.cf.pojo.ResultJsonVO;
import com.fjut.cf.redis.RedisUtils;
import com.fjut.cf.token.TokenManager;
import com.fjut.cf.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author axiang [2019/10/21]
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    TokenManager tokenManager;

    @GetMapping("/test")
    public ResultJsonVO testMethod()
    {
        TokenModel axiang = tokenManager.createToken("axiang");
        String auth = tokenManager.createAuth(axiang);
        TokenModel tokenModel = tokenManager.getToken(auth);
        System.out.println(auth);
        System.out.println(tokenModel.getUsername());
        System.out.println(tokenModel.getToken());
        boolean b = tokenManager.checkToken(tokenModel);
        System.out.println(b);

        return new ResultJsonVO();
    }
}
