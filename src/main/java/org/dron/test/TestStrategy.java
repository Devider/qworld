package org.dron.test;

import org.dron.Movement;
import org.dron.Ship;
import org.dron.Strategy;

import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by PhantomX on 20.04.14.
 */
public class TestStrategy implements Strategy {

    private final LinkedList<Movement> movements;

    public TestStrategy() {
        movements = new LinkedList<Movement>();
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.turnRight(20));
        movements.add(MovementImpl.turnRight(20));
        movements.add(MovementImpl.turnRight(20));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.turnLeft(60));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.straight(10));
        movements.add(MovementImpl.straight(10));
    }

    @Override
    public Movement doMove(Ship ship) {

        return movements.pollFirst();
//        return new MovementImpl(10, 0, 0);
    }
}
