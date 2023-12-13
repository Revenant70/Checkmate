package com.todo.backend.controller;

import com.todo.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {


    @Autowired
    private TaskService taskService;


    @GetMapping("/tasks")
    public ResponseEntity<?> getUserTasks(Authentication authentication) throws Exception {
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
