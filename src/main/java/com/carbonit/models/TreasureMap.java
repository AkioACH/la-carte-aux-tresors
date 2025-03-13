package com.carbonit.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreasureMap {


    private final List<Mountain> mountains = new ArrayList<>();
    private final List<Treasure> treasures = new ArrayList<>();
    private int width;
    private int height;

    public TreasureMap() {
    }

    public void setBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    public void addMountain(Mountain mountain) {
        mountains.add(mountain);
    }

    public boolean isMountainAt(Position position) {
        return mountains.stream().anyMatch(mountain -> mountain.position().equals(position));
    }

    public boolean isWithinBounds(Position position) {
        return position.x() >= 0 && position.x() <= width && position.y() >= 0 && position.y() <= height;
    }

    public Treasure getTreasureAt(Position nextPosition) {
        if (nextPosition == null || treasures.isEmpty()) {
            return null;
        }
        return treasures.stream()
                .filter(treasure -> nextPosition.equals(treasure.getPosition())) // Vérification propre
                .findFirst()
                .orElse(null);
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void cleanEmptyTreasures() {
        treasures.removeIf(treasure -> treasure.getQuantity() == 0);
    }

    public String mapAndMountainToString() {
        String mapDescription = String.format("C - %d - %d%n", width, height);

        String mountainsDescription = mountains.stream()
                .map(mountain -> String.format("M - %d - %d", mountain.position().x(), mountain.position().y()))
                .collect(Collectors.joining("\n"));

        return mountains.isEmpty() ? mapDescription : mapDescription + mountainsDescription + "\n";
    }

}


