package com.example.mblog1.logic;

import com.example.mblog1.dao.DeptDao;
import com.example.mblog1.vo.DeptVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class DeptLogic {
    Logger logger = LoggerFactory.getLogger(DeptLogic.class);

    @Autowired
    DeptDao deptDao = null;

    public int deptInsert(DeptVO pVO) {
        logger.info("deptInsert 호출");
        int result = 0;

        result = deptDao.deptInsert(pVO);

        return result;
    }

    public List<Map<String, Object>> deptList(Map<String, Object> pMap) {
        logger.info("deptList 호출");
        List<Map<String, Object>> dList = null;

        dList = deptDao.deptList(pMap);

        return dList;
    }

    public int deptUpdate(@RequestBody DeptVO pdVO) {
        logger.info("deptUpdate 호출");
        int result = 0;

        result = deptDao.deptUpdate(pdVO);

        return result;
    }

    public int deptDelete(int deptno) {
        logger.info("deptDelete 호출");
        int result = 0;

        result = deptDao.deptDelete(deptno);

        return result;
    }
}
