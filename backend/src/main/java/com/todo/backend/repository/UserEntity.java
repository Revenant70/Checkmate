package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userid;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled", unique = true)
    private boolean enabled;

}
