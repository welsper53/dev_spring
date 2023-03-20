package com.example.mblog1.controller;

import com.example.mblog1.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restful/*")   // upmu[0]
public class RestfulController {
    Logger logger = LoggerFactory.getLogger(RestfulController.class);

    // http://localhost:8000:restful/{id}
    @GetMapping("{id}")
    public String main(@PathVariable int id) {
        logger.info("해시값 받아주는 어노테이션 : " + id);

        return String.valueOf(id);
    }

    // http://localhost:8000/resful/get?mem_id=kiwi&mem_pw=123&mem_name=키위
    @GetMapping ("get")     // upmu[1]
    public String getTest(MemberVO mVO) {
        logger.info(mVO.toString());

        return "get 요청 : " + mVO.getMem_id() + ", " + mVO.getMem_pw()
                + ", " + mVO.getMem_name();
    }

    /* postman에서 테스트해야만 하며 @RequestBody에 들어갈 값은 Body에 들어갈 값은 Body 선택 후
    *  row체크하고 반드시 JSON선택 후 JSON포맷으로 파라미터 넘겨야 한다 - 주의할 것
    *  @RequestBody에 타입을 선언하면 MessageConverter가 대신 해준다 - 반드시 JSON형식
    *  http://localhost:8000/restful/post*/
    @PostMapping ("post")
    public String postTest(@RequestBody MemberVO mVO) {
        logger.info(mVO.toString());

        return "post 요청 : " + mVO.getMem_id() + ", " + mVO.getMem_pw()
                + ", " + mVO.getMem_name();
    }
}
