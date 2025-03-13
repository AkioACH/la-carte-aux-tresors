package com.carbonit;

import com.carbonit.enums.Movement;
import com.carbonit.enums.Orientation;
import com.carbonit.models.Adventurer;
import com.carbonit.models.Position;
import com.carbonit.models.Treasure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AdventurerTest {

    @Test
    public void testMove() {
        Adventurer adventurer = new Adventurer("Indiana", new Position(2, 3), Orientation.N, "AAD");
        adventurer.move(new Position(3, 4));
        assertEquals(new Position(3, 4), adventurer.getPosition());
    }

    @Test
    public void testPopMove() {
        Adventurer adventurer = new Adventurer("Lara", new Position(1, 1), Orientation.S, "AGD");
        assertEquals(Movement.A, Movement.fromString(adventurer.popMove()));
        assertEquals(Movement.G, Movement.fromString(adventurer.popMove()));
    }

    @Test
    public void testCollectTreasure() {
        Adventurer adventurer = new Adventurer("Lara", new Position(1, 1), Orientation.S, "AGD");
        Treasure treasure = new Treasure(new Position(1, 1), 1);
        adventurer.collectTreasure(treasure);
        assertEquals(1, adventurer.getCollectedTreasures());
    }
}