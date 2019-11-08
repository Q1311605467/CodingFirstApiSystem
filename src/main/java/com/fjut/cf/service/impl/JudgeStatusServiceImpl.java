package com.fjut.cf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fjut.cf.component.judge.local.LocalJudgeHttpClient;
import com.fjut.cf.mapper.*;
import com.fjut.cf.pojo.enums.CodeLanguage;
import com.fjut.cf.pojo.enums.SubmitResult;
import com.fjut.cf.pojo.po.JudgeResultPO;
import com.fjut.cf.pojo.po.JudgeStatusPO;
import com.fjut.cf.pojo.po.ViewJudgeStatusPO;
import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.StatusCountVO;
import com.fjut.cf.service.JudgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
@Service
public class JudgeStatusServiceImpl implements JudgeStatusService {
    @Autowired
    UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    ProblemInfoMapper problemInfoMapper;

    @Autowired
    JudgeStatusMapper judgeStatusMapper;

    @Autowired
    JudgeResultMapper judgeResultMapper;

    @Autowired
    ViewJudgeStatusMapper viewJudgeStatusMapper;

    @Autowired
    LocalJudgeHttpClient localJudgeHttpClient;

    @Override
    public Integer insertJudgeStatus(JudgeStatusPO judgeStatusPO) {
        return judgeStatusMapper.insertJudgeStatus(judgeStatusPO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateJudgeStatusWhenSubmitSuccess(JudgeStatusPO judgeStatusPO) {
        // 题目ID的题目提交总数+1
        problemInfoMapper.updateProblemInfoTotalSubmitAddOne(judgeStatusPO.getProblemId());
        Integer count = judgeStatusMapper.queryAllJudgeStatusByUsernameAndProblemId(judgeStatusPO.getUsername(), judgeStatusPO.getProblemId());
        // 如果用户之前未提交该题，则题目ID的提交用户总数+1
        if (count == 0) {
            problemInfoMapper.updateProblemInfoTotalSubmitUserAddOne(judgeStatusPO.getProblemId());
        }
        return true;
    }

    @Override
    public Boolean updateJudgeStatusWhenSubmitFail(Integer judgeId) {
        // 更新评测记录中的评测结果为ERROR （提交失败）
        judgeStatusMapper.updateJudgeStatusResultById(judgeId, SubmitResult.ERROR.getCode());
        // 更新评测结果内容
        JudgeResultPO judgeResultPO = new JudgeResultPO();
        judgeResultPO.setJudgeId(judgeId);
        judgeResultPO.setInfo("submit to local judge system fail at " + new Date().toString());
        judgeResultMapper.insertJudgeResult(judgeResultPO);
        return true;
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void queryResultFromLocalJudgeAsync(JudgeStatusPO judgeStatusPO) throws Exception {
        // 评测结果
        String judgingStr = "judging";
        boolean quitLoop = false;
        int times = 100;
        // TODO: 可以根据活跃线程数executor.getActiveCount()来设置获取时间间隔times
        do {
            String getResultJsonStr = null;
            getResultJsonStr = localJudgeHttpClient.getResultFromLocalJudge(judgeStatusPO.getId());
            JSONObject jsonObject = JSONObject.parseObject(getResultJsonStr);
            if ("success".equals(jsonObject.getString("ret"))) {
                JSONObject resultJsonObj = JSONObject.parseObject(jsonObject.getString("result"));
                judgingStr = resultJsonObj.getString("type");
                // 如果代码在评测等待中，则更新评测结果为评测等待
                if ("padding".equals(judgingStr)) {
                    judgeStatusMapper.updateJudgeStatusResultById(judgeStatusPO.getId(), SubmitResult.PENDING.getCode());
                }
                // 如果代码在评测中，则更新状态
                else if ("judging".equals(judgingStr)) {
                    judgeStatusMapper.updateJudgeStatusResultById(judgeStatusPO.getId(), SubmitResult.JUDGING.getCode());
                }
                // 如果代码评测结果为编译失败，则更新状态
                else if ("CE".equals(judgingStr)) {
                    // 插入数据库表中评测结果为CE
                    JudgeResultPO judgeResultPO = new JudgeResultPO();
                    judgeResultPO.setJudgeId(judgeStatusPO.getId());
                    judgeResultPO.setInfo(resultJsonObj.getString("info"));
                    judgeResultMapper.insertJudgeResult(judgeResultPO);
                    judgeStatusMapper.updateJudgeStatusResultById(judgeStatusPO.getId(), SubmitResult.CE.getCode());
                    // 退出循环
                    quitLoop = true;
                }
                //以下为编译正确返回的内容，即提交并且编译成功得到的结果，但不一定为AC
                else {
                    JSONArray retJsonArr = resultJsonObj.getJSONArray("ret");
                    // 评测机返回多组不同IO的评测记录，交由处理反馈结果
                    judgingStr = handleLocalJudgeReturns(retJsonArr, judgeStatusPO);
                    quitLoop = true;
                }
            } else {
                quitLoop = true;
            }
            Thread.sleep(2000);
            times--;
        } while (times > 0 && !quitLoop);
        // 200s的获取结果执行完毕或者拿到AC/CE编译的结果后执行
        // 如果用户AC了这道题，执行的逻辑
        if ("AC".equalsIgnoreCase(judgingStr)) {
            // 题目 AC 数量加一
            problemInfoMapper.updateProblemInfoTotalAcAddOne(judgeStatusPO.getProblemId());
            // 查询用户以前是否解决过该题
            Integer count = judgeStatusMapper.queryAcJudgeStatusByUsernameAndProblemId(judgeStatusPO.getUsername(), judgeStatusPO.getProblemId());
            if (count == 0) {
                // 用户AC数量加一
                userBaseInfoMapper.updateUserBaseInfoAcNumAddOneByUsername(judgeStatusPO.getUsername());
                // 题目AC用户数量加一
                problemInfoMapper.updateProblemInfoTotalAcUserAddOne(judgeStatusPO.getProblemId());
            }
            // TODO: 对挑战模式的更新逻辑
        } else {
            // 用户尝试过该题目，但没有解决

        }
    }

    /**
     * 处理本地评测机返回值的业务
     *
     * @param retJsonArr
     * @param judgeStatusPO
     * @return
     */
    private String handleLocalJudgeReturns(JSONArray retJsonArr, JudgeStatusPO judgeStatusPO) {
        JudgeResultPO judgeResultPO = new JudgeResultPO();
        String ans;
        String resultStr = "";
        String resStatus = "";
        int time = 0;
        int memory = 0;
        // 如果是计分类题，获得的得分
        int getScore = 0;
        // 是否是计分类题
        boolean isScore = false;
        for (int i = 0; i < retJsonArr.size(); i++) {
            resStatus = retJsonArr.getJSONArray(i).getString(1);
            resultStr += ("测试结果：【" + resStatus + "】 ");
            resultStr += ("测试文件：【" + retJsonArr.getJSONArray(i).getString(0) + "】 ");
            if ("SC".equals(resStatus)) {
                int score = retJsonArr.getJSONArray(i).getInteger(5);
                getScore += score;
                resultStr += ("得分：【" + score + "】 ");
                isScore = true;

                time += retJsonArr.getJSONArray(i).getInteger(2);
                resultStr += ("用时：【" + retJsonArr.getJSONArray(i).getInteger(2) + "MS】 ");
            } else if ("MLE".equals(resStatus) || "OLE".equals(resStatus)) {
                time += retJsonArr.getJSONArray(i).getInteger(4);
                resultStr += ("用时：【" + retJsonArr.getJSONArray(i).getInteger(4) + "MS】 ");
            } else {
                time += retJsonArr.getJSONArray(i).getInteger(2);
                resultStr += ("用时：【" + retJsonArr.getJSONArray(i).getInteger(2) + "MS】 ");
            }
            memory = Math.max(memory, retJsonArr.getJSONArray(i).getInteger(3));
            resultStr += ("内存：【" + retJsonArr.getJSONArray(i).getInteger(3) + "KB】\n");
        }
        judgeResultPO.setInfo(resultStr);
        judgeResultPO.setJudgeId(judgeStatusPO.getId());
        judgeResultMapper.insertJudgeResult(judgeResultPO);
        // 如果是得分题
        if (isScore) {
            judgeStatusPO.setScore(getScore / retJsonArr.size());
            judgeStatusPO.setResult(SubmitResult.SC.getCode());
            ans = "SC";
        } else {
            judgeStatusPO.setResult(SubmitResult.valueOf(resStatus).getCode());
            ans = resStatus;
        }
        judgeStatusPO.setTimeUsed(time + "MS");
        judgeStatusPO.setMemoryUsed(memory + "KB");
        judgeStatusMapper.updateJudgeStatusAfterJudgedById(judgeStatusPO);
        return ans;
    }

    @Override
    public List<StatusCountVO> queryStatusCountByDayAsc(int days) {
        List<StatusCountVO> statusCountVOS = judgeStatusMapper.queryStatusCountByDayAsc(days);
        return judgeStatusMapper.queryStatusCountByDayAsc(days);
    }

    @Override
    public List<JudgeStatusVO> queryJudgeStatusDescLimit(Integer startIndex, Integer pageSize, String nick, Integer problemId, Integer result, Integer language) {
        List<JudgeStatusVO> judgeStatusVOS = new ArrayList<>();
        List<ViewJudgeStatusPO> viewJudgeStatusPOS = viewJudgeStatusMapper.queryViewJudgeStatusDescLimit(startIndex, pageSize, nick, problemId, result, language);
        for (ViewJudgeStatusPO vjs : viewJudgeStatusPOS) {
            JudgeStatusVO judgeStatusVO = new JudgeStatusVO();
            judgeStatusVO.setId(vjs.getId());
            judgeStatusVO.setNick(vjs.getNick());
            judgeStatusVO.setUsername(vjs.getUsername());
            judgeStatusVO.setCodeLength(vjs.getCodeLength());
            judgeStatusVO.setLanguage(CodeLanguage.getNameByCode(vjs.getLanguage()));
            judgeStatusVO.setTimeUsed(vjs.getTimeUsed());
            judgeStatusVO.setMemoryUsed(vjs.getMemoryUsed());
            judgeStatusVO.setProblemId(vjs.getProblemId());
            judgeStatusVO.setSubmitTime(vjs.getSubmitTime());
            // 如果是Score的，带上分值
            if (vjs.getResult() == SubmitResult.SC.getCode()) {
                judgeStatusVO.setResult(SubmitResult.getNameByCode(vjs.getResult()) + " " + vjs.getScore());
            } else {
                judgeStatusVO.setResult(SubmitResult.getNameByCode(vjs.getResult()));
            }
            judgeStatusVOS.add(judgeStatusVO);
        }
        return judgeStatusVOS;
    }

    @Override
    public Integer queryViewJudgeStatusCount(String nick, Integer problemId, Integer result, Integer language) {
        return viewJudgeStatusMapper.queryViewJudgeStatusCount(nick, problemId, result, language);
    }
}
