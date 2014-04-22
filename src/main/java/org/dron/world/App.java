package org.dron.world;


import org.dron.Movement;
import org.dron.Ship;
import org.dron.Strategy;
import org.dron.test.TestStrategy;

import java.awt.*;
import java.io.File;

public class App {
    private World world;
    private Strategy strategy;
    private FrameImagePanel frameImage;
    private int FRAME_PER_SECOND = 1;

    public App(World world, Strategy strategy) {
        this.world = world;
        this.strategy = strategy;
    }

    public static void main(String[] args) {
		File file = new File("./world.jpg");
		World world = World.load(file);
        Strategy strategy = new TestStrategy();
        App app = new App(world, strategy);
        app.start();
	}

    private void start() {
        frameImage = new FrameImagePanel(world.draw());
        frameImage.start();
        while (true) {
            Movement movement = strategy.doMove(world.getShip());
            if (isEmptyMovement(movement)) {
                end();
                return;
            }
            world.moveShip(movement);
            frameImage.updateImage(world.draw());
            if (!world.checkShip()) {
                crash();
                return;
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000l/FRAME_PER_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void end() {
        drawString("END");
    }

    private boolean isEmptyMovement(Movement movement) {
        return null == movement ||
                (movement.pitch() == 0 && movement.roll() == 0 && movement.yaw() == 0);
    }

    private void drawString(String s) {
        Graphics graphics = frameImage.getGraphics();
        Font font = new Font(null, Font.ITALIC, 100);
        graphics.setFont(font);
        graphics.setColor(Color.RED);
        graphics.drawString(s, 50, 100);
    }

    private void crash() {
        drawString("CRASH");
    }


}