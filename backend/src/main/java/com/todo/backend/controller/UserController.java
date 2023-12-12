package com.todo.backend.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.UserEntity;
import com.todo.backend.service.TaskService;
import com.todo.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/api")
@Scope("session")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HttpServletRequest request;


    @PostMapping("/users/signup")
    public ResponseEntity<String> signupUser(@RequestBody UserEntity user, Principal principal) throws Exception{
        try {

            UserEntity createdUser = userService.userSignup(user);
            // Attempt to authenticate the user
            if (createdUser != null) {
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

    @DeleteMapping("/users/delete-account")
    public ResponseEntity<UserEntity> deleteUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user, HttpSession session) throws Exception{
        try {
            UserDetails authenticatedUser = userService.userLogin(user.getEmail(), user.getPassword());
            // Attempt to authenticate the user
            if (authenticatedUser != null) {
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



    @PostMapping("/users/logout")
    public ResponseEntity logoutUser(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping("/users/change-password")
    public ResponseEntity changeUserPassword(@RequestBody UserEntity user) throws Exception{
        return new ResponseEntity(user.getPassword(), HttpStatus.OK);
    }



    // -------------------------------------- TASKS SECTION ------------------------------------------



    @GetMapping("/tasks")
    public ResponseEntity<?> getUserTasks() throws Exception {
        try {

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }



        return new ResponseEntity<>("User tasks retrieved successfully", HttpStatus.OK);
    }

    /*@PostMapping("/tasks")
    public ResponseEntity createUserTask() throws Exception{
        return new ResponseEntity("Task created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{taskid}")
    public ResponseEntity getSingleUserTask() throws Exception {
        return new ResponseEntity(, HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity updateUserTask() throws Exception {
        return new ResponseEntity(, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity deleteUserTask() throws Exception {
        return new ResponseEntity(, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}/complete")
    public ResponseEntity deleteUserTask() throws Exception {
        return new ResponseEntity(, HttpStatus.OK);
    }*/
}
