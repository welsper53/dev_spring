package com.example.mblog1;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// @Configuration
// : 사용자 정의 클래스도 ApplicationContext나 BeanFactory의 관리를 받을 수 있다
// : - 의존성 주입
// application.propertiesssss
// : maven 방식
// application.yml
// : gradle 방식
// : 반복코드가 없다 - json방식이라서
// resource - mapper -> member.xml, board.xml, order.xml
// static - CND
// css
// js
// images
@Configuration
@PropertySource("classpath:/application.properties")
// classpath : resource -> log4j.properties
public class DatabaseConfiguration {
    private static final Logger logger = LogManager.getLogger(DatabaseConfiguration.class);
    // @Configuration으로 선언된 클래스에서만 사용가능한 어노테이션이다
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari") // application.properties의 접두어
    public HikariConfig hikariConfig() {
        return new HikariConfig();  // 생성자 호출 - 메모리 로딩 - 변수와 메소드를 누릴 수 있따
    }

    // 물리적으로 떨어져 있는 오라클(application.properties) 서버와 연결통로 확보
    // POJO에서는 MyBatisConfig.xml <- hikariConfig()
    @Bean
    public DataSource dataSource() {
        // 인터페이스 = 구현체 클래스 (new HikariConfig()) -> application.properties
        DataSource dataSource = new HikariDataSource(hikariConfig());
        logger.info("datasource : {}", dataSource);
        return dataSource;
    }
    @Autowired
    private ApplicationContext applicationContext; // 빈 관리 - 이른 인스턴스화 - BeanFactory의 자손 클래스임 - 그래서 기능은 더 많다

    /*  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configLocation" value="WEB-INF/mybatis-config.xml"/>
		<property name="dataSource" ref="data-source-target"/>
	    </bean>                                                                         */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        //classpath는 src/main/resourcs이고 해당 쿼리가 있는 xml 위치는 본인의 취향대로 위치키시고 그에 맞도록 설정해주면 된다.
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /*  <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
		<constructor-arg index="0" ref="sqlSessionFactory"/>
	    </bean>                                                                         */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}