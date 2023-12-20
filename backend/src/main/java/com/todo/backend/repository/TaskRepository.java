package com.todo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


    Optional<List<TaskEntity>> findByUserUsername(String username);
}
