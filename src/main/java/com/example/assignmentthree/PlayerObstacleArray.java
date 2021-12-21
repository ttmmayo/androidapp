package com.example.assignmentthree;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Stores a list of player obstacles in an array that can be drawn
 */
public class PlayerObstacleArray {

    //Int value containing the length of the array
    private int arrayLength;

    /**
     * Public get property for the array length
     * @return the arrayLength
     */
    public int getArrayLength() {
        return arrayLength;
    }

    private PlayerObstacle playerObstacleArray[];

    public PlayerObstacleArray(int arrayLength, int screenWidth, int screenHeight) {
        //Sets the array length to the one specified by the constructor
        this.arrayLength = arrayLength;
        //Initializes the array based on this instance
        playerObstacleArray = new PlayerObstacle[this.arrayLength];
        populateObstacleList(screenWidth, screenHeight);
    }

    /**
     * Populates a given array with player obstacles
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    private void populateObstacleList(int screenWidth, int screenHeight) {
        playerObstacleArray = new PlayerObstacle[arrayLength];
        //Creates new obstacles for the length of the array list
        for (int i = 0; i < playerObstacleArray.length; i++) {
            //Creates a new instance of the player obstacle to add
            playerObstacleArray[i] = new PlayerObstacle(screenWidth, screenHeight);
        }
    }

    /**
     * Draws each of the player obstacles in the array
     * @param canvas the surface to draw on
     */
    public void onDraw(Canvas canvas) {
        for (PlayerObstacle p: playerObstacleArray) {
            p.onDraw(canvas);
        }
    }

    /**
     * Goes through the array of player obstacles and check if it has collided with a playerball
     * @param playerBall the ball to check if it has collided with
     * @return whether it has collided with an obstacle in the array
     */
    public boolean checkCollisionsArray(PlayerBall playerBall) {
        //Loop through the list and check each one for a collision with the playerball
        for (PlayerObstacle playerObstacle : playerObstacleArray) {
            if (playerBall.collisionCheck(playerObstacle)) {
                return true;
            }
        }
        return false;
    }

}

