package com.fjut.cf.controller;

import com.alibaba.fastjson.JSONObject;
import com.fjut.cf.component.interceptor.PrivateRequired;
import com.fjut.cf.component.judge.local.LocalJudgeHttpClient;
import com.fjut.cf.pojo.bo.LocalJudgeSubmitInfoBO;
import com.fjut.cf.pojo.enums.CodeLanguage;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.enums.SubmitResult;
import com.fjut.cf.pojo.po.JudgeStatusPO;
import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.pojo.vo.StatusCountVO;
import com.fjut.cf.service.JudgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    LocalJudgeHttpClient localJudgeHttpClient;

    @GetMapping("/list/get")
    public ResultJsonVO getStatusList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "nick", required = false) String nick,
                                      @RequestParam(value = "problemId", required = false) String problemIdStr,
                                      @RequestParam(value = "result", required = false) String resultStr,
                                      @RequestParam(value = "language", required = false) String languageStr) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = 50;
        }
        Integer startIndex = (pageNum - 1) * pageSize;
        if (!StringUtils.isEmpty(nick)) {
            nick = "%" + nick + "%";
        } else {
            nick = null;
        }
        Integer problemId = null;
        Integer result = null;
        Integer language = null;
        if (!StringUtils.isEmpty(problemIdStr)) {
            problemId = Integer.parseInt(problemIdStr);
        }
        if (!StringUtils.isEmpty(resultStr)) {
            result = SubmitResult.getCodeByName(resultStr);
        }
        if (!StringUtils.isEmpty(languageStr)) {
            language = CodeLanguage.getCodeByName(languageStr);
        }
        List<JudgeStatusVO> judgeStatusVOS = judgeStatusService.queryJudgeStatusDescLimit(startIndex, pageSize, nick, problemId, result, language);
        Integer length = judgeStatusService.queryViewJudgeStatusCount(nick, problemId, result, language);
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

    /**
     * TODO: 本地判题
     *
     * @param pid
     * @param timeLimit
     * @param MemoryLimit
     * @param code
     * @param languageStr
     * @param username
     * @param cid
     * @return
     */
    @PrivateRequired
    @PostMapping("/submit/post")
    public ResultJsonVO submitToLocalJudge(@RequestParam("pid") Integer pid,
                                           @RequestParam("timeLimit") Integer timeLimit,
                                           @RequestParam("memoryLimit") Integer MemoryLimit,
                                           @RequestParam("code") String code,
                                           @RequestParam("language") String languageStr,
                                           @RequestParam("username") String username,
                                           @RequestParam(value = "cid", required = false) Integer cid) throws Exception {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Date currentTime = new Date();
        // 目前支持语言 JAVA Python2 C/C++
        int language = ("JAVA").equalsIgnoreCase(languageStr) ? 2 : ("Python").equalsIgnoreCase(languageStr) ? 3 : 1;
        if (null == cid) {
            cid = -1;
        }
        // TODO: 如果是赛题
        if (cid != -1) {

        }
        /*
           业务逻辑如下：
           1. 将评测记录插入数据库中
           2. 提交到评测机评测
           3. 把取结果的业务放到线程池中去做
         */
        JudgeStatusPO judgeStatusPO = new JudgeStatusPO();
        judgeStatusPO.setUsername(username);
        judgeStatusPO.setProblemId(pid);
        judgeStatusPO.setContestId(cid);
        judgeStatusPO.setLanguage(language);
        judgeStatusPO.setSubmitTime(currentTime);
        judgeStatusPO.setResult(SubmitResult.PENDING.getCode());
        // 默认为非分值题
        judgeStatusPO.setScore(-1);
        judgeStatusPO.setTimeUsed("-");
        judgeStatusPO.setMemoryUsed("-");
        judgeStatusPO.setCode(code);
        judgeStatusPO.setCodeLength(code.length());
        // 插入数据库中
        judgeStatusService.insertJudgeStatus(judgeStatusPO);
        String type = "submit";
        // 获得插入后的rid
        Integer rid = judgeStatusPO.getId();
        LocalJudgeSubmitInfoBO localJudgeSubmitInfoBO = new LocalJudgeSubmitInfoBO();
        localJudgeSubmitInfoBO.setType(type);
        localJudgeSubmitInfoBO.setPid(pid);
        localJudgeSubmitInfoBO.setRid(rid);
        localJudgeSubmitInfoBO.setMemoryLimit(MemoryLimit);
        localJudgeSubmitInfoBO.setTimeLimit(timeLimit);
        localJudgeSubmitInfoBO.setCode(code);
        localJudgeSubmitInfoBO.setLanguageId(language);
        String submitJsonStr = "";
        try {
            submitJsonStr = localJudgeHttpClient.submitToLocalJudge(localJudgeSubmitInfoBO);
        } catch (Exception e) {
            // 请求评测机出现异常，返回失败状态
            judgeStatusService.updateJudgeStatusWhenSubmitFail(localJudgeSubmitInfoBO.getRid());
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL,"提交代码到本地评测机失败！");
            return resultJsonVO;
        }
        JSONObject jsonObject = JSONObject.parseObject(submitJsonStr);
        // 如果提交到本地评测机成功，则
        if ("success".equals(jsonObject.getString("ret"))) {
            // 更新数据库表
            judgeStatusService.updateJudgeStatusWhenSubmitSuccess(judgeStatusPO);
            // 启用异步Service获取结果
            judgeStatusService.queryResultFromLocalJudgeAsync(judgeStatusPO);
            resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS,"提交评测成功！");
        }
        // 如果请求评测机成功，但评测机返回失败结论
        else {
            // 更新数据库表，返回结果
            judgeStatusService.updateJudgeStatusWhenSubmitFail(localJudgeSubmitInfoBO.getRid());
            resultJsonVO.setStatus(ResultJsonCode.BUSINESS_FAIL,"提交代码到本地评测机失败！");
        }
        return resultJsonVO;
    }
}
