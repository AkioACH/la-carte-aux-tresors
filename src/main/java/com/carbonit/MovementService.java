package com.carbonit;

import com.carbonit.models.Adventurer;
import com.carbonit.models.Position;
import com.carbonit.models.Treasure;
import com.carbonit.models.TreasureMap;

import java.util.logging.Logger;

public class MovementService {
    private final TreasureMap treasureMap;
    private final AdventurerManager adventurerManager;

    public MovementService(TreasureMap treasureMap, AdventurerManager adventurerManager) {
        this.treasureMap = treasureMap;
        this.adventurerManager = adventurerManager;
    }

    public void moveForward(Adventurer adventurer) {
        Position nextPosition = getNextPosition(adventurer);

        if (adventurerManager.isPositionOccupied(nextPosition)) {
            return;
        }

        if (!treasureMap.isWithinBounds(nextPosition)) {
            return;
        }

        if (treasureMap.isMountainAt(nextPosition)) {
            return;
        }


        Treasure treasure = treasureMap.getTreasureAt(nextPosition);
        if (treasure != null) {
            adventurer.collectTreasure(treasure);
            treasureMap.cleanEmptyTreasures();
        }

        adventurer.move(nextPosition);
    }

    private Position getNextPosition(Adventurer adventurer) {
        return switch (adventurer.getOrientation()) {
            case N -> new Position(adventurer.getPosition().x(), adventurer.getPosition().y() - 1);
            case S -> new Position(adventurer.getPosition().x(), adventurer.getPosition().y() + 1);
            case O -> new Position(adventurer.getPosition().x() - 1, adventurer.getPosition().y());
            case E -> new Position(adventurer.getPosition().x() + 1, adventurer.getPosition().y());
        };
    }
}
