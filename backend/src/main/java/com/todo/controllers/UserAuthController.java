package com.todo.controllers;

import java.util.Map;
import java.util.Random;

import com.todo.repositories.UserDetails;
import com.todo.service.UserAuthService;
import com.todo.service.UsersService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins="http://localhost:5173")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/send-confirmation-code")
    public ResponseEntity<String> sendConfirmationCode(@RequestBody UserDetails passwordReset) {
        System.out.println("\n\n" + passwordReset.getEmail() + "\n\n");
        // Generate a random confirmation code (you may use a library for this)
        String confirmationCode = String.valueOf(100000 + new Random().nextInt(900000));

        userAuthService.sendConfirmationCode(passwordReset.getEmail(), confirmationCode);

        return ResponseEntity.ok("Confirmation code sent successfully");
    }

    // user login check
    @PostMapping("/login")
    public ResponseEntity<UserDetails> checkUserLogin(@RequestBody UserDetails loginForm) {
        return new ResponseEntity<UserDetails>(usersService.checkUserLogin(loginForm.getEmail(), loginForm.getPassword()), HttpStatus.OK);
    }
}