package com.carbonit.models;

import com.carbonit.AdventurerManager;
import com.carbonit.enums.Orientation;
import com.carbonit.exceptions.PositionConflictException;
import com.carbonit.interfaces.IMovable;

/**
 * Represents adventurer that can navigate a rectangular map based on a set of instructions.
 * The adventurer's position and orientation are managed by a {@link AdventurerManager}.
 */
public class Adventurer implements IMovable {
    /**
     * The adventurer's name.
     */
    private final String name;
    private Position position;
    /**
     * A string containing the instructions for the adventurer.
     * Instructions can include:
     * - 'G': Turn left
     * - 'D': Turn right
     * - 'A': Move forward
     */
    private String movementSequence;
    private int treasuresCollected = 0;

    /**
     * The orientation of the adventurer, represented by a cardinal direction:
     * - 'N': North
     * - 'E': East
     * - 'S': South
     * - 'W': West
     */
    private Orientation orientation;

    /**
     * Creates a new instance of {@code Lawnmower}.
     *
     * @param position         The initial X-coordinate and Y-coordinate of the adventurer.
     * @param orientation      The initial orientation of the adventurer (N, S, O, E).
     * @param movementSequence The set of movementSequence the adventurer should execute.
     * @throws PositionConflictException If the initial position is already occupied by another adventurer.
     */

    public Adventurer(String name, Position position, Orientation orientation, String movementSequence) {

        this.name = name;
        this.position = position;
        this.orientation = orientation;
        this.movementSequence = movementSequence;
    }


    public char popMove() {
        char move = movementSequence.charAt(0);
        movementSequence = movementSequence.substring(1);
        return move;
    }


    public Position getPosition() {
        return this.position;
    }


    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation newOrientation) {
        this.orientation = newOrientation;
    }

    public String getMovementSequence() {
        return this.movementSequence;
    }

    public Character getNextMove() {
        if (this.movementSequence == null || this.movementSequence.isEmpty()) {
            return null;
        }
        return this.movementSequence.charAt(0);
    }

    public void collectTreasure(Treasure treasure) {
        if (treasure.getQuantity() > 0) {
            treasuresCollected++;
            treasure.decreaseQuantity();
        }
    }

    @Override
    public String toString() {
        return "A-" + name + "-" + position.x() + "-" + position.y() + "-" + treasuresCollected;
    }

    @Override
    public void move(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getCollectedTreasures() {
        return treasuresCollected;
    }
}
