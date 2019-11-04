package com.fjut.cf.controller;

import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.pojo.vo.StatusCountVO;
import com.fjut.cf.service.JudgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author axiang [2019/10/30]
 */
@RestController
@CrossOrigin
@RequestMapping("/judge_status")
public class JudgeStatusController {
    @Autowired
    JudgeStatusService judgeStatusService;

    @GetMapping("/list/get")
    public ResultJsonVO getStatusList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = 50;
        }
        Integer startIndex = (pageNum - 1) * pageSize;
        List<JudgeStatusVO> judgeStatusVOS = judgeStatusService.queryJudgeStatusDescLimit(startIndex, pageSize);
        Integer length = judgeStatusService.queryViewJudgeStatusCount();
        resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        resultJsonVO.addInfo(judgeStatusVOS);
        resultJsonVO.addInfo(length);
        return resultJsonVO;
    }

    @GetMapping("/count/get")
    public ResultJsonVO getStatusCountByDay(@RequestParam(value = "days", required = false) String daysStr) {
        int days;
        days = daysStr == null ? 60 : Integer.parseInt(daysStr);
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        HashMap<Date, StatusCountVO> hashMap = new HashMap<>(105);
        for (int i = 0; i < days; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -days + 1 + i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            StatusCountVO statusCountVO = new StatusCountVO();
            statusCountVO.setTotalCount(0);
            statusCountVO.setAcCount(0);
            statusCountVO.setSubmitDay(calendar.getTime());
            hashMap.put(calendar.getTime(), statusCountVO);
        }
        List<StatusCountVO> statusCountVOS = judgeStatusService.queryStatusCountByDayAsc(days);
        for (StatusCountVO statusCountVO : statusCountVOS) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(statusCountVO.getSubmitDay());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            hashMap.put(calendar.getTime(), statusCountVO);
        }
        List<StatusCountVO> ans = new ArrayList<>();
        for (Date date : hashMap.keySet()) {
            StatusCountVO statusCountVO = hashMap.get(date);
            ans.add(statusCountVO);
        }
        Collections.sort(ans, new Comparator<StatusCountVO>() {
            @Override
            public int compare(StatusCountVO sc1, StatusCountVO sc2) {
                return sc1.getSubmitDay().compareTo(sc2.getSubmitDay()); //按时间升序
            }
        });
        resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        resultJsonVO.addInfo(ans);
        return resultJsonVO;
    }
}
