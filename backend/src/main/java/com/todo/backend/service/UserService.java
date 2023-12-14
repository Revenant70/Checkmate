package com.todo.backend.service;

import com.todo.backend.repository.AuthorityEntity;
import com.todo.backend.repository.AuthorityRepository;
import com.todo.backend.repository.UserEntity;
import com.todo.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;


@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserEntity userSignup(UserEntity userEntity) {
        if(userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new UsernameNotFoundException("Username " + userEntity.getUsername() + " already exists");
        }

        AuthorityEntity authorityEntity = authorityRepository.findByAuthority("ROLE_USER");
        userEntity.getAuthorities().add(authorityEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
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

        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );
        SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
        Authentication authenticationReq = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(), authorities);
        contextHolder.setAuthentication(authenticationReq);

        SecurityContextHolder.setContext(contextHolder);

        return userEntity;
    }


}
