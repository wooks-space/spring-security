package com.swhwang.jwt_restapi.auth;

import com.swhwang.jwt_restapi.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

@Configuration
@EnableWebMvc
public class LoginSecurityConfigure {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    JWTProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.cors(Customizer.withDefaults());
        http.formLogin(form->
                form.loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            System.out.println("인증에 성공함.");
                            Optional.of(request.getInputStream().readAllBytes()).ifPresent(
                                    input -> {
                                        System.out.println(input);
                                    });
                            response.setStatus(201);
                            System.out.println("이름 정보 가져오기.");
                            Optional.of(authentication.getName())
                                    .ifPresent(name -> {
                                        System.out.println("authentication Name:" + name);
                                        System.out.println("인증 토큰 반환하기!!");
                                        String token = jwtProvider().GenerateToken(authentication);
                                        System.out.println("token:" + token);
                                        response.setHeader("Authorization", "Bearer " + token);
                                    });
                        })
                        .failureHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value())));
        http.logout(logout->
                logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));
        http.authorizeRequests(requests->
                requests.requestMatchers("/login").permitAll()
                        .requestMatchers("/join").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
}
