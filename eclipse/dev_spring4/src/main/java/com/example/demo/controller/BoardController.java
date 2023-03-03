package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.example.demo.logic.BoardLogic;

/**
 * 요청에 대한 컨트롤러 클래스의 메서드 마다 요청객체와 응답객체를 주입해주는 클래스 메서드 중심의 요청객체와 응답객체를 주입해주는 클래스 왜
 * 나만 상속을 받아야 하나요? - 요청객체와 응답객체가 필요하다. 지금은 서블릿이 아닌 대신에 MultiActionController을
 * 상속받았다.
 */
public class BoardController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardLogic boardLogic = null;
	// setter객체주입법 - 자바코드에서 객체 주입을 처리할 때 사용함
	public void setBoardLogic(BoardLogic boardLogic) {
		this.boardLogic = boardLogic;
	}
	
	// void doGet(req,res) -> ActionForward execute(req,res) -> String execute(req,res)
	// -> String ~~~(req,res) (<-사용자정의)
	public ModelAndView boardList(HttpServletRequest req, HttpServletResponse res)
	{
		logger.info("boardList 호출");
		List<Map<String,Object>> bList = null;
		bList = boardLogic.boardList();
		
		ModelAndView mav =  new ModelAndView();
		mav.setViewName("board/boardList");
		mav.addObject("bList", bList);
		
		
		return mav;
	}

}
