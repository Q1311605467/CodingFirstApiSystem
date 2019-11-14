package com.fjut.cf.controller;

import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.po.ProblemInfoPO;
import com.fjut.cf.pojo.po.ProblemSamplePO;
import com.fjut.cf.pojo.po.ProblemViewPO;
import com.fjut.cf.pojo.vo.ProblemListVO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.service.ProblemService;
import com.fjut.cf.service.ProblemTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author axiang [2019/10/22]
 */
@RestController
@RequestMapping("/problem")
@CrossOrigin
public class ProblemController {
    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemTagService problemTagService;

    @GetMapping("/list/get")
    public ResultJsonVO getProblemLimit(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam(value = "tagId", required = false) Integer tagId,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "username", required = false) String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 50;
        }
        Integer startIndex = (pageNum - 1) * pageSize;
        if (!StringUtils.isEmpty(title)) {
            // 拼接查询字符串
            title = "%" + title + "%";
        } else {
            // 拼接查询字符串如果为空字符或者null则 置为null
            title = null;
        }
        List<ProblemListVO> problemList = problemService.queryProblemListLimit(username, title, tagId, startIndex, pageSize);
        Integer integer = problemService.queryProblemTotalCount(title, tagId);
        resultJsonVO.addInfo(problemList);
        resultJsonVO.addInfo(integer);
        resultJsonVO.setStatus(ResultJsonCode.REQUIRED_SUCCESS);
        return resultJsonVO;
    }

    @GetMapping("/info/get")
    public ResultJsonVO getProblemInfoByProblemId(@RequestParam("problemId") Integer problemId) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        ProblemInfoPO problemInfoPO = problemService.queryProblemInfoByProblemId(problemId);
        ProblemViewPO problemViewPO = problemService.queryProblemViewByProblemId(problemId);
        List<ProblemSamplePO> problemSamplePOS = problemService.queryProblemSampleByProblemId(problemId);
        resultJsonVO.addInfo(problemInfoPO);
        resultJsonVO.addInfo(problemViewPO);
        resultJsonVO.addInfo(problemSamplePOS);
        return resultJsonVO;
    }


}
