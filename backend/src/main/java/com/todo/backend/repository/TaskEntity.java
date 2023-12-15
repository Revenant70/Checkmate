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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskid;

    @Column(name = "description")
    private String desc;

    @Column(name = "reminders")
    private String reminders;

    @Column(name = "status")
    private String status;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username" ,nullable = false)
    private UserEntity user;

}
