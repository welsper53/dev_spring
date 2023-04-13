package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity     // JPA 사용 시 테이블 생성해준다 - 확인할 것. application.yml에 create 모드인지 확인
@Data       // getter, setter 생성
public class User {
    @Id  // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;    // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
    @CreationTimestamp
    private Timestamp createDate;

    // 회원가입에 사용할 생성자 추가 - 테이블 자동생성된다
    public User() {}
    @Builder
    public User(String username, String password, String email
            , String role, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
    }
    /*
    * 출력 로그
    * 2023-04-18 22:07:51.788 [INFO] 21788 [PojoInstantiator.java : 47] {HHH000182: No default (no-argument) constructor for class: com.example.demo.model.User (class must be instantiated by Interceptor)}
        Hibernate:

            drop table if exists User
        Hibernate:

            create table User (
               id integer not null auto_increment,
                createDate datetime,
                email varchar(255),
                password varchar(255),
                role varchar(255),
                username varchar(255),
                primary key (id)
            ) engine=InnoDB
    */
}
