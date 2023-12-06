package com.todo.controllers;

import java.util.Optional;
import java.util.List;
import java.util.Map;

import com.todo.repositories.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.service.UsersService;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins="http://localhost:5173")
public class UsersController {
  
    @Autowired
    private UsersService usersService;

    @GetMapping("/{user_id}")
    public ResponseEntity<Optional<UserDetails>> userItems(@PathVariable Long user_id) {
        return new ResponseEntity<Optional<UserDetails>>(usersService.getUsersSingle(user_id), HttpStatus.OK);
    }

    
}
