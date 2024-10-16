package com.swhwang.jwt_restapi.auth;

import com.swhwang.jwt_restapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(repo.existsByUsername(username)) {
            return new MyUserDetails(repo.findByUsername(username));
        }
        return new MyUserDetails(null);
    }
}
