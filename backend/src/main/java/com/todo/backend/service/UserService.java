package com.todo.backend.service;

import com.todo.backend.repository.UserEntity;
import com.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity userSignup(UserEntity user){
        return userRepository.save(user);
    }

    public UserEntity deleteUser(UserEntity user){
        return null;
        // figure out how to delete by either user id or email
    }

    public UserEntity userLogin(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

}
