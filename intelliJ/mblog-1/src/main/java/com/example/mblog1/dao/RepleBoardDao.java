package com.example.mblog1.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepleBoardDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate = null;
	Logger logger = LogManager.getLogger(RepleBoardDao.class);

	public List<Map<String, Object>> qnaList(Map<String, Object> pMap) {
		logger.info("qnaList 호출");
		List<Map<String, Object>> bList = null;

		bList = sqlSessionTemplate.selectList("qnaList", pMap);

		return bList;
	}

	public int qnaInsert(Map<String, Object> pMap) {
		logger.info("qnaInsert 호출");
		// 입력이 성공했는지 유무를 담는 변수 선언
		int result = 0;
		// insert 시에 시퀀스로 채번된 속성을 담을 변수 - 여기서는 시퀀스로 채번되는 qna_bno이다
		int qna_bno = 0;

		result = sqlSessionTemplate.update("qnaInsert", pMap);

		if (result == 1) {		// insert가 되면
			if (pMap.get("qna_bno") != null) {
				qna_bno = Integer.parseInt(pMap.get("qna_bno").toString());
			}
		}
		logger.info("result : " + result);
		logger.info("useGeneratedKey 프로퍼티 속성값 가져오기 : " + qna_bno);

		return qna_bno;
	}

	public int fileInsert(Map<String, Object> pMap) {
		logger.info("fileInsert 호출 성공");
		// 입력이 성공했는지 유무를 담는 변수 선언
		int result = 0;

		result = sqlSessionTemplate.update("fileInsert", pMap);
		logger.info("result : " + result);

		return result;
	}

	public void fileUpdate(List<Map<String, Object>> pList) {
		logger.info("fileUpdate 호출 성공");
		// 입력이 성공했는지 유무를 담는 변수 선언
		int result = 0;

		result = sqlSessionTemplate.update("fileUpdate", pList);
		logger.info("result : " + result);
	}

	public int qnaDelete(Map<String,Object> pMap) {
		logger.info("qnaDelete 호출");
		int result = 0;

		result = sqlSessionTemplate.delete("qnaDelete", pMap);
		logger.info(result);

		if (result == 1) {
			result = sqlSessionTemplate.delete("fileDelete", pMap);
		}

		return result;
	}

	public List<Map<String, Object>> qnaDetail(Map<String, Object> pMap) {
		logger.info("qnaDetail 호출");
		List<Map<String,Object>> qList = null;

		qList = sqlSessionTemplate.selectList("qnaDetail", pMap);

		return qList;
	}
	public void qnaHit(Map<String, Object> pMap) {
		logger.info("qnaHit 호출");
		logger.info(pMap);
		int result = 0;

		// qna 게시물 조회
		pMap.put("id", "qna");

		result = sqlSessionTemplate.update("qnaHit", pMap);
		logger.info("result : " + result);
	}

	public List<Map<String, Object>> fileList(Map<String, Object> pMap) {
		logger.info("fileList 호출");
		List<Map<String,Object>> fList = null;

		fList = sqlSessionTemplate.selectList("fileList", pMap);

		return fList;
	}

	public int qnaUpdate(Map<String, Object> pMap) {
		logger.info("qnaUpdate 호출");
		int result = 0;

		result = sqlSessionTemplate.update("qnaUpdate", pMap);
		logger.info("result : " + result);

		return result;
	}
}
