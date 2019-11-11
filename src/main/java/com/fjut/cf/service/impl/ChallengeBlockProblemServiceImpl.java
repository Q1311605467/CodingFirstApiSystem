package com.fjut.cf.service.impl;

import com.fjut.cf.mapper.ChallengeBlockProblemMapper;
import com.fjut.cf.pojo.vo.ChallengeBlockProblemVO;
import com.fjut.cf.service.ChallengeBlockProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author axiang [2019/11/11]
 */
@Service
public class ChallengeBlockProblemServiceImpl implements ChallengeBlockProblemService {
    @Autowired
    ChallengeBlockProblemMapper challengeBlockProblemMapper;

    @Override
    public List<ChallengeBlockProblemVO> queryChallengeBlockProblemByBlockIdAscLimit(Integer blockId, Integer startIndex, Integer pageSize) {
        return challengeBlockProblemMapper.queryChallengeBlockProblemByBlockIdAscLimit(blockId, startIndex, pageSize);
    }

    @Override
    public Integer queryChallengeBlockProblemCountByBlockId(Integer blockId) {
        return challengeBlockProblemMapper.queryChallengeBlockProblemCountByBlockId(blockId);
    }
}
