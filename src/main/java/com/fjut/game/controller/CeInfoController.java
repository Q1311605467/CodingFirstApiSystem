package com.fjut.game.controller;

import com.fjut.game.pojo.CeInfoPO;
import com.fjut.game.pojo.JsonInfoVO;
import com.fjut.game.service.CeInfoService;
import com.fjut.game.util.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author axiang [2019/10/8]
 */
@RestController
@RequestMapping("/ce_info")
public class CeInfoController {
    @Autowired
    CeInfoService ceInfoService;

    @GetMapping("/get")
    public JsonInfoVO getCeInfo()
    {
        JsonInfoVO jsonInfoVO = new JsonInfoVO();
        List<CeInfoPO> ceInfoPOS = ceInfoService.queryAllCeInfo();
        Logger logger = LogUtils.getBussinessLogger();
        logger.error("error");
        logger.info("info");
        logger.debug("debug");
        logger.warn("warn");
        jsonInfoVO.addInfo(ceInfoPOS);
        return jsonInfoVO;
    }

}
