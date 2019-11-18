package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.*;
import com.fjut.cf.pojo.enums.OjId;
import com.fjut.cf.pojo.enums.ProblemDifficultLevel;
import com.fjut.cf.pojo.po.*;
import com.fjut.cf.pojo.vo.ProblemListVO;
import com.fjut.cf.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

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

    @Autowired
    UserProblemSolvedMapper userProblemSolvedMapper;


    @Override
    public List<ProblemListVO> queryProblemListByConditionsDescLimit(String username, String title, Integer tagId, Integer startIndex, Integer pageSize) {
        List<ProblemListVO> problemListVOS = new ArrayList<>();
        boolean needSolvedStatus = false;
        List<ProblemInfoWithDifficultPO> problemInfoWithDifficultPOS = problemMapper.queryProblemInfoWithDifficultAscLimit(title, tagId, startIndex, pageSize);
        Map<Integer, Integer> map = new TreeMap<>();
        if(!StringUtils.isEmpty(username))
        {
            needSolvedStatus  =true;
            List<UserProblemSolvedPO> solvedProblems = userProblemSolvedMapper.queryUserProblemSolvedByUsername(username);

            for (UserProblemSolvedPO solvedProblem : solvedProblems) {
                map.put(solvedProblem.getProblemId(), solvedProblem.getSolvedCount());
            }
        }
        for (ProblemInfoWithDifficultPO problemInfo : problemInfoWithDifficultPOS) {
            ProblemListVO problemListVO = new ProblemListVO();
            String isSolved = "";
            if(needSolvedStatus)
            {
                if (map.get(problemInfo.getProblemId()) == null) {
                    isSolved = "";
                } else if (map.get(problemInfo.getProblemId()) >= 1) {
                    isSolved = "✔";
                }
                else{
                    isSolved = "X";
                }
            }
            problemListVO.setIsSolved(isSolved);
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
