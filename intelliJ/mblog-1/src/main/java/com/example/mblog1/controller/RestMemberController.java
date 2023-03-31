package com.example.mblog1.controller;

import com.example.mblog1.logic.MemberLogic;
import com.google.gson.Gson;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/*")
public class RestMemberController {
    //Logger logger = LoggerFactory.getLogger(RestMemberController.class);
    Logger logger = LogManager.getLogger(RestMemberController.class);

    @Autowired
    private MemberLogic  memberLogic = null;

    @PostMapping("memberInsert")
    public String memberInsert(@RequestBody Map<String, Object> pMap) {
        // 리액트에서 body에 {}객체리터럴로 넘겨준 정보를 Map이나 VO로 담을 수 있다
        logger.info("memberInsert 호출");
        logger.info(pMap.toString());
        int result = 0;

        result = memberLogic.memberInsert(pMap);

        return String.valueOf(result);
    }

    // http://localhost:8000/member/memberList
    // 리액트 프로젝트에서 닉네임 중복검사 시 사용하는 메소드 구현입니다
    // 리액트에서 넘기는 파라미터는 { MEM_NICKNAME: memInfo[key], type: 'overlap' }
    @GetMapping("memberList")
    public String memberList(@RequestParam Map<String, Object> pMap) {
        logger.info("memberList 호출");
        logger.info(pMap.toString());
        String temp = null;
        List<Map<String,Object>> mList = new ArrayList<>();

        mList = memberLogic.memberList(pMap);
        logger.info(mList);

        // 파라미터로 넘어온 쿼리스트링이 회원집합에 존재하면 조회결과가 있다
        // -> mList.size() == 1 or 0
        // temp에 문자열이 들어있으면 자바스크립트쪽에서는 true로 판정된다
        // -> 주의할것 - 자바와 다름
        if (mList.size() > 0) {
            Gson g = new Gson();
            temp = g.toJson(mList);
        }
        // mem_uid가 없을 때  - 회원가입 유도 할 것.
        else {
            temp = "0";
        }

        return temp;
    }

    @PostMapping("memberUpdate")
    public String memberUpdate(@RequestBody Map<String, Object> pMap) {
        logger.info("memberUpdate 호출");
        logger.info(pMap.toString());
        int result = 0;

        result = memberLogic.memberUpdate(pMap);

        return String.valueOf(result);
    }

    @GetMapping("memberDelete")
    public String memberDelete(@RequestParam Map<String, Object> pMap) {
        logger.info("memberDelete 호출");
        logger.info(pMap.toString());
        int result = 0;

        result = memberLogic.memberDelete(pMap);

        return String.valueOf(result);
    }
}
