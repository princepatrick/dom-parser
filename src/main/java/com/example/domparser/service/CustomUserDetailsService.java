package com.example.domparser.service;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.Collections;

@Service()
public class CustomUserDetailsService implements UserDetailsService{
    private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from database or in-memory store
        // For demo purposes, returning a hard-coded user

        logger.info("Loading the user through the userName" + username);
        return new User("user", "{noop}password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }
}
