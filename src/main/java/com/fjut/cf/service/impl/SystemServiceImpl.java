package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.SystemInfoMapper;
import com.fjut.cf.pojo.po.SystemInfoPO;
import com.fjut.cf.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author axiang [2019/10/30]
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    SystemInfoMapper systemInfoMapper;

    @Override
    public SystemInfoPO querySystemInfoByName(String name) {
        return systemInfoMapper.querySystemInfoByName(name);
    }
}
