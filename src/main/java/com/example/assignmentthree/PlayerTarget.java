package com.example.assignmentthree;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * A purple player target that spawns at the bottom
 */
public class PlayerTarget extends GameObject {

    public static Paint paintTarget = new Paint();

    /**
     * Creates an instance of a PlayerTarget
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public PlayerTarget(int screenWidth, int screenHeight) {
        rand = new Random();

        //Generates a random number between the screens, accounting for the radius
        setXCoord(rand.nextInt(screenWidth - getRadius() * 2) + getRadius());
        setYCoord(screenHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
         canvas.drawCircle(getXCoord(), getYCoord(), getRadius(), paintTarget);
    }
}

