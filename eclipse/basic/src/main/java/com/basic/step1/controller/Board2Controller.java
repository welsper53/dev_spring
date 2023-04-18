package com.basic.step1.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.basic.step1.dao.Board2Dao;
import com.kh.test.board.model.vo.Board;

@Controller
@RequestMapping("/board2/*")
public class Board2Controller {
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private Board2Dao board2Dao = null;
	//http://localhost:8000/step1/board2/selectBoard.sp4?bid=1
	@GetMapping("selectBoard.sp4")
	public String selectBoard(@RequestParam Map<String,Object> pMap, Model model) {
		logger.info("selectBoard 호출 성공");
		Board board = null;
		board = board2Dao.selectBoard(pMap);
		model.addAttribute("board", board);
		return "forward:boardDetail.jsp";
	}
}
