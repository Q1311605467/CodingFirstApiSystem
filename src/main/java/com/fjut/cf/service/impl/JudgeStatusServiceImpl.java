package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.JudgeStatusMapper;
import com.fjut.cf.pojo.vo.StatusCountVO;
import com.fjut.cf.service.JudgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author axiang [2019/10/30]
 */
@Service
public class JudgeStatusServiceImpl implements JudgeStatusService {
    @Autowired
    JudgeStatusMapper judgeStatusMapper;

    @Override
    public List<StatusCountVO> queryStatusCountByDayAsc(int days) {
        List<StatusCountVO> statusCountVOS = judgeStatusMapper.queryStatusCountByDayAsc(days);
        for(StatusCountVO statusCountVO: statusCountVOS)
        {
         System.out.println("xxxxx"+statusCountVO.toString());
        }
        return judgeStatusMapper.queryStatusCountByDayAsc(days);
    }
}
