package com.todo.backend.service;

import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Service
public class TaskService implements Serializable {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<List<TaskEntity>> getTasks(String username){
        return taskRepository.findByUserUsername(username);
    }

    public void createTask(TaskEntity taskEntity){
        
        taskRepository.save(taskEntity);
    }
}
