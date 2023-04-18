package com.basic.step1.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.vo.BoardMasterVO;

@Service
public class BoardDao {
	Logger logger = LoggerFactory.getLogger(BoardDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate = null;

	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("boardList 호출 성공");
		List<Map<String, Object>> boardList = null;
		try {
			boardList = sqlSessionTemplate.selectList("boardList", pMap);
			// insert here
			logger.info(boardList.toString());
		} catch (DataAccessException e) {
			logger.info("Exception : "+e.toString());
		} 
		return boardList;
	}

	public int getBNo() {
		logger.info("getBNo 호출 성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.selectOne("getBNo");
			// insert here
			logger.info(result+"");
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 
		return result;
	}

	public void bStepUpdate(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.update("bStepUpdate",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 	
	}

	public int getBGroup() {
		logger.info("getBGroup 호출 성공");
		int result = 0;
		try {
			result = sqlSessionTemplate.selectOne("getBGroup");
			// insert here
			logger.info(result+"");
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 
		return result;
	}

	public int boardMInsert(Map<String, Object> pMap) {
		logger.info(pMap.toString());
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardMInsert",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 	
		return result;
	}

	public int boardMUpdate(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardMUpdate",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 	
		return result;
	}

	public int boardMDelete(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.delete("boardMDelete",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		}	
		return result;
	}

	public int hitCount(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.update("hitCount",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		}	
		return result;
	}

	public int boardSInsert(Map<String, Object> pMap) {
		int result = 0;
		try {
			result = sqlSessionTemplate.update("boardSInsert",pMap);
			logger.info("result : "+result);
		} catch (Exception e) {
			logger.info("Exception : "+e.toString());
		} 	
		return result;
	}
}
