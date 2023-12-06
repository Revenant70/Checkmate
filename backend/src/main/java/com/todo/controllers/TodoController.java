package com.todo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.repositories.Todo;
import com.todo.repositories.TodoRepository;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/api/v1/users/tasks")
@CrossOrigin(origins="http://localhost:5173")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository; 
    
    @Autowired
    private TodoService todoService;

    @GetMapping("/alltasks")
    public ResponseEntity<Iterable<Todo>> getTasks(){
        return new ResponseEntity<Iterable<Todo>>(todoService.getTasks(), HttpStatus.OK); 
    }

    @GetMapping("/{getTaskById}")
    public ResponseEntity<Optional<Todo>> getTaskById(@PathVariable("getTaskById") Long task_id){
        return new ResponseEntity<Optional<Todo>>(todoService.getTaskById(task_id), HttpStatus.OK);
    }
    
    @GetMapping("/{user_id}")
    public ResponseEntity<Optional<List<Todo>>> getAllTasksForUser(@PathVariable("user_id") Long userId){
        return new ResponseEntity<Optional<List<Todo>>>(todoService.getTasksByUserId(userId), HttpStatus.OK);
    }

   @GetMapping("/filter")
   @ResponseBody
    public ResponseEntity<List<Todo>> filterTasks(@RequestParam Map<String, Object> todo) throws IOException{
        List<Todo> filterTasks = todoService.filterTasks(todo);
        return ResponseEntity.ok(filterTasks);
    }

    @PostMapping("/create")
    public @ResponseBody String createTask(@RequestBody Todo todo) {
        todo.setUserId(todo.getUserId());
        todo.setTitle(todo.getTitle());
        todo.setDesc(todo.getDesc());
        todo.setReminders(todo.getReminders());
        todo.setStatus(todo.getStatus());
        todoRepository.save(todo);
        return "Saved";
    }

    @PutMapping("/{task_id}/update") 
    public @ResponseBody ResponseEntity<Todo> updateTask(@PathVariable long task_id, @RequestParam String title, String desc, String reminders, String status){
        Todo updateTask = todoRepository.findById(task_id).orElseThrow(null);
        updateTask.setTitle(title);
        updateTask.setDesc(desc);
        updateTask.setReminders(reminders);
        updateTask.setStatus(status);
        todoRepository.save(updateTask);
        
        return ResponseEntity.ok(updateTask);
    }

    // Maybe create some sort of daily goal or counter for every one of these the user completes
    @PutMapping("/{task_id}/complete") 
    public @ResponseBody ResponseEntity<Todo> completeTask(@PathVariable long task_id, @RequestParam String title, String desc, String reminders, String status){
        Todo updateTask = todoRepository.findById(task_id).orElseThrow(null);
        updateTask.setTitle(title);
        updateTask.setDesc(desc);
        updateTask.setReminders(reminders);
        updateTask.setStatus(status);
        todoRepository.save(updateTask);
        
        return ResponseEntity.ok(updateTask);
    }


    @DeleteMapping("/{task_id}/delete")
    public @ResponseBody ResponseEntity<Todo> deleteTask(@PathVariable long task_id){
        Todo deleteTask = todoRepository.findById(task_id).orElse(null);
        todoRepository.delete(deleteTask);
        return ResponseEntity.ok(deleteTask);

    }


}