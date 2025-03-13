package com.carbonit.models;

public class Treasure {
    private final Position position;
    private int quantity;

    public Treasure(Position position, int quantity) {
        this.position = position;
        this.quantity = quantity;
    }

    public Position getPosition() {
        return position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    @Override
    public String toString() {
        return "T-" + position.x() + "-" + position.y() + "-" + quantity;
    }

}
