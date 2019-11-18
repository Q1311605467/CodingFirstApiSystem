package com.fjut.cf.controller;

import com.fjut.cf.pojo.enums.ResultJsonCode;
import com.fjut.cf.pojo.vo.ContestListVO;
import com.fjut.cf.pojo.vo.ResultJsonVO;
import com.fjut.cf.service.ContestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author axiang [2019/11/18]
 */
@RestController
@CrossOrigin
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    ContestInfoService contestInfoService;

    @GetMapping("/list/get")
    public ResultJsonVO getContestList(@RequestParam("kind") Integer kind,
                                       @RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "searchName", required = false) String searchName,
                                       @RequestParam(value = "searchPermission", required = false) Integer searchPermission,
                                       @RequestParam(value = "searchStatus", required = false) Integer searchStatus) {
        ResultJsonVO resultJsonVO = new ResultJsonVO(ResultJsonCode.REQUIRED_SUCCESS);
        Integer startIndex = (pageNum - 1) * pageSize;
        if (StringUtils.isEmpty(searchName)) {
            searchName = null;
        } else {
            searchName = "%" + searchName + "%";
        }
        List<ContestListVO> contestListVOS = contestInfoService.queryContestListByConditionsDescLimit(kind, searchName, searchPermission, searchStatus, startIndex, pageSize);
        Integer count = contestInfoService.queryContestInfoCountByKind(kind, searchName, searchPermission, searchStatus);
        resultJsonVO.addInfo(contestListVOS);
        resultJsonVO.addInfo(count);
        return resultJsonVO;
    }

}
