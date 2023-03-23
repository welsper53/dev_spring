package com.example.mblog1.controller;

import com.example.mblog1.logic.DeptLogic;
import com.example.mblog1.vo.DeptVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")
public class RestDeptController {
    Logger logger = LoggerFactory.getLogger(RestDeptController.class);

    @Autowired
    DeptLogic deptLogic = null;

    // 단위 테스트 URL은
    // => http://localhost:8000/dept/deptInsert 이지만 테스트는 불가능함
    /**************************************************
     * 부서등록 구현
     * @param pdVO
     * @return insert문 성공여부 - 1:성공, 0:실패
     **************************************************/
    @PostMapping("/deptInsert")
    public String deptInsert (@RequestBody DeptVO pdVO) {
        logger.info("deptInsert 호출");
        int result = 0;

        logger.info(pdVO.toString());
        result = deptLogic.deptInsert(pdVO);

        return String.valueOf(result);
    }

    @GetMapping("deptList")
    public String deptList (@RequestParam Map<String,Object> pMap) {
        logger.info("deptList 호출");
        List<Map<String,Object>> dList = null;
        dList = deptLogic.deptList(pMap);

        Gson g = new Gson();
        String temp = g.toJson(dList);

        return temp;
    }

    @PostMapping("/deptUpdate")
    public String deptUpdate (@RequestBody DeptVO pdVO) {
        logger.info("deptUpdate 호출");
        int result = 0;

        logger.info(pdVO.toString());
        result = deptLogic.deptUpdate(pdVO);

        return String.valueOf(result);
    }

    @GetMapping("/deptDelete")
    public String deptDelete (int deptno) {
        logger.info("deptDelete 호출");
        logger.info("사용자가 선택한 부서번호 - 단 자손이 없어야 한다");
        int result = 0;

        logger.info(Integer.toString(deptno));
        result = deptLogic.deptDelete(deptno);

        return String.valueOf(result);
    }
}

/*  @Controller는 리턴값이 화면 출력으로 사용한다
*   @RestController는 리턴값에 대한 마임타입이 'text/plain'으로 사용된다
*   리액트 연동 시 조회된 결과나 백엔드에서 전달되는 값은 text이거나 json포맷만 가능하므로
*   후자를 선택한다
*
*   @RquestBody는 post방식으로 사용자가 입력한 값이 보안상 중요한 정보인 경우 사용가능하다
*   패킷 헤더가 아닌 바디에 저장되므로 노출 위험이 없다
*   */