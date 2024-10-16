package com.swhwang.mysql_connect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfigure {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.formLogin(form->
                form.loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> response.setStatus(201))
                        .failureHandler((request, response, authentication) -> response.setStatus(401))
        );
        http.authorizeRequests(requests->
                requests.requestMatchers("/login").permitAll()
                        .requestMatchers("/join").permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }
}
