package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.BorderHonorRankMapper;
import com.fjut.cf.pojo.enums.AwardLevel;
import com.fjut.cf.pojo.enums.ContestLevel;
import com.fjut.cf.pojo.po.BorderHonorRankPO;
import com.fjut.cf.pojo.vo.BorderHonorRankVO;
import com.fjut.cf.service.BorderHonorRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@Service
public class BorderHonorRankServiceImpl implements BorderHonorRankService {
    @Autowired
    BorderHonorRankMapper borderHonorRankMapper;

    @Override
    public List<BorderHonorRankVO> queryBorderHonorRankDescLimit(Integer startIndex, Integer pageSize) {
        List<BorderHonorRankVO> results = new ArrayList<>();
        List<BorderHonorRankPO> borderHonorRankPOS = borderHonorRankMapper.queryBorderHonorRankDescLimit(startIndex, pageSize);
        for(BorderHonorRankPO item : borderHonorRankPOS)
        {
            BorderHonorRankVO borderHonorRankVO = new BorderHonorRankVO();
            borderHonorRankVO.setContestLevel(ContestLevel.getNameByCode(item.getContestLevel()));
            borderHonorRankVO.setAwardLevel(AwardLevel.getNameByCode(item.getAwardLevel()));
            borderHonorRankVO.setDescription(item.getDescription());
            borderHonorRankVO.setId(item.getId());
            borderHonorRankVO.setRealNameOne(item.getRealNameOne());
            borderHonorRankVO.setRealNameTwo(item.getRealNameTwo());
            borderHonorRankVO.setRealNameThree(item.getRealNameThree());
            borderHonorRankVO.setUsernameOne(item.getUsernameOne());
            borderHonorRankVO.setUsernameTwo(item.getUsernameTwo());
            borderHonorRankVO.setUsernameThree(item.getUsernameThree());
            borderHonorRankVO.setRewardDate(item.getRewardDate());
            borderHonorRankVO.setRegisterTime(item.getRegisterTime());
            results.add(borderHonorRankVO);
        }
        return results;
    }

    @Override
    public Integer queryBorderHonorRankCount() {
        return borderHonorRankMapper.queryBorderHonorRankCount();
    }
}
