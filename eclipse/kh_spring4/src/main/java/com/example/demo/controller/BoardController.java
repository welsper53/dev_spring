package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.example.demo.logic.BoardLogic;

public class BoardController extends MultiActionController {
private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private BoardLogic boardLogic = null;
public void setBoardLogic(BoardLogic boardLogic) {
		this.boardLogic = boardLogic;
	}
public ModelAndView boardList(HttpServletRequest req, HttpServletResponse res)
{
ModelAndView mav =  new ModelAndView();
mav.setViewName("board/list");
return mav;}

}
