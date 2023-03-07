package com.di;

public class HelloBeanImpl implements HelloBean {
	String msg = null;
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getGreeting(String msg) {
		// this.msg를 사용 시 'helloBean.xml'에 등록한 msg로 초기화된 this.msg가 출력된다
//		return "Spring " + this.msg;
		return "Spring " + msg;
	}
}