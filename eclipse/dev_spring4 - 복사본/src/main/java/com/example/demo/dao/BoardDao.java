package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardDao {
	private static final Logger logger = LoggerFactory.getLogger(BoardDao.class);
	private SqlSessionTemplate sqlSessionTemplate = null;

	public List<Map<String, Object>> boardList() {
		logger.info("BoardDao.boardList 호출");
		List<Map<String,Object>> bList = null;
		
		return bList;
	}
}
