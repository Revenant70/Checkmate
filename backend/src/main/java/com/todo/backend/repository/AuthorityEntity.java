package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint(name = "ix_auth_username", columnNames = {"username", "authority"})
})
@Getter
@Setter
@ToString
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

}
