package com.example.mblog1.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/*")
public class RestMemberController {
    Logger logger = LoggerFactory.getLogger(RestMemberController.class);

    @GetMapping("jsonMemberList")
    public String jsonMemberList(Model model) {
        logger.info("jsonMemberList 호출");

        String temp = null;
        List<Map<String,Object>> mList = new ArrayList<>();
        Map<String,Object> rmap = new HashMap<>();

        rmap.put("mem_id", "tomato");
        rmap.put("mem_pw", "123");
        rmap.put("mem_name", "토마토");
        mList.add(rmap);

        Gson g = new Gson();
        temp = g.toJson(mList);

        return temp;
    }
}
