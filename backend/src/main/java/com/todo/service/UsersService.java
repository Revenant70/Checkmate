package com.todo.service;

import java.util.Optional;

import com.todo.repositories.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    public UsersService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<UserDetails> getUsers(){
        return usersRepository.findAll();
    }

    public Optional<UserDetails> getUsersSingle(Long user_id){
        return usersRepository.findById(user_id);
    }

    public UserDetails checkUserLogin(String email, String password){
        UserDetails user = usersRepository.findByEmailIgnoreCase(email);

        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }

    }

}
