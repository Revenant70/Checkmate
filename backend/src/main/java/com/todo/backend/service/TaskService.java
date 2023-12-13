package com.todo.backend.service;

import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


@Service
public class TaskService implements Serializable {

    @Autowired
    private TaskRepository taskRepository;


}
