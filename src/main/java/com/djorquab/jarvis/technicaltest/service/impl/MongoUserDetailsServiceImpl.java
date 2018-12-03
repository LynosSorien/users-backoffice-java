package com.djorquab.jarvis.technicaltest.service.impl;

import com.djorquab.jarvis.technicaltest.model.Users;
import com.djorquab.jarvis.technicaltest.repository.UsersRepository;
import com.djorquab.jarvis.technicaltest.service.MongoUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("mongoUserDetailsService")
@Slf4j
public class MongoUserDetailsServiceImpl implements MongoUserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username "+username+" not found!");
        }
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        if (user.getRoles() != null) {
            String[] roles = user.getRoles().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        log.info("Loaded user {} with authorities {}", user.getUsername(), authorities);
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
