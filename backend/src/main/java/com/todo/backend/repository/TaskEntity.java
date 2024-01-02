package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name="todo")
@Getter
@Setter
@ToString
public class TaskEntity implements Serializable {

    @Id
    @Column(name = "taskid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskid;

    @Column(name = "description")
    private String desc;

    @Column(name = "duedate")
    private String dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private UserEntity user;

}
