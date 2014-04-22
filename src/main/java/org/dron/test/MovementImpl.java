package org.dron.test;

import org.dron.Movement;

/**
 * Created by PhantomX on 20.04.14.
 */
public class MovementImpl implements Movement {
    private int pitch;
    private int roll;
    private int yaw;

    public static Movement straight(int pitch) {
        return new MovementImpl(pitch, 0, 0);
    }

    public static Movement turnRight(int yaw) {
        return new MovementImpl(0, 0, yaw);
    }

    public static Movement turnLeft(int yaw) {
        return new MovementImpl(0, 0, -yaw);
    }

    public MovementImpl(int pitch, int roll, int yaw) {
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }

    @Override
    public int pitch() {
        return pitch;
    }

    @Override
    public int roll() {
        return roll;
    }

    @Override
    public int yaw() {
        return yaw;
    }
}
