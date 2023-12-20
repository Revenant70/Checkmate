package com.todo.backend.controller;

import com.todo.backend.repository.UserEntity;
import com.todo.backend.service.JwtService;
import com.todo.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody UserEntity user) throws Exception{
        try {

            // Attempt to authenticate the user
            UserEntity userEntity = userService.userSignup(user);
            if (userEntity != null) {
                // User authentication successful
                return new ResponseEntity<>("User created successfully", HttpStatus.OK);
            } else {
                // User signup failed
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create user");
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during user signup: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<UserEntity> deleteUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user) throws Exception{
        System.out.println(user);
        try {
            if(userService.userLogin(user) != null) {
                String token = jwtService.generateToken(user.getUsername());
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during user authentication: " + e.getMessage());
        }

    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity changeUserPassword(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user.getPassword(), HttpStatus.OK);
    }
}
