package com.example.mblog1.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;


//@Repository
@Service
public class MemberDao {
    Logger logger = LoggerFactory.getLogger(MemberDao.class);

    public int memberInsert(Map<String, Object> pMap) {
        logger.info("memberInsert 호출");
        int result = 0;


        return result;
    }
}
