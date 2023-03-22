package com.example.mblog1.dao;

import com.example.mblog1.vo.DeptVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DeptDao {
    Logger logger = LoggerFactory.getLogger(DeptDao.class);

    // DatabaseConfiguration에서 @Configuration으로 빈등록된 객체 주입받기 코드
    // application.properties에서 물리적으로 떨어져 있는 오라클 서버정보 받음

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate = null;

    public int deptInsert(DeptVO pVO) {
        logger.info("deptInsert 호출");
        int result = 0;

        // DML문을 가진 xml문은 src/main/resources 아래에 있다
        result = sqlSessionTemplate.update("deptInsert", pVO);
        logger.info("DB쿼리 결과 : " + result);

        return result;
    }

    public List<Map<String, Object>> deptList(Map<String, Object> pMap) {
        logger.info("deptList 호출");
        List<Map<String,Object>> dList = null;

        dList = sqlSessionTemplate.selectList("deptList", pMap);

        return dList;
    }

    public int deptUpdate(DeptVO pVO) {
        logger.info("deptUpdate 호출");
        int result = 0;

        // DML문을 가진 xml문은 src/main/resources 아래에 있다
        result = sqlSessionTemplate.update("deptUpdate", pVO);
        logger.info("DB쿼리 결과 : " + result);

        return result;
    }

    public int deptDelete(int deptno) {
        logger.info("deptDelete 호출");
        int result = 0;

        // DML문을 가진 xml문은 src/main/resources 아래에 있다
        result = sqlSessionTemplate.update("deptDelete", deptno);
        logger.info("DB쿼리 결과 : " + result);

        return result;
    }
}
