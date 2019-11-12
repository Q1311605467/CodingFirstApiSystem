package com.fjut.cf.controller;

import com.fjut.cf.pojo.vo.BorderHonorRankVO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.service.BorderHonorRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@RestController
@CrossOrigin
@RequestMapping("/border")
public class BorderController {
    @Autowired
    BorderHonorRankService borderHonorRankService;


    @GetMapping("/honor_rank")
    public ResultJsonVO getHonorRankList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize) {
        ResultJsonVO resultJsonVO = new ResultJsonVO();
        Integer startIndex = (pageNum - 1) * pageSize;
        List<BorderHonorRankVO> borderHonorRankVOS = borderHonorRankService.queryBorderHonorRankDescLimit(startIndex, pageSize);
        Integer count  = borderHonorRankService.queryBorderHonorRankCount();
        resultJsonVO.addInfo(borderHonorRankVOS);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }


}
