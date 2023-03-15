package com.example.mblog1.logic;

import com.example.mblog1.dao.MemberDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberLogic {
    Logger logger = LoggerFactory.getLogger(MemberLogic.class);

    @Autowired
    private MemberDao memberDao = null;

    public int memberInsert(Map<String, Object> pMap) {
        logger.info("memberInsert 호출");
        logger.info(pMap.toString());

        int result = 0;
        result = memberDao.memberInsert(pMap);

        return result;
    }
}
