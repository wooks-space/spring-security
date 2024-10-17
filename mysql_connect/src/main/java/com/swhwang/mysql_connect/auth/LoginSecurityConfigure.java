package com.swhwang.mysql_connect.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class LoginSecurityConfigure {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(form->
                form.loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_OK))
                        .defaultSuccessUrl("/index.html", true)
                        .failureHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
        );
        http.authorizeRequests(requests->
                requests.requestMatchers("/login","/join").permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }
}
