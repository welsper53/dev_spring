package com.example.demo.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class InsaMapMain {
	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("com\\example\\demo\\di\\insaMap.xml");
		
		// 파라미터는 bean의 id값이다
		InsaMap insaMap = (InsaMap)context.getBean("_insaMap");
		System.out.println(insaMap.insaMap);
		
	}
}
