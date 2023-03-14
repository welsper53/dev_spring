package com.example.demo.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sonata {
	Logger logger = LoggerFactory.getLogger(Sonata.class);
	private int speed;
	private String carName;
	private int wheelNum;
	
	public Sonata() {
		logger.info("디폴트 생성자입니다");
	}
	public Sonata(int speed, String carName, int wheelNum) {
		this.speed = speed;
		this.carName = carName;
		this.wheelNum = wheelNum;
	}
	
	public void stop() {
		logger.info("자동차가 정지합니다.");
	}
	
	public String toString() {
		return "그 남자의 자동차는 " + this.carName
				+ "이고, 현재 속도는 " + this.speed
				+ "이고, 바퀴수는 " + this.wheelNum
				+ "입니다";
	}
}
