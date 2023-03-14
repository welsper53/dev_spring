package com.example.mblog1.logic;

import com.example.mblog1.dao.BoardDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BoardLogic {
    Logger logger = LoggerFactory.getLogger(BoardLogic.class);

    @Autowired
    private BoardDao boardDao = null;

    public List<Map<String, Object>> boardLogic(Map<String, Object> pMap) {
        logger.info("boardLogic 호출");

        List<Map<String,Object>> bList = new ArrayList<>();
        bList = boardDao.boardList(pMap);

        return bList;
    }
}
