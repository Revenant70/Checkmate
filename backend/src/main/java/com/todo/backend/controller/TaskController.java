package com.todo.backend.controller;

import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.UserEntity;
import com.todo.backend.repository.UserRepository;
import com.todo.backend.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TaskController {


    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/tasks")
    public ResponseEntity<?> getUserTasks(Authentication authentication) throws Exception {
        Optional<List<TaskEntity>> tasks;
        try {
            tasks = taskService.getTasks(authentication.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>("There was an internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(tasks);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> createUserTask(@RequestBody TaskEntity tasks, Authentication auth) throws Exception{
        try{
            String loggedInUser = auth.getName();
            tasks.setUser(userRepository.findByUsername(loggedInUser));
            taskService.createTask(tasks);
            System.out.println(tasks);
            return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
        
    }
//    @GetMapping("/tasks/{taskid}")
//    public ResponseEntity getSingleUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }
//
//    @PutMapping("/tasks/{taskId}")
//    public ResponseEntity updateUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }
//
   @DeleteMapping("/tasks/{taskId}")
   public ResponseEntity<String> deleteUserTask(@PathVariable Long taskId) throws Exception {
       try {
            System.out.println(taskId);
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
       } catch(Exception exception) {
            System.out.println(exception);
        return new ResponseEntity<>("Couldn't delete task", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
//
//    @DeleteMapping("/tasks/{taskId}/complete")
//    public ResponseEntity deleteUserTask() throws Exception {
//        return new ResponseEntity(, HttpStatus.OK);
//    }


}
