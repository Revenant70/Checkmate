package com.todo.repositories;

import jakarta.persistence.*;

@Entity
@Table(name="authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long authId;

    @Column(name="username")
    String userName;

    @Column(name="authority")
    String authority;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }



    @Override
    public String toString() {
        return "Authorities{" +
                "authId=" + authId +
                ", userName='" + userName + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
