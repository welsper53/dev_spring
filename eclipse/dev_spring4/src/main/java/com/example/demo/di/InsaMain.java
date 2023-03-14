package com.example.demo.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class InsaMain {
	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("com\\example\\demo\\di\\insaBean.xml");
		
		// 파라미터는 bean의 id값이다
		InsaList insaList = (InsaList)context.getBean("insa");
		System.out.println(insaList.insaBean);
		
		for (int i=0; i<insaList.insaBean.size(); i++) {
			System.out.println(insaList.insaBean.get(i));
		}
		System.out.println("====");
		for (String i : insaList.insaBean) {
			System.out.println(i);
		}
	}
}
