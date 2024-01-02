package com.todo.backend.service;

import com.todo.backend.repository.*;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


@Service
public class UserService implements Serializable {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails userSignup(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity userLogin(UserEntity userEntity){
        System.out.println(userEntity);
        return userRepository.findByUsername(userEntity.getUsername());
    }

    public void editUserProfile(UserEntity updatedUser, Authentication authentication){
        try {
            UserEntity dbUserEntity = userRepository.findByUsername(authentication.getName());
            if(dbUserEntity != null) {
            if(updatedUser.getFirstname() != null) {
                dbUserEntity.setFirstname(updatedUser.getFirstname());
            }
            if(updatedUser.getLastname() != null) {
                dbUserEntity.setLastname(updatedUser.getLastname());
            }
            if(updatedUser.getUsername() != null) {
                dbUserEntity.setUsername(updatedUser.getUsername());
            }
            if(updatedUser.getPassword() != null) {
                dbUserEntity.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            userRepository.save(dbUserEntity);
        }
        } catch(EntityNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deleteUserProfile(Authentication authentication) {
        try {
            UserEntity userEntity = userRepository.findByUsername(authentication.getName());
            userRepository.deleteById(userEntity.getUserid());
        } catch(Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }

}
