package com.carbonit;

import com.carbonit.enums.Movement;
import com.carbonit.models.Adventurer;

/**
 * The {@code GameEngine} class is responsible for orchestrating game.
 *
 */
public class GameEngine {
    private final AdventurerManager adventurerManager;
    private final MovementService movementService;

    public GameEngine(AdventurerManager adventurerManager, MovementService movementService) {
        this.adventurerManager = adventurerManager;
        this.movementService = movementService;
    }

    /**
     * Executes all instructions for each adventurer.
     */
    public void execute() {
        boolean hasRemainingMoves;
        do {
            hasRemainingMoves = false;
            for (Adventurer adventurer : adventurerManager.getAdventurers()) {
                if (!adventurer.getMovementSequence().isEmpty()) {
                    executeMove(adventurer);
                    hasRemainingMoves = true;
                }
            }
        } while (hasRemainingMoves);
    }

    private void executeMove(Adventurer adventurer) {
        Movement move = Movement.fromString(adventurer.popMove());
        switch (move) {
            case G -> adventurer.setOrientation(adventurer.getOrientation().turnLeft());
            case D -> adventurer.setOrientation(adventurer.getOrientation().turnRight());
            case A -> movementService.moveForward(adventurer);
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        }
    }
}

