package com.example.mblog1.logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.mblog1.dao.RepleBoardDao;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class RepleBoardLogic {
	Logger logger = LogManager.getLogger(RepleBoardLogic.class);
	@Autowired
	private RepleBoardDao repleBoardDao = null;

	public List<Map<String, Object>> qnaList(Map<String, Object> pMap) {
		logger.info("qnaList호출 성공");
		List<Map<String, Object>> bList = null;

		bList = repleBoardDao.qnaList(pMap);

		return bList;
	}

	public int qnaInsert(Map<String, Object> pMap) {
		logger.info("qnaInsert 호출");
		// 여기서 result는 insert 성공 유무를 나타내는 숫자(0,1)가 아니라
		// 글 등록 시에 채번된 시퀀스를 값이어야 한다
		// qna_bno값이어야 한다
		int result = 0;

		result = repleBoardDao.qnaInsert(pMap);
		// 위에서 돌려받은 시퀀스값(qna_bno)를 pMap에 담아준다
		// 키 값이 소문자이면 #{qna_bno}, 대문자이면 #{QNA_BNO}
		// 사용자가 입력한 값의 키 값은 모두 싹다 소문자로 하자
		pMap.put("qna_bno", result);
		logger.info("qna_bno: " + pMap.get("qna_bno") + ", type: " + pMap.get("qna_bno").getClass().getSimpleName());

		// 선택한 이미지가 있니?
		if (pMap.get("fileNames") != null) {
			// 작성자가 선택하는 이미지의 개수가 다르다
			// 3개이면 3개를 담아내야 한다 -> 3개에 대한 UPDATE가 3번 일어나야 한다
			List<Map<String,Object>> fList = fileNames(pMap);
			logger.info(fList);

			repleBoardDao.fileUpdate(fList);
		}

		return result;
	}

	private List<Map<String,Object>> fileNames(Map<String, Object> pMap) {
		logger.info("fileNames 호출");
		logger.info("qna_bno: " + pMap.get("qna_bno"));

		List<Map<String,Object>> pList = new ArrayList<>();
		HashMap<String,Object> fMap =null;

		// pMap.get("fileName") => ["man1.png", "man2.png", ...]
		String[] fileNames = pMap.get("fileNames").toString()
								.substring(1, pMap.get("fileNames").toString().length()-1).split(",");

		for (int i=0; i<fileNames.length; i++) {
			fMap = new HashMap<String,Object>();
			fMap.put("file_name", fileNames[i]);
			fMap.put("qna_bno", pMap.get("qna_bno"));

			pList.add(fMap);
		}

		return pList;
	}

	public String imageUpload(MultipartFile image) {
		logger.info("imageUpload 호출 성공");
		// 이미지 업로드 된 파일에 대한 file_name, file_size, file_path 등을 결정해준다 - 서비스 계층이다 (과장,차장,부장이 한다)
		Map<String,Object> pMap = new HashMap<>();

		// 사용자가 선택한 파일 이름 담기
		String filename = null;
		String fullPath = null;
		double d_size = 0.0;

		if(!image.isEmpty()) {
			//filename = image.getOriginalFilename();
			// 같은 파일명으로 업로드 되는 경우 덮어쓰기 되는 것을 방지 하고자
			// 오리지널 파일명 앞에 날짜와 시간정보를 활용하여 절대 같은 이름이 발생하지 않도록 처리한다
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar time = Calendar.getInstance();

			// 파일명 = 날짜시간-파일명 (공란은 '_'으로 변경)
			filename = sdf.format(time.getTime()) + "-" + image.getOriginalFilename().replaceAll(" ","_");

			// 이미지에 대한 업로드이므로 첨부파일 크기 계산은 하지 않음
			// 스프링프로젝트가 바라보는 물리적인 위치 정보
			String saveFolder = "D:\\dev_spring\\intelliJ\\mblog-1\\src\\main\\webapp\\pds";
			fullPath = saveFolder+"\\"+filename;
			logger.info("fullPath: " + fullPath);

			try {
				// File객체는 파일명을 객체화 해주는 클래스임
				// -> 생성되었다고 해서 실제 파일까지 생성되는 것이 아님
				File file = new File(fullPath);
				byte[] bytes = image.getBytes();

				// outstream반드시 생성해서 파일정보를 읽어서 쓰기를 처리해줌
				// BufferedOutputStream은 필터 클래스이지 실제 파일 쓸수 없는 객체
				// => 실제 파일 쓰기가 가능한 클래스가 FileOutputStream임
				//    : 생성자 파라미터에 파일 정보 담기
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bos.write(bytes);   // bos : 주소번지

				// 파일쓰기와 관련 위변조 방지 위해 사용후 반드시 닫을 것
				bos.close();

				// 여기까지는 임시 파일 쓰기 처리였고, 이 다음에는 mblog_file테이블에 insert될 정보를 초기화 해준다
//				d_size = Math.floor((file.length()/(1024.0*1024.0)) * 10) / 10;
				d_size = Math.floor((file.length()/1024.0) * 10) / 10;
				logger.info("d_size : " + d_size);
				logger.info("filename : " + filename);
				logger.info("fullPath : " + fullPath);

				pMap.put("file_name", filename);
				pMap.put("file_size", d_size);
				pMap.put("file_path", fullPath);

				int result = repleBoardDao.fileInsert(pMap);
				logger.info(result);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
		}

		// 리턴 값으로 선택한 이미지 파일명을 넘겨서
		// 사용자 화면에 첨부된 파일명을 열거해 주는데 사용할 것임
		String temp = filename;
		logger.info("temp: " + temp);

		return temp;
	}

	public int qnaDelete(Map<String,Object> pMap) {
		logger.info("qnaDelete 호출");
		int result = 0;

		result = repleBoardDao.qnaDelete(pMap);
		logger.info(result);

		return result;
	}

	// 한 건만 가져오는 데 왜 List<Map>인가요? 그냥 Map<>으로 하면 안되나요?
	// => DataSet변화 [{qna}, {fileListt}, {commentList}]
	public List<Map<String, Object>> qnaDetail(Map<String, Object> pMap) {
		logger.info("qnaDetail 호출");
		List<Map<String,Object>> bList = null;
		int qna_bno = 0;

		// int 타입으로 형변환
		if (pMap.get("qna_bno") != null) {
			qna_bno = Integer.parseInt(pMap.get("qna_bno").toString());
			pMap.put("qna_bno", qna_bno);
		}

		bList = repleBoardDao.qnaDetail(pMap);

		// 댓글 처리 추가
		// insert here
		// 댓글 처리 추가

		// 이미지 파일이 있는지?
		if (bList !=null && bList.size() == 1) {
			List<Map<String,Object>> fileList = repleBoardDao.fileList(pMap);

			if (fileList != null && fileList.size() > 0) {
				bList.addAll(fileList);
			}
		}

		// 게시물 조회수 증가
		if (bList.size() > 0) {
			logger.info("게시물 조회수 증가");
			repleBoardDao.qnaHit(pMap);
		}

		return bList;
	}

	public int qnaUpdate(Map<String, Object> pMap) {
		logger.info("qnaUpload 호출");
		int result = 0;

		result = repleBoardDao.qnaUpdate(pMap);
		logger.info(result);

		return result;
	}
}
