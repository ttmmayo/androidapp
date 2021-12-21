package com.example.assignmentthree;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Implements a PlayerBall which is rolled in response to the accelerometer
 * Can collide with obstacles, targets and the sides of the screen
 */
public class PlayerBall extends GameObject {

    public static Paint paintPlayer = new Paint();

    /**
     * Creates an instance of the PlayerBall class at the top of the screen halfway
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public PlayerBall(int screenWidth, int screenHeight) {
        setXCoord(screenWidth / 2);
        setYCoord(getRadius() * 2);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(getXCoord(), getYCoord(), getRadius(), paintPlayer);
    }

    /**
     * Moves the ball in response to x and y value changes
     * @param x the value of the accelerometer in the x direction
     * @param y the value of the accelerometer in the y direction
     */
    public void move(int x, int y) {
        setXCoord(getXCoord() - x * 2);
        setYCoord(getYCoord() + y * 2);
    }

    /**
     * Checks if it has collided with another game object
     * @param object the object to check if it has collided with
     * @return if it has collided with another gameObject
     */
    public boolean collisionCheck(GameObject object) {
        float xDistance = object.getXCoord() - getXCoord();
        float yDistance = object.getYCoord() - getYCoord();
        //Calculates the distance between them
        float distanceTotal = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        //If this is less than the combined radii,
        if (distanceTotal <= object.getRadius() + getRadius()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if it has collided with the wall
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @return whether it collided with the sides of the screen
     */
    public boolean collisionCheckWall(int screenWidth, int screenHeight) {
        //Checks if it collided with the horizontal sides
        if (getXCoord() <= 0 + getRadius() || getXCoord() >= screenWidth - getRadius()) {
            return true;
        }
        //Checks if it collides with the vertical sides
        if (getYCoord() <= 0 + getRadius() || getYCoord() >= screenHeight + getRadius() * 2 / 3) {
            return true;
        }
        return false;
    }
}

