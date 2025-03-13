package com.carbonit;

import com.carbonit.enums.Orientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrientationTest {

    @Test
    public void testTurnLeft() {
        assertEquals(Orientation.O, Orientation.N.turnLeft());
        assertEquals(Orientation.S, Orientation.O.turnLeft());
        assertEquals(Orientation.E, Orientation.S.turnLeft());
        assertEquals(Orientation.N, Orientation.E.turnLeft());
    }

    @Test
    public void testTurnRight() {
        assertEquals(Orientation.E, Orientation.N.turnRight());
        assertEquals(Orientation.S, Orientation.E.turnRight());
        assertEquals(Orientation.O, Orientation.S.turnRight());
        assertEquals(Orientation.N, Orientation.O.turnRight());
    }
}