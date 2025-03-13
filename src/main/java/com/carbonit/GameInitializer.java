package com.carbonit;

import com.carbonit.exceptions.PositionConflictException;
import com.carbonit.models.Adventurer;
import com.carbonit.models.Position;
import com.carbonit.models.Treasure;
import com.carbonit.models.TreasureMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GameInitializer {

    /**
     * Initialize the game reading entry file and validate created objects
     *
     * @param inputFileName entry file's name .
     * @return `GameEngine` ready to be executed.
     * @throws IOException           in case of reading problem.
     * @throws IllegalStateException if constituency problem is detected.
     */
    public static GameEngine initializeGame(String inputFileName, TreasureMap treasureMap, AdventurerManager adventurerManager) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource(inputFileName);
        if (resource == null) {
            throw new FileNotFoundException("Input file not found in resources");
        }
        TreasureMapParser.parse(new File(resource.getFile()), treasureMap, adventurerManager);
        validateGameData(treasureMap, adventurerManager.getAdventurers().stream().toList());
        return new GameEngine(adventurerManager, new MovementService(treasureMap, adventurerManager));
    }

    /**
     * Check consistency of game's data
     *
     * @param treasureMap .
     * @throws IllegalStateException if error is detected.
     */
    public static void validateGameData(TreasureMap treasureMap, List<Adventurer> adventurers) {

        for (Adventurer adventurer : adventurers) {
            Position position = adventurer.getPosition();

            if (treasureMap.isMountainAt(position)) {
                throw new IllegalStateException(PositionConflictException.POSITION_OCCUPIED_BY_MOUNTAIN + position);
            }

            long count = adventurers.stream()
                    .filter(a -> a.getPosition().equals(position))
                    .count();
            if (count > 1) {
                throw new IllegalStateException(PositionConflictException.POSITION_ALREADY_OCCUPIED_BY_ANOTHER_ADVENTURER + position);
            }
        }

        for (Treasure treasure : treasureMap.getTreasures()) {
            Position position = treasure.getPosition();

            if (treasureMap.isMountainAt(position)) {
                throw new IllegalStateException(PositionConflictException.POSITION_OCCUPIED_BY_MOUNTAIN + position);
            }

            long count = treasureMap.getTreasures().stream()
                    .filter(a -> a.getPosition().equals(position))
                    .count();
            if (count > 1) {
                throw new IllegalStateException(PositionConflictException.POSITION_ALREADY_OCCUPIED_BY_ANOTHER_TREASURE + position);
            }
        }
    }
}

