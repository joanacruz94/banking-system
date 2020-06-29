package com.ironhack.server.enums;

public enum Status {
    FROZEN("Frozen"),
    ACTIVE("Active");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
