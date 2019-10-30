package com.fjut.cf.service;

import com.fjut.cf.pojo.po.SystemInfoPO;

/**
 * @author axiang [2019/10/30]
 */
public interface SystemService {
    /**
     * 根据名字查询系统字段
     *
     * @param name
     * @return
     */
    SystemInfoPO querySystemInfoByName(String name);

}
