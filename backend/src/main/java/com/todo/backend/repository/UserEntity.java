package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name="users")
@Data
@DynamicUpdate
@DynamicInsert
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false, unique = true, updatable = false)
    private Long userid;

    @Column(name = "username", nullable = false, unique = true, updatable = true)
    private String username;

    @Column(name = "password", nullable = false, updatable = true)
    private String password;

    @Column(name = "firstname", updatable = true)
    private String firstname;

    @Column(name = "lastname", updatable = true)
    private String lastname;

    @Column(name = "enabled", nullable = false, updatable = true)
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
