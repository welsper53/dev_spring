package com.example.demo.util;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

// 스프링 부트에서는 RequestParam이 대신 해준다, Model, ModelMap
// 사용자가 입력한 값을 Map에 담아 준다
// 담을 Map은 컨트롤계층에서 bind메소드 호출 시 파라미터를 이용해서 원본 주소번지를 받아온다
// 그리고 그 안에 담는다.
public class HashMapBinder {
	Logger logger = Logger.getLogger(HashMapBinder.class);
	HttpServletRequest req = null;	// 전역변수
	
	public HashMapBinder() {}
	// 생성자 파라미터에 요청객체(지역변수)가 필요한 이유는 뭐죠?
	// => 
	public HashMapBinder(HttpServletRequest req) {
		// 생성자의 역할 -> 전역변수의 초기화
		this.req = req;
	}
	
	// 어떤 페이지 어떤 상황에서 공통코드 재사용 가능하게 할 것인가?
	// 업무별 요청 클래스에서 주입 받을 객체를 정해서 메소드의 파라미터로 전달 받음
	// 전달 받은 주소번지 원본에 값을 담아준다
	public void bind(Map<String,Object> pMap) {
		logger.info("bind 호출");
		pMap.clear();
		Enumeration<String> en = req.getParameterNames();
		
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			pMap.put(key, req.getParameter(key));
		}
		logger.info(pMap);
	}
}
