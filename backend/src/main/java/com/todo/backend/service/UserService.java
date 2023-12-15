package com.todo.backend.service;

import com.todo.backend.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserEntity userSignup(UserEntity userEntity) {
        if(userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new UsernameNotFoundException("Username " + userEntity.getUsername() + " already exists");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setUsername(userEntity.getUsername());
        authorityEntity.setAuthority("ROLE_USER");

        authorityRepository.save(authorityEntity);

        return userRepository.save(userEntity);
    }

    public UserEntity userLogin(UserEntity userEntity){
        if(userEntity.getUsername() == null){
            throw new UsernameNotFoundException("User cannot be null");
        }
        if(userRepository.findByUsername(userEntity.getUsername()) == null) {
            throw new UsernameNotFoundException("User " + userEntity.getUsername() + " was not found");
        }
        UserEntity dbUser = userRepository.findByUsername(userEntity.getUsername());
        if(!passwordEncoder.matches(userEntity.getPassword(), dbUser.getPassword())) {
            throw new BadCredentialsException("Password was incorrect");
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = userDetailsManager.loadUserByUsername(userEntity.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), null, userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return userEntity;
    }


}
