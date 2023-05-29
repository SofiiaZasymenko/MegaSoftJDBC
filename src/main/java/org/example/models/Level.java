package org.example.models;

public enum Level {
    TRAINEE,
    JUNIOR,
    MIDDLE,
    SENIOR;

    public static Level fromName(String name) {
        for (Level level : values()) {
            if (level.name().equals(name)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Not valid name!");
    }
}