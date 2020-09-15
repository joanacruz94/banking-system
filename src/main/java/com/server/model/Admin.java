package com.server.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User{
    public Admin(String name, String email, String password) {
        super(name, email, password);
    }
}
