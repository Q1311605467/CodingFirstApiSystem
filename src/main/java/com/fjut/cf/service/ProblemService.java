package com.fjut.cf.service;

import com.fjut.cf.pojo.po.ProblemInfoPO;
import com.fjut.cf.pojo.po.ProblemSamplePO;
import com.fjut.cf.pojo.po.ProblemViewPO;
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

    /**
     * 根据题目ID查询题目基本信息
     * @param problemId
     * @return
     */
    ProblemInfoPO queryProblemInfoByProblemId(Integer problemId);

    /**
     * 根据题目ID查询题目视图信息内容
     * @param problemId
     * @return
     */
    ProblemViewPO queryProblemViewByProblemId(Integer problemId);

    /**
     * 根据题目ID查询题目输入输出样例
     * @param problemId
     * @return
     */
    List<ProblemSamplePO> queryProblemSampleByProblemId(Integer problemId);

}
