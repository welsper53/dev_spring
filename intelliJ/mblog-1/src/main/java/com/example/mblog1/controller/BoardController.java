package com.example.mblog1.controller;

import com.example.mblog1.logic.BoardLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board/*")
public class BoardController {
    Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardLogic boardLogic = null;

    @GetMapping("boardList")
    public String boardList(Model model, @RequestParam Map<String,Object> pMap) {
        logger.info("boardList 호출");
        logger.info(pMap.toString());

        List<Map<String,Object>> bList = null;
        bList = boardLogic.boardLogic(pMap);
        model.addAttribute("bList", bList);

//        return "forward:boardList.jsp";
        return "board/boardList";
    }
}
