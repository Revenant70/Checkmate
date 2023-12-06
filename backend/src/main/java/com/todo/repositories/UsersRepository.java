package com.todo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<UserDetails, Long>{
    List<Todo> findByUserID(Long user_id);
    UserDetails findByEmailIgnoreCase(String email);
}
