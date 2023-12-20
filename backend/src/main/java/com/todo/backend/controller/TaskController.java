package com.todo.backend.controller;

import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.UserEntity;
import com.todo.backend.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TaskController {


    @Autowired
    private TaskService taskService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/tasks")
    public ResponseEntity<?> getUserTasks(Authentication authentication) throws Exception {
        Optional<List<TaskEntity>> tasks;
        System.out.println(authentication.getPrincipal());
        try {
            tasks = taskService.getTasks(authentication.getName());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("There was an internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/tasks")
    public ResponseEntity createUserTask(@RequestBody TaskEntity tasks) throws Exception{
        System.out.println(tasks);
        return new ResponseEntity("Task created successfully", HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @GetMapping("/tasks/{taskid}")
//    public ResponseEntity getSingleUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @PutMapping("/tasks/{taskId}")
//    public ResponseEntity updateUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @DeleteMapping("/tasks/{taskId}")
//    public ResponseEntity deleteUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @DeleteMapping("/tasks/{taskId}/complete")
//    public ResponseEntity deleteUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }


}
