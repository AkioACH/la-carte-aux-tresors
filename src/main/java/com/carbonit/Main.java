package com.carbonit;

import com.carbonit.models.TreasureMap;

import java.io.IOException;


public class Main {
public static void main(String[] args) {
        try {

            TreasureMap treasureMap = new TreasureMap();
            AdventurerManager adventurerManager = new AdventurerManager();

            GameEngine engine = GameInitializer.initializeGame("resourcesmain.txt", treasureMap, adventurerManager);
            engine.execute();
            writeResult(adventurerManager, treasureMap);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeResult(AdventurerManager adventurerManager, TreasureMap treasureMap) {
        try {
            ResultFileWriter.writeResults("game_results.txt", adventurerManager.getAdventurers().stream().toList(), treasureMap);
        } catch (IOException e) {
            System.err.println("Error while writing the file : " + e.getMessage());
            e.printStackTrace();
        }
    }

}