package com.example.assignmentthree;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The obstacle that a player can be destroyed by
 */
public class PlayerObstacle extends GameObject {

    public static Paint paintObstacle = new Paint();

    /**
     * Generates a random location for the PlayerObstacle in the middle of the screen
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public PlayerObstacle(int screenWidth, int screenHeight) {

        rand = new Random();

        //Generates a random number between the screens, accounting for the radius
        setXCoord(rand.nextInt(screenWidth - getRadius() * 2) + getRadius());
        setYCoord(rand.nextInt(screenHeight - getRadius() * 6) + getRadius() * 4);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(getXCoord(), getYCoord(), getRadius(), paintObstacle);
    }
}

