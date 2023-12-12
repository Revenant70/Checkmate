package com.todo.backend.service;

import com.todo.backend.repository.UserEntity;
import com.todo.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class UserService implements Serializable {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity userSignup(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserEntity deleteUser(UserEntity user){
        return null;
        // figure out how to delete by either user id or email
    }

    public UserDetails userLogin(String email, String password){
        UserEntity userEntity = userRepository.findByEmailAndPassword(email, password);

        if(userEntity != null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getUserRole())
                .build();
    }


}
