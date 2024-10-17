package com.swhwang.jwt_restapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/join")
    public ResponseEntity join(User user) {
        // 회원 정보를 입력받으면 패스워드를 암호화하여 DB에 저장
        user.setPassword(encoder.encode(user.getPassword()));
        User result = repo.save(user);

        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Created!!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created!");
    }
}
