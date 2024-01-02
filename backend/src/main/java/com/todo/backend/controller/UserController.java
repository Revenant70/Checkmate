package com.todo.backend.controller;

import com.todo.backend.repository.UserEntity;
import com.todo.backend.service.JwtService;
import com.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody UserEntity user) throws Exception{
        UserDetails userDetails = userService.userSignup(user);
        if(userDetails != null) {
            return new ResponseEntity<>("Signup successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<UserEntity> deleteUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user) throws Exception{
        try{
            UserEntity userEntity = userService.userLogin(user);
            if(userEntity == null) {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } catch(AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<?> editUserProfile(@RequestBody UserEntity updatedUser, Authentication authentication) throws Exception{
        try {
            userService.editUserProfile(updatedUser, authentication);
            return new ResponseEntity<String>("Profile edited", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Could not edit profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity<?> deleteUserProfile(Authentication authentication) throws Exception {
        try {
            userService.deleteUserProfile(authentication);
            return new ResponseEntity<>("Profile Deleted", HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>("Could not delete profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
