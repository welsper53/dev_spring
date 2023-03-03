package com.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class HelloMain {
	public static void main(String[] args) {
		Resource resource = new FileSystemResource("D:\\workspace_java\\workspace_spring\\kh_spring4\\src\\main\\java\\com\\di\\hellobean.xml");
		
		/* Spring Container(엔진)는 두 가지 유형의 컨테이너 제공한다
		 * spring-heans.jar에 살고 있음
		 * 모든 빈을 늦게 로딩한다 
		 * -> getBean() 호출 될 때까지 Bean의 생성을 미룬다*/
		BeanFactory factory = new XmlBeanFactory(resource);
		// 제어역전, 의존성 주입, scope
		HelloBean helloBean = (HelloBean)factory.getBean("helloBean");	// 파라미터 "helloBean"은 'helloBean.xml'에서 가져온다
		
		// 'HelloBeanImpl.java'의 'getGreeting'메소드 코드에 따라 메시지가 바뀐다
		System.out.println(helloBean.getGreeting("안녕"));
		
		/* spring-context.jar에 살고 있음
		 * Context 시작시킬 때 모든 singleton 빈을 미리 로딩함으로써
		 * 그 빈이 필요할 때 즉시 사용될 수 있도록 보장해준다
		 * 어플리케이션 동작 시 Bean생성되기를 기다릴 필요가 없게된다 */
		ApplicationContext context = new ClassPathXmlApplicationContext("com\\di\\helloBean.xml");
		HelloBean helloBean2 = (HelloBean)context.getBean("helloBean");
		System.out.println("잘가");
	}
}
