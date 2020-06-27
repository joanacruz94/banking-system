package com.ironhack.server.enums;

public enum Status {
    FROZEN("Frozen"),
    ACTIVE("Active");

    private String description;

    private Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
