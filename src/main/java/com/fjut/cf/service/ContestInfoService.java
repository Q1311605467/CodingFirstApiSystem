package com.fjut.cf.service;

import com.fjut.cf.pojo.vo.ContestListVO;

import java.util.List;

/**
 * @author axiang [2019/11/18]
 */
public interface ContestInfoService {

    /**
     * 根据条件分页查询不同类型比赛列表
     *
     * @param kind
     * @param searchTitle
     * @param searchPermission
     * @param searchStatus
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ContestListVO> queryContestListByConditionsDescLimit(Integer kind,
                                                              String searchTitle,
                                                              Integer searchPermission,
                                                              Integer searchStatus,
                                                              Integer startIndex,
                                                              Integer pageSize);

    /**
     * 根据条件查询不同比赛类型记录数
     *
     * @param kind
     * @param searchTitle
     * @param searchPermission
     * @param searchStatus
     * @return
     */
    Integer queryContestInfoCountByKind(Integer kind,
                                        String searchTitle,
                                        Integer searchPermission,
                                        Integer searchStatus);
}
