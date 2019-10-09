package com.fjut.game.service;

import com.fjut.game.pojo.CeInfoPO;
import java.util.List;

/**
 * @author axiang [2019/10/8]
 */
public interface CeInfoService {
    /**
     * 查询全部的评测结果信息
     * @return
     */
    List<CeInfoPO> queryAllCeInfo();
}
