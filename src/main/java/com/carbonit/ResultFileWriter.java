package com.carbonit;

import com.carbonit.models.Adventurer;
import com.carbonit.models.Treasure;
import com.carbonit.models.TreasureMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResultFileWriter {

    /**
     * Generate result file with map description and adventurers description
     *
     * @param fileName    File's name.
     * @param adventurers adventurers list (with theirs final positions).
     * @param treasureMap treasureMap.
     */
    public static void writeResults(String fileName, List<Adventurer> adventurers, TreasureMap treasureMap) throws IOException {
        Path outputDirectory = Paths.get("results");
        if (!outputDirectory.toFile().exists()) {
            outputDirectory.toFile().mkdirs();
        }

        Path filePath = outputDirectory.resolve(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(treasureMap.mapAndMountainToString());


            writer.write("# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants} \n");
            for (Treasure treasure : treasureMap.getTreasures()) {
                writer.write(String.format(
                        "T - %d - %d - %d\n",
                        treasure.getPosition().x(),
                        treasure.getPosition().y(),
                        treasure.getQuantity()
                ));
            }

            writer.write("# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés} \n");
            for (Adventurer adventurer : adventurers) {
                writer.write(String.format(
                        "A - %s - %d - %d - %s - %d\n",
                        adventurer.getName(),
                        adventurer.getPosition().x(),
                        adventurer.getPosition().y(),
                        adventurer.getOrientation(),
                        adventurer.getCollectedTreasures()
                ));
            }

        }
    }
}
