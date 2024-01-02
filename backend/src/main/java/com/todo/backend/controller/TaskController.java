package com.todo.backend.controller;

import com.todo.backend.repository.TaskEntity;
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

   @PutMapping("/tasks/{taskId}")
   public ResponseEntity<String> updateUserTask(@RequestBody TaskEntity taskEntity, @PathVariable Long taskId) throws Exception {
    System.out.println("This is the controller");
        try {
            taskService.updateTask(taskId, taskEntity);
            return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
       } catch(Exception exception) {
            System.out.println(exception);
        return new ResponseEntity<>("Couldn't update task", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @DeleteMapping("/tasks/{taskId}")
   public ResponseEntity<String> deleteUserTask(@PathVariable Long taskId) throws Exception {
       try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
       } catch(Exception exception) {
            System.out.println(exception);
        return new ResponseEntity<>("Couldn't delete task", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @PutMapping("/tasks/{taskId}/complete")
   public ResponseEntity<String> completeUserTask(@PathVariable Long taskId) throws Exception {
        try {
            taskService.completeTask(taskId);
            return new ResponseEntity<>("Task completed successfully", HttpStatus.OK);
       } catch(Exception exception) {
            System.out.println(exception);
        return new ResponseEntity<>("Couldn't complete task", HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }


}
