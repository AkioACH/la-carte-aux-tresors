package com.carbonit.models;

public record Mountain(Position position) {

    @Override
    public String toString() {
        return "M-" + position.x() + "-" + position.y();
    }
}