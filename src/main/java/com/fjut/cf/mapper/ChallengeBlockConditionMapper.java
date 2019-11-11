package com.fjut.cf.mapper;

import com.fjut.cf.pojo.po.ChallengeBlockConditionPO;
import com.fjut.cf.pojo.vo.ChallengeBlockConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
public interface ChallengeBlockConditionMapper {
    /**
     * 查询模块之间的关系
     *
     * @return
     */
    List<ChallengeBlockConditionPO> queryAllChallengeBlockCondition();


    /**
     * 查询模块解锁条件
     *
     * @param blockId
     * @return
     */
    List<ChallengeBlockConditionVO> queryChallengeBlockConditionByBlockId(@Param("blockId") Integer blockId);

    /**
     * 查询后置模块
     *
     * @param blockId
     * @return
     */
    List<Integer> queryRearBlocksByBlockId(@Param("blockId") Integer blockId);
}
