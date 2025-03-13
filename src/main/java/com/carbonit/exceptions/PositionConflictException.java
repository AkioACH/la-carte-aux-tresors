package com.carbonit.exceptions;

public class PositionConflictException extends RuntimeException {
    public static final String POSITION_ALREADY_OCCUPIED_BY_ANOTHER_ADVENTURER = "The position is already occupied by another adventurer";
    public static final String POSITION_ALREADY_OCCUPIED_BY_ANOTHER_TREASURE = "The position is already occupied by another treasure";
    public static final String POSITION_OCCUPIED_BY_MOUNTAIN = "Adventurers can't be mountain goats! Move them to a safer spot";

    public PositionConflictException(String message, int positionX, int positionY) {
        super(message + ": " + positionX + "," + positionY);
    }
}