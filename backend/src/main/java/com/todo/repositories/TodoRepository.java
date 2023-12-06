package com.todo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
    Optional<List<Todo>> findByUserId(Long userId);
}
