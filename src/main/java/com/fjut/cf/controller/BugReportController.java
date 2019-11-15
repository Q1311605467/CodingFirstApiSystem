package com.fjut.cf.controller;

import com.fjut.cf.component.interceptor.LoginRequired;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.po.BugReportPO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.service.BugReportedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author axiang [2019/11/15]
 */
@RestController
@CrossOrigin
@RequestMapping("/bug")
public class BugReportController {
    @Autowired
    BugReportedService bugReportedService;

    @LoginRequired
    @PostMapping("/report")
    public ResultJsonVO insertReport(@RequestParam("username") String username,
                                     @RequestParam("type") String typeStr,
                                     @RequestParam("title") String title,
                                     @RequestParam("text") String text) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Integer type = Integer.parseInt(typeStr);
        BugReportPO bugReport = new BugReportPO();
        bugReport.setUsername(username);
        bugReport.setType(type);
        bugReport.setTitle(title);
        bugReport.setText(text);
        bugReport.setIsFixed(0);
        bugReport.setReportTime(new Date());
        Integer ans = bugReportedService.insertBugReport(bugReport);
        if (1 == ans) {
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        } else {
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL);
        }
        return resultJsonVO;
    }
}
