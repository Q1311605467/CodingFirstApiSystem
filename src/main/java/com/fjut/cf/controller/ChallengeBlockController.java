package com.fjut.cf.controller;

import com.fjut.cf.component.interceptor.PrivateRequired;
import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.po.ChallengeBlockConditionPO;
import com.fjut.cf.pojo.vo.*;
import com.fjut.cf.service.ChallengeBlockProblemService;
import com.fjut.cf.service.ChallengeBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@RestController
@CrossOrigin
@RequestMapping("/challenge_block")
public class ChallengeBlockController {
    @Autowired
    ChallengeBlockService challengeBlockService;

    @Autowired
    ChallengeBlockProblemService challengeBlockProblemService;

    @PrivateRequired
    @GetMapping("/graph/get")
    public ResultJsonVO getUserOpenedChallengeBlock(@RequestParam("username") String username) {

        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        List<UserChallengeBlockVO> userChallengeBlockVOS = challengeBlockService.queryUserChallengeBlockByUsername(username);
        List<ChallengeBlockConditionPO> challengeBlockConditionPOS = challengeBlockService.queryAllChallengeBlockCondition();
        resultJsonVO.addInfo(userChallengeBlockVOS);
        resultJsonVO.addInfo(challengeBlockConditionPOS);
        return resultJsonVO;
    }

    @GetMapping("/condition/get")
    public ResultJsonVO getBlockConditionByBlockId(@RequestParam("blockId") Integer blockId) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        List<ChallengeBlockConditionVO> conditions = challengeBlockService.queryChallengeBlockConditionByBlockId(blockId);
        resultJsonVO.addInfo(conditions);
        return resultJsonVO;
    }

    @PrivateRequired
    @GetMapping("/detail/get")
    public ResultJsonVO getChallengeBlockDetailByBlockId(@RequestParam("blockId") Integer blockId,
                                                         @RequestParam("username") String username) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        ChallengeBlockVO challengeBlockVO = challengeBlockService.queryChallengeBlockByBlockIdAndUsername(blockId, username);
        List<ChallengeBlockConditionVO> conditionVOS = challengeBlockService.queryChallengeBlockConditionByBlockId(blockId);
        resultJsonVO.addInfo(challengeBlockVO);
        resultJsonVO.addInfo(conditionVOS);
        return resultJsonVO;
    }

    @GetMapping("/problem/get")
    public ResultJsonVO getBlockProblems(@RequestParam("username") String username,
                                         @RequestParam("blockId") Integer blockId,
                                         @RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        Integer startIndex = (pageNum - 1) * pageSize;
        List<ChallengeBlockProblemVO> challengeBlockProblemVOS = challengeBlockProblemService.queryChallengeBlockProblemByBlockIdAscLimit(blockId, startIndex, pageSize);
        Integer count = challengeBlockProblemService.queryChallengeBlockProblemCountByBlockId(blockId);
        if (challengeBlockProblemVOS.size() == 0 && count == 0) {
            resultJsonVO.setStatus(ResultJsonCode.RESOURCE_NOT_EXIST, "本模块没有题目");
        }
        resultJsonVO.addInfo(challengeBlockProblemVOS);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }

}
