package com.example.mblog1.controller;

import com.example.mblog1.logic.MemberLogic;
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
@RequestMapping("/member/*")
public class MemberController {
    Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberLogic memberLogic = null;

    @GetMapping("memberInsert")
    public String memberInsert(@RequestParam Map<String,Object> pMap) {
        logger.info("memberInsert 호출");
        logger.info(pMap.toString());

        int result = 0;     // 회원가입) 0: 실패 | 1: 성공
        result = memberLogic.memberInsert(pMap);

        return "redirect:./memberList";
    }
    @GetMapping("memberList")
    public String memberList(Model model) {
        logger.info("memberList 호출");

        List<Map<String,Object>> mList = null;


        return "member/memberList";
    }
}
