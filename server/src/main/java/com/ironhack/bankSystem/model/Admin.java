package com.ironhack.bankSystem.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User{

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}
