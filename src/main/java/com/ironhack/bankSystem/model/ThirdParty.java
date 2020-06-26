package com.ironhack.bankSystem.model;

import javax.persistence.Entity;

@Entity
public class ThirdParty extends User {
    private String hashedKey;

    public ThirdParty(String name, String hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }
}
