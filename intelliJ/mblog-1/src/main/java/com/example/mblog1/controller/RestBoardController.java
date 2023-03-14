package com.example.mblog1.controller;

import com.example.mblog1.logic.BoardLogic;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board/*")
public class RestBoardController {
    Logger logger = LoggerFactory.getLogger(RestBoardController.class);

    @Autowired
    private BoardLogic boardLogic = null;

    @GetMapping("jsonBoardList")
    public String boardList(Model model, @RequestParam Map<String,Object> pMap) {
        logger.info("jsonBoardList 호출");
        logger.info(pMap.toString());

        List<Map<String,Object>> bList = null;
        bList = boardLogic.boardLogic(pMap);
        model.addAttribute("bList", bList);

        Gson g = new Gson();
        String temp = g.toJson(bList);

        return temp;
    }

    @GetMapping("getTest")
    public String getTest() {
        logger.info("getTest 호출");

        return "테스트";
    }
}
