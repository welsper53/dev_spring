package com.example.demo.di;

import java.util.HashMap;
import java.util.Map;

public class InsaMap {
	// @Autowired <- 스프링 부트에서는 여기에 해당 어노테이션 사용해서 처리한다
	HashMap<String,String> insaMap = null;

	public void setInsaMap(HashMap<String,String> insaMap) {
		this.insaMap = insaMap;
		
	}

}
