package com.fjut.cf.service;

import com.fjut.cf.pojo.vo.BorderHonorRankVO;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface BorderHonorRankService {
    /**
     * 分页查询榜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<BorderHonorRankVO> queryBorderHonorRankDescLimit(Integer startIndex, Integer pageSize);

    /**
     * 查询榜单记录数
     * @return
     */
    Integer queryBorderHonorRankCount();

    /**
     * 根据用户名查询用户参与榜单字符串
     *
     * @param username
     * @return
     */
    List<String> queryBorderHonorRankByUsername(String username);

}
