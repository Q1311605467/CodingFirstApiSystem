package com.fjut.cf.service;

import com.fjut.cf.pojo.vo.ProblemListVO;

import java.util.List;

/**
 * @author axiang [2019/10/22]
 */
public interface ProblemService {
    /**
     * 分页带条件查询题目展示详情
     *
     * @param title
     * @param tagId
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ProblemListVO> queryProblemListLimit(String title, Integer tagId, Integer startIndex, Integer pageSize);

    /**
     * 带条件查询题目数量
     *
     * @param title
     * @param tagId
     * @return
     */
    Integer queryProblemTotalCount(String title, Integer tagId);


}
