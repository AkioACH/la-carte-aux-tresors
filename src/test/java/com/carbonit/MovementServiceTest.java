package com.carbonit;

import com.carbonit.enums.Orientation;
import com.carbonit.models.Adventurer;
import com.carbonit.models.Mountain;
import com.carbonit.models.Position;
import com.carbonit.models.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementServiceTest {



    @Test
    public void testMoveForwardWithoutObstacle() {
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        treasureMap.addMountain(new Mountain(new Position(3, 1)));
        AdventurerManager adventurerManager = new AdventurerManager();
        MovementService movementService = new MovementService(treasureMap,adventurerManager);
        Adventurer adventurer = new Adventurer("Test", new Position(2, 2), Orientation.N, "A");
        movementService.moveForward(adventurer);
        assertEquals(new Position(2, 1), adventurer.getPosition());
    }

    @Test
    public void testMoveBlockedByMountain() {
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        treasureMap.addMountain(new Mountain(new Position(3, 1)));
        AdventurerManager adventurerManager = new AdventurerManager();
        MovementService movementService = new MovementService(treasureMap,adventurerManager);
        Adventurer adventurer = new Adventurer("Test", new Position(3, 2), Orientation.N, "A");
        movementService.moveForward(adventurer);
        assertEquals(new Position(3, 2), adventurer.getPosition());
    }
}
