package com.carbonit;

import com.carbonit.enums.Orientation;
import com.carbonit.models.Mountain;
import com.carbonit.models.Position;
import com.carbonit.models.Treasure;
import com.carbonit.models.TreasureMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

/**
 * A parser for reading and validating a file containing TreasureMap configurations.
 */
public class TreasureMapParser {

    public static final String CARDINAL_DIRECTION = "NSOE";

    /**
     * Parses a file containing TreasureMap and Adventurer configurations.
     *
     * @param file The input file containing the configurations.
     * @throws IOException              If an I/O error occurs reading the file.
     * @throws IllegalArgumentException If the input contains invalid values or is malformed.
     */
    public static void parse(File file, TreasureMap treasureMap, AdventurerManager adventurerManager) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty() || lines.size() < 2) {
            throw new IllegalArgumentException("Input file is empty or does not contain sufficient data.");
        }
        // Remove spaces
        removeSpaces(lines);
        // Remove comments lines
        removeComments(lines);

        parseMap(lines, treasureMap);

        for (int i = 1; i < lines.size(); i++) {

            // Parse Adventurer position and orientation
            String[] position = lines.get(i).split("-");
            int initialX, initialY;

            switch (position[0]) {
                case "M":
                    if (position.length != 3) {
                        throw new IllegalArgumentException("Mountain line is invalid. Expected format: 'M - initialX - initialY'");
                    }
                    initialX = parseNonNegativeInteger(position[1], "Mountain initial X-coordinate");
                    initialY = parseNonNegativeInteger(position[2], "Mountain initial Y-coordinate");
                    treasureMap.addMountain(new Mountain(new Position(initialX, initialY)));
                    break;
                case "T":
                    if (position.length != 4) {
                        throw new IllegalArgumentException("Treasure line is invalid. Expected format: 'T - initialX - initialY - treasure number'");
                    }
                    initialX = parseNonNegativeInteger(position[1], "Treasure initial X-coordinate");
                    initialY = parseNonNegativeInteger(position[2], "Treasure initial Y-coordinate");

                    int treasureNumber = parseNonNegativeInteger(position[3], "Treasure place can't be empty");

                    treasureMap.addTreasure(new Treasure(new Position(initialX, initialY), treasureNumber));
                    break;
                case "A":
                    if (position.length != 6) {
                        throw new IllegalArgumentException("Adventurer position line is invalid. Expected format: 'A - Name - initialX - initialY - orientation - movement sequence'");
                    }
                    String name = parseNonEmptyString(position[1], "Adventurer name");
                    initialX = parseNonNegativeInteger(position[2], "Adventurer initial X-coordinate");
                    initialY = parseNonNegativeInteger(position[3], "Adventurer initial Y-coordinate");
                    char orientation = parseOrientation(position[4]);

                    if (orientation != 'N' && orientation != 'S' && orientation != 'O' && orientation != 'E') {
                        throw new IllegalArgumentException("Unexpected character for orientation: " + orientation);
                    }

                    String sequence = parseNonEmptyString(position[5], "Adventurer movement sequence");

                    // Parse Adventurer instructions
                    validateInstructions(sequence);

                    adventurerManager.addAdventurer(name, new Position(initialX, initialY), Orientation.fromChar(orientation), sequence);
                    break;
                default:
                    throw new IllegalArgumentException("Starting line is invalid. Line is supposed to start with 'M','T' or 'A'.");
            }

        }
    }

    private static void parseMap(List<String> lines, TreasureMap treasureMap) {

        String[] dimensions = lines.get(0).split("-");
        if (dimensions.length != 3) {
            throw new IllegalArgumentException("Treasure Map dimensions line is invalid. Expected format: 'maxX maxY'");
        }
        if (!dimensions[0].equals("C")) {
            throw new IllegalArgumentException("Map line is invalid. Expected format: 'T - initialX - initialY'");
        }
        int maxX = parsePositiveInteger(dimensions[1], "Treasure Map width (maxX)");
        int maxY = parsePositiveInteger(dimensions[2], "Treasure Map height (maxY)");
        treasureMap.setBounds(maxX, maxY);
    }

    /**
     * Remove commentary lines starting with "#" .
     *
     * @param lines lines to parse.
     */
    private static void removeComments(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.startsWith("#")) {
                iterator.remove();
            }
        }
    }

    /**
     * Remove commentary lines starting with "#" .
     *
     * @param lines lines to parse.
     */
    private static void removeSpaces(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        lines.replaceAll(s -> s.replaceAll("\\s+", ""));
    }

    /**
     * Parses and validates a positive integer value.
     *
     * @param value     The value to parse.
     * @param fieldName The name of the field being parsed (for error messages).
     * @return The parsed positive integer.
     * @throws IllegalArgumentException If the value is not a valid positive integer.
     */
    private static int parsePositiveInteger(String value, String fieldName) {
        int result = parseNonNegativeInteger(value, fieldName);
        if (result == 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0.");
        }
        return result;
    }

    /**
     * Parses and validates a non-empty String value.
     *
     * @param value     The value to parse.
     * @param fieldName The name of the field being parsed (for error messages).
     * @return The parsed non-empty String.
     * @throws IllegalArgumentException If the value is not a valid String.
     */
    private static String parseNonEmptyString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must be a non-empty string.");
        }
        return value;
    }

    /**
     * Parses and validates a non-negative integer value.
     *
     * @param value     The value to parse.
     * @param fieldName The name of the field being parsed (for error messages).
     * @return The parsed non-negative integer.
     * @throws IllegalArgumentException If the value is not a valid non-negative integer.
     */
    private static int parseNonNegativeInteger(String value, String fieldName) {
        try {
            int result = Integer.parseInt(value);
            if (result < 0) {
                throw new IllegalArgumentException(fieldName + " must be a non-negative integer.");
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.", e);
        }
    }

    /**
     * Parses and validates Adventurer orientation.
     *
     * @param value The value to parse.
     * @return The parsed orientation character.
     * @throws IllegalArgumentException If the value is not a valid orientation (N, S, O, E).
     */
    private static char parseOrientation(String value) {
        if (value.length() != 1) {
            throw new IllegalArgumentException("Orientation must be a single character (N, S, O, E).");
        }
        char orientation = value.charAt(0);
        if (CARDINAL_DIRECTION.indexOf(orientation) == -1) {
            throw new IllegalArgumentException("Orientation must be one of the following: N, S, O, E.");
        }
        return orientation;
    }

    /**
     * Validates Adventurer movement sequence.
     *
     * @param instructions The instructions string to validate.
     * @throws IllegalArgumentException If the instructions contain invalid characters.
     */
    private static void validateInstructions(String instructions) {
        if (!instructions.matches("[AGD]*")) {
            throw new IllegalArgumentException("Instructions must only contain the characters A, G, and D.");
        }
    }
}