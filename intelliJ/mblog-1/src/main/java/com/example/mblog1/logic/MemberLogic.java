package com.example.mblog1.logic;

import com.example.mblog1.dao.MemberDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/* 모델 계층
*  : MemberLogic[직접 오라클과 연동하지 않음] + MemberDao[데이터셋]
* 공통된 관심사 분리 (AspectJ 프레임워크 - 오픈소스)
* 트랜잭션 처리 지원 받음
* */
@Service
public class MemberLogic {
    Logger logger = LoggerFactory.getLogger(MemberLogic.class);

    @Autowired
    private MemberDao memberDao = null;

    public int memberInsert(Map<String, Object> pMap) {
        logger.info("memberInsert 호출");

        int result = 0;
        result = memberDao.memberInsert(pMap);

        return result;
    }

    public List<Map<String,Object>> memberList(Map<String, Object> pMap) {
        logger.info("memberList 호출");

        List<Map<String,Object>> mList = null;

        mList = memberDao.memberList(pMap);

        return mList;
    }

    public int memberUpdate(Map<String, Object> pMap) {
        logger.info("memberUpdate 호출");

        int result = 0;
        result = memberDao.memberUpdate(pMap);

        return result;
    }

    public int memberDelete(Map<String, Object> pMap) {
        logger.info("memberDelete 호출");

        int result = 0;
        result = memberDao.memberDelete(pMap);

        return result;
    }
}
