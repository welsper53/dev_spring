package com.example.mblog1.logic;

import java.util.List;
import java.util.Map;

import com.example.mblog1.dao.RepleBoardDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepleBoardLogic {
	Logger logger = LogManager.getLogger(RepleBoardLogic.class);
	@Autowired
	private RepleBoardDao repleBoardDao = null;
	public List<Map<String, Object>> qnaList(Map<String, Object> pMap) {
		logger.info("boardList호출 성공");
		List<Map<String, Object>> bList = null;
		bList = repleBoardDao.qnaList(pMap);
		return bList;
	}
	public int qnaInsert(Map<String, Object> pMap) {
		logger.info("qnaInsert 호출");
		int result = 0;
		result = repleBoardDao.qnaInsert(pMap);
		return result;
	}
	
}
