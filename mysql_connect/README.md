# Step 2: DB와 연동하여 사용자 인증 구현
>**주의! 사용자 정보에 영속성을 부여하고 로그인에 사용하기 위한 최소한의 구성입니다.**<br>
> 이 코드에는 사용자 검증이 포함되지 않았고, 사용자 중복도 고려하지 않았습니다.<br>
> 실제 운영 환경은 사용자 인증 및 회원 가입에 철저한 검증이 요구됩니다.
## 1. 의존성 추가하기
* build.gradle<br>
    ```
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    annotationProcessor 'org.projectlombok:lombok'
    ```

## 2. DB 연동을 위한 데이터 기입하기
1. src/main/resources에 application.yaml을 추가한다.<br>
    ```yaml
    spring:
      application:
        name: mysql_connect
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: "jdbc:mysql://<MySQL IP Address>:3306/user"
        username: <Username>
        password: <Password>
      jpa:
        database-platform: org.hibernate.dialect.MySQL8Dialect # Hibernate가 사용할 SQL 문법, MySQL 8.0
        hibernate: 
          ddl-auto: update # Hibernate의 데이터베이스 스키마 생성 방식을 지정, update는 변경사항만 반영
        show-sql: true # Hibernate가 생성하는 모든 쿼리 출력
        properties: 
          hibernate:
            format_sql: true # Hibernate의 출력 형식을 가독성 있게 변경
    ```
   
## 3. 회원가입 API를 이용해 로그인 계정 생성
1. 기본 값으로 런타임 시 admin / admin 계정이 생성된다.
    ```java
    package com.swhwang.mysql_connect.user;
    
    import jakarta.annotation.PostConstruct;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Component;
    
    @Component
    public class InitUser {
        @Autowired
        private UserRepository repo;
        @Autowired
        private PasswordEncoder encoder;
    
        @PostConstruct
        public void init() {
            // 초기 계정 설정, admin 계정이 없다면 admin/admin으로 생성
            if(!repo.existsByUsername("admin")) {
                repo.save(new User("admin","admin"));
            }
        }
    }
    ```
2. Postman 등 API Tester를 이용해 계정 생성할 수 있다.<br>
    > **user.UserController**를 참고하면 localhost:8080/join에서 form 데이터를 입력받도록 구성되어 있다.<br>
    ![image](https://github.com/user-attachments/assets/9d58d9b3-5c63-4752-86a0-b6c2a33fa0e0)

## 3. 생성된 계정으로 사용자 인증하기
1. localhost:8080/login에서 확인할 수 있다.
    > ![image](https://github.com/user-attachments/assets/b5a3bce6-dcfe-4260-944d-841db0e3f1e5)
2. 인증에 성공했으면 마찬가지로 404 에러 또는 웰컴페이지 출력
    > ![image](https://github.com/user-attachments/assets/6fdb7b18-8ac6-4a89-b68c-28423869c70c)