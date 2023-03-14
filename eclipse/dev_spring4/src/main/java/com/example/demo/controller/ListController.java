package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ListController extends AbstractController {
	Logger logger = LoggerFactory.getLogger(ListController.class);
	
	// @Autowired <- 스프링 부트에서는 여기에 해당 어노테이션 사용해서 처리한다
	List<String> insaBean2 = null;

	// 생성자 객체(car.xml) 주입법 코드와 setter객체 주입법 코드가 있다
	// spring boot에서는 @Autowired 어노테이션으로 필요가 없어졌다
	// insaBean = new ArrayList<>();
	public void setInsaBean2(List<String> insaBean2) {
		this.insaBean2 = insaBean2;
	}
	
	// 메소드의 파라미터로 요청객체와 응답객체를 주입받고 있다는 것은 서블릿에 의존적인 프레임워크를 의미한다
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("insaBean", insaBean2);
		mav.setViewName("di/insaList");

		return mav;
	}

}
