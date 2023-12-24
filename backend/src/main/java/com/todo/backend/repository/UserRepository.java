package com.todo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(@Param("username") String username);
}
