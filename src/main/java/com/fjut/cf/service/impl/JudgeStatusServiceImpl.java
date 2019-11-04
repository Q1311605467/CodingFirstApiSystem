package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.JudgeStatusMapper;
import com.fjut.cf.mapper.ViewJudgeStatusMapper;
import com.fjut.cf.pojo.enums.CodeLanguage;
import com.fjut.cf.pojo.enums.SubmitResult;
import com.fjut.cf.pojo.po.ViewJudgeStatusPO;
import com.fjut.cf.pojo.vo.JudgeStatusVO;
import com.fjut.cf.pojo.vo.StatusCountVO;
import com.fjut.cf.service.JudgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
@Service
public class JudgeStatusServiceImpl implements JudgeStatusService {
    @Autowired
    JudgeStatusMapper judgeStatusMapper;

    @Autowired
    ViewJudgeStatusMapper viewJudgeStatusMapper;

    @Override
    public List<StatusCountVO> queryStatusCountByDayAsc(int days) {
        List<StatusCountVO> statusCountVOS = judgeStatusMapper.queryStatusCountByDayAsc(days);
        for (StatusCountVO statusCountVO : statusCountVOS) {
            System.out.println("xxxxx" + statusCountVO.toString());
        }
        return judgeStatusMapper.queryStatusCountByDayAsc(days);
    }

    @Override
    public List<JudgeStatusVO> queryJudgeStatusDescLimit(Integer startIndex, Integer pageSize) {
        List<JudgeStatusVO> judgeStatusVOS = new ArrayList<>();
        List<ViewJudgeStatusPO> viewJudgeStatusPOS = viewJudgeStatusMapper.queryViewJudgeStatusDescLimit(startIndex, pageSize);
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
            judgeStatusVO.setResult(SubmitResult.getNameByCode(vjs.getResult()));
            judgeStatusVOS.add(judgeStatusVO);
        }
        return judgeStatusVOS;
    }

    @Override
    public Integer queryViewJudgeStatusCount() {
        return viewJudgeStatusMapper.queryViewJudgeStatusCount();
    }
}
