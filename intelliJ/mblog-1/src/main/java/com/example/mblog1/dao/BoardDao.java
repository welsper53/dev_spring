package com.example.mblog1.dao;

// import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Repository // 외부 라이브러리 사용 시 (과거)
@Service
public class BoardDao {
    Logger logger = LoggerFactory.getLogger(BoardDao.class);

    public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
        logger.info("boardList 호출");
        logger.info("pMap ===> " + pMap);
        List<Map<String,Object>> bList = null;

        // NullPointerException 방어코드 작성
        if (bList == null) {
            bList = new ArrayList<>();
            Map<String,Object> rMap = new HashMap<>();

            rMap.put("BM_TITLE", "공지사항 1");
            rMap.put("BM_WRITER", "김유신");
            bList.add(rMap);
            rMap = new HashMap<>();
            rMap.put("BM_TITLE", "공지사항 2");
            rMap.put("BM_WRITER", "이성계");
            bList.add(rMap);
            rMap = new HashMap<>();
            rMap.put("BM_TITLE", "공지사항 3");
            rMap.put("BM_WRITER", "강감찬");
            bList.add(rMap);
        }

        return bList;
    }
}
