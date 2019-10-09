package com.fjut.game.service.impl;

import com.fjut.game.mapper.CeInfoMapper;
import com.fjut.game.pojo.CeInfoPO;
import com.fjut.game.service.CeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author axiang [2019/10/8]
 */
@Service
public class CeInfoServiceImpl implements CeInfoService {
    @Autowired
    private CeInfoMapper ceInfoMapper;

    @Override
    public List<CeInfoPO> queryAllCeInfo() {
        return ceInfoMapper.queryAllCeInfo();
    }
}
