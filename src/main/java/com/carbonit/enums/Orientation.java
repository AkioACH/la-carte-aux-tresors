package com.carbonit.enums;

import com.carbonit.models.Position;

public enum Orientation {
    N, E, S, O;

    public static Orientation fromChar(char c) {
        return switch (c) {
            case 'N' -> N;
            case 'E' -> E;
            case 'S' -> S;
            case 'O' -> O;
            default -> throw new IllegalArgumentException("Invalid orientation: " + c);
        };
    }

    public Orientation turnLeft() {
        return switch (this) {
            case N -> O;
            case O -> S;
            case S -> E;
            case E -> N;
        };
    }

    public Orientation turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> O;
            case O -> N;
        };
    }

    public Position moveForward(Position currentPosition) {
        return switch (this) {
            case N -> new Position(currentPosition.x(), currentPosition.y() - 1);
            case S -> new Position(currentPosition.x(), currentPosition.y() + 1);
            case E -> new Position(currentPosition.x() + 1, currentPosition.y());
            case O -> new Position(currentPosition.x() - 1, currentPosition.y());
        };
    }
}
