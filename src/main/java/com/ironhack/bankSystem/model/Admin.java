package com.ironhack.bankSystem.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    public Admin(String name) {
        super(name);
    }
}
