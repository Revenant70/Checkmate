package com.todo.backend.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="todo")
@Getter
@Setter
@ToString
public class TaskEntity {

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

    @Column(name = "userid")
    private Long userid;

}
