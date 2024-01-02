package com.todo.backend.service;

import com.todo.backend.repository.TaskEntity;
import com.todo.backend.repository.TaskRepository;
import com.todo.backend.repository.UserEntity;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements Serializable {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<List<TaskEntity>> getTasks(String username) {
        return taskRepository.findByUserUsername(username);
    }

    public void createTask(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void completeTask(Long taskId) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        taskEntity.get().setStatus("Complete");
        taskRepository.save(taskEntity.get());
    }

    public void updateTask(Long taskId, TaskEntity taskEntity) {
        try {
            Optional<TaskEntity> dbTaskEntity = taskRepository.findById(taskId);
            if (dbTaskEntity != null) {
                if(dbTaskEntity.get().getTitle() != null) {
                    
                }
                taskRepository.save(dbTaskEntity.get());
            }

        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }
}
