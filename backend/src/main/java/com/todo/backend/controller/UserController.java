package com.todo.backend.controller;

import com.todo.backend.repository.UserEntity;
import com.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> signupUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(userService.userSignup(user), HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<UserEntity> deleteUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user) throws Exception{
        try {

            UserEntity authenticatedUser = userService.userLogin(user.getEmail(), user.getPassword());
            // Attempt to authenticate the user
            if (authenticatedUser != null) {
                // User authentication successful
                return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
            } else {
                // User authentication failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during user authentication: " + e.getMessage());
        }

    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity getUserTasks() throws Exception{
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity changeUserPassword(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user.getPassword(), HttpStatus.OK);
    }
}
