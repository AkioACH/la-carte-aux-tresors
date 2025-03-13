package com.carbonit;

import com.carbonit.enums.Orientation;
import com.carbonit.exceptions.PositionConflictException;
import com.carbonit.models.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameInitializerTest {

    @Test
    public void testValidGameInitialization() throws IOException {
        GameEngine engine = GameInitializer.initializeGame("valid_input.txt", new TreasureMap(), new AdventurerManager());
        Assertions.assertNotNull(engine);
    }

    @Test
    public void testInvalidGameInitialization() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            GameInitializer.initializeGame("invalid_input.txt", new TreasureMap(), new AdventurerManager());
        });
        assertTrue(exception.getMessage().contains("Input file not found in resources"));
    }

    @Test
    public void testInvalidPositionBetweenMountainAndAdventurerAtInitialization() {

        Position position = new Position(3, 1);
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        treasureMap.addMountain(new Mountain(position));
        AdventurerManager adventurerManager = new AdventurerManager();
        adventurerManager.addAdventurer("test",position, Orientation.O,"D");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
        GameInitializer.validateGameData(treasureMap,adventurerManager.getAdventurers().stream().toList());
        });
        assertTrue(exception.getMessage().contains(PositionConflictException.POSITION_OCCUPIED_BY_MOUNTAIN));
    }

    @Test
    public void testInvalidPositionBetweenMountainAndATreasureAtInitialization() {

        Position position = new Position(3, 1);
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        treasureMap.addMountain(new Mountain(position));
        treasureMap.addTreasure(new Treasure(position,2));
        AdventurerManager adventurerManager = new AdventurerManager();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
        GameInitializer.validateGameData(treasureMap,adventurerManager.getAdventurers().stream().toList());
        });
        assertTrue(exception.getMessage().contains(PositionConflictException.POSITION_OCCUPIED_BY_MOUNTAIN));
    }

    @Test
    public void testInvalidPositionBetweenTwoAdventurerAtInitialization() {

        Position position = new Position(3, 1);
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        AdventurerManager adventurerManager = new AdventurerManager();
        adventurerManager.addAdventurer("test",position, Orientation.O,"D");
        Exception exception = assertThrows(PositionConflictException.class, () -> {
        adventurerManager.addAdventurer("test2",position, Orientation.O,"D");
        });
        assertTrue(exception.getMessage().contains(PositionConflictException.POSITION_ALREADY_OCCUPIED_BY_ANOTHER_ADVENTURER));
    }

    @Test
    public void testInvalidPositionBetweenTreasuresAtInitialization() {

        Position position = new Position(3, 1);
        TreasureMap treasureMap = new TreasureMap();
        treasureMap.setBounds(5, 5);
        treasureMap.addTreasure(new Treasure(position,1));
        treasureMap.addTreasure(new Treasure(position,3));
        AdventurerManager adventurerManager = new AdventurerManager();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            GameInitializer.validateGameData(treasureMap,adventurerManager.getAdventurers().stream().toList());
        });
        assertTrue(exception.getMessage().contains(PositionConflictException.POSITION_ALREADY_OCCUPIED_BY_ANOTHER_TREASURE));
    }
}
