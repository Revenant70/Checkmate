package com.todo.backend.repository;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "resettokens")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ResetToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "expirydate")
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private UserEntity user;

}
