package com.carbonit;


import com.carbonit.enums.Orientation;
import com.carbonit.exceptions.PositionConflictException;
import com.carbonit.models.Adventurer;
import com.carbonit.models.Position;

import java.util.LinkedHashSet;

import static com.carbonit.exceptions.PositionConflictException.POSITION_ALREADY_OCCUPIED_BY_ANOTHER_ADVENTURER;

/**
 * The {@code AdventurerManager} class is responsible for managing adventurers. It ensures that
 * adventurer avoids conflicting positions.
 */
public class AdventurerManager {

    /**
     * Adventurers set in order to define priority for each turn
     */
    private final LinkedHashSet<Adventurer> adventurers = new LinkedHashSet<>();

    public AdventurerManager() {
    }

    public void addAdventurer(String name, Position position, Orientation orientation, String movements) {
        if (isPositionOccupied(position)) {
            throw new PositionConflictException(POSITION_ALREADY_OCCUPIED_BY_ANOTHER_ADVENTURER, position.x(), position.y());
        }
        Adventurer adventurer = new Adventurer(name, position, orientation, movements);
        adventurers.add(adventurer);
    }

    public boolean isPositionOccupied(Position position) {
        return adventurers.stream().toList().stream().anyMatch(adventurer -> adventurer.getPosition().equals(position));

    }

    public LinkedHashSet<Adventurer> getAdventurers() {
        return adventurers;
    }
}
