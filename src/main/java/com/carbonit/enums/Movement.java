package com.carbonit.enums;

public enum Movement {
    A, G, D;

    public static Movement fromString(char move) {
        return switch (move) {
            case 'A' -> A;
            case 'G' -> G;
            case 'D' -> D;
            default -> throw new IllegalArgumentException("Invalid movement: " + move);
        };
    }
}
