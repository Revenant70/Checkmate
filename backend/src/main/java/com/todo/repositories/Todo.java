package com.todo.repositories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="todo")
public class Todo {
    @Column(name="userid")
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="taskid")
    private Long taskId;
    @Column(name="description")
    private String desc;
    @Column(name="reminders")
    private String reminders;
    @Column(name="status")
    private String status;
    @Column(name="title")
    private String title;
    

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReminders() {
        return this.reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", taskId='" + getTaskId() + "'" +
            ", desc='" + getDesc() + "'" +
            ", reminders='" + getReminders() + "'" +
            ", status='" + getStatus() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
