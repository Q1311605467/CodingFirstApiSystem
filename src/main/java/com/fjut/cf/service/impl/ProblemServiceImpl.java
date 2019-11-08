package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.ProblemInfoMapper;
import com.fjut.cf.mapper.ProblemMapper;
import com.fjut.cf.mapper.ProblemSampleMapper;
import com.fjut.cf.mapper.ProblemViewMapper;
import com.fjut.cf.pojo.enums.OjId;
import com.fjut.cf.pojo.enums.ProblemDifficultLevel;
import com.fjut.cf.pojo.po.ProblemInfoPO;
import com.fjut.cf.pojo.po.ProblemInfoWithDifficultPO;
import com.fjut.cf.pojo.po.ProblemSamplePO;
import com.fjut.cf.pojo.po.ProblemViewPO;
import com.fjut.cf.pojo.vo.ProblemListVO;
import com.fjut.cf.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author axiang [2019/10/22]
 */
@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemInfoMapper problemInfoMapper;

    @Autowired
    ProblemViewMapper problemViewMapper;

    @Autowired
    ProblemSampleMapper problemSampleMapper;

    @Autowired
    ProblemMapper problemMapper;


    @Override
    public List<ProblemListVO> queryProblemListLimit(String title, Integer tagId, Integer startIndex, Integer pageSize) {
        List<ProblemListVO> problemListVOS = new ArrayList<>();
        List<ProblemInfoWithDifficultPO> problemInfoWithDifficultPOS = problemMapper.queryProblemInfoWithDifficultAscLimit(title, tagId, startIndex, pageSize);
        for (ProblemInfoWithDifficultPO problemInfo : problemInfoWithDifficultPOS) {
            ProblemListVO problemListVO = new ProblemListVO();
            //FIXME: 是否解决暂时置未解决
            problemListVO.setIsSolved("？");
            problemListVO.setProblemId(problemInfo.getProblemId());
            problemListVO.setTitle(problemInfo.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String ratio;
            if (problemInfo.getTotalSubmit() == 0) {
                ratio = "0.00%(0/0)";
            } else {
                ratio = decimalFormat.format(100.00 * problemInfo.getTotalAc() / problemInfo.getTotalSubmit()) + "%("
                        + problemInfo.getTotalAc() + "/" + problemInfo.getTotalSubmit() + ")";
            }
            problemListVO.setRatio(ratio);
            problemListVO.setDifficult(ProblemDifficultLevel.getNameByCode(problemInfo.getDifficultLevel()));
            problemListVO.setBelongToOj(OjId.getNameByCode(problemInfo.getBelongOjId()));
            problemListVOS.add(problemListVO);
        }
        return problemListVOS;
    }

    @Override
    public Integer queryProblemTotalCount(String title, Integer tagId) {
        return problemInfoMapper.queryProblemInfoCount(title, tagId);
    }

    @Override
    public ProblemInfoPO queryProblemInfoByProblemId(Integer problemId) {
        return problemInfoMapper.queryProblemInfoByProblemId(problemId);
    }

    @Override
    public ProblemViewPO queryProblemViewByProblemId(Integer problemId) {
        return problemViewMapper.queryProblemViewByProblemId(problemId);
    }

    @Override
    public List<ProblemSamplePO> queryProblemSampleByProblemId(Integer problemId) {
        return problemSampleMapper.queryProblemSampleByProblemId(problemId);
    }
}
