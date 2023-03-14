package com.example.demo.di;

import java.util.List;

public class InsaList {
	// @Autowired <- 스프링 부트에서는 여기에 해당 어노테이션 사용해서 처리한다
	List<String> insaBean = null;

	// 생성자 객체(car.xml) 주입법 코드와 setter객체 주입법 코드가 있다
	// spring boot에서는 @Autowired 어노테이션으로 필요가 없어졌다
	// insaBean = new ArrayList<>();
	public void setInsaBean(List<String> insaBean) {
		this.insaBean = insaBean;
	}

	
}
