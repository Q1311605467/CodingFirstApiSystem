package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.ContestInfoMapper;
import com.fjut.cf.pojo.enums.ContestKind;
import com.fjut.cf.pojo.enums.ContestPermission;
import com.fjut.cf.pojo.po.ContestInfoPO;
import com.fjut.cf.pojo.vo.ContestListVO;
import com.fjut.cf.service.ContestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author axiang [2019/11/18]
 */
@Service
public class ContestInfoServiceImpl implements ContestInfoService {
    @Autowired
    ContestInfoMapper contestInfoMapper;

    @Override
    public List<ContestListVO> queryContestListByConditionsDescLimit(Integer kind,
                                                                     String searchTitle,
                                                                     Integer searchPermission,
                                                                     Integer searchStatus,
                                                                     Integer startIndex,
                                                                     Integer pageSize) {
        List<ContestListVO> result = new ArrayList<>();
        List<ContestInfoPO> contestInfos = contestInfoMapper.queryContestInfoByConditionsDescLimit(kind, searchTitle, searchPermission, searchStatus, startIndex, pageSize);
        for (ContestInfoPO contestInfo : contestInfos) {
            ContestListVO contestList = new ContestListVO();
            contestList.setId(contestInfo.getId());
            contestList.setTitle(contestInfo.getTitle());
            contestList.setKind(ContestKind.getNameByCode(contestInfo.getContestKind()));
            contestList.setBeginTime(contestInfo.getBeginTime());
            contestList.setEndTime(contestInfo.getEndTime());
            contestList.setPermission(ContestPermission.getNameByCode(contestInfo.getPermissionType()));
            Date currentTime = new Date();
            if (contestInfo.getEndTime().compareTo(currentTime) < 0) {
                contestList.setStatus("已结束");
            } else if (contestInfo.getBeginTime().compareTo(currentTime) >= 0) {
                contestList.setStatus("未开始");
            } else {
                contestList.setStatus("正在进行");
            }
            result.add(contestList);
        }
        return result;
    }

    @Override
    public Integer queryContestInfoCountByKind(Integer kind,
                                               String searchTitle,
                                               Integer searchPermission,
                                               Integer searchStatus) {
        return contestInfoMapper.queryContestInfoCountByConditions(kind, searchTitle, searchPermission, searchStatus);
    }


}
