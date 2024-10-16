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
