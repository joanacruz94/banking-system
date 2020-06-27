package com.ironhack.server.enums;

public enum Operation {
    CREDIT("Credit"),
    DEBIT("Debit");

    private String description;

    private Operation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
