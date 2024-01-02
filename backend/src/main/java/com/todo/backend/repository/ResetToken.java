package com.todo.backend.repository;

import java.util.Date;

import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetToken {

    private String token;
    private Date expiryDate;

    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private UserEntity user;

}
