package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class UserEntity implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "enabled", unique = true)
    private boolean enabled = true;



}
