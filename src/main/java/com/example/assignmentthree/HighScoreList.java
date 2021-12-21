package com.example.assignmentthree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Singleton class containing an instance of the highScoreList
 * highScoreList is an array that holds the five highest scores
 */
public class HighScoreList {

    //Stores the private single instance
    private static HighScoreList highScoreList = new HighScoreList();
    Integer highScoreArray[];

    //Private constructor
    private HighScoreList() {
        //Initializes the high score array
        highScoreArray = new Integer[]{0, 0, 0, 0, 0};
    }

    /**
     * Method that gets an instance of the high score list
     * @return highScoreList instance
     */
    public static HighScoreList getInstance() {
        return highScoreList;
    }

    /**
     * Adds a score to the highScoreArray if it is one in the top 5
     * @param score the total points of the last game
     */
    public void addScore(int score) {
        //Loop through the high score array
        for (int i = 0; i <highScoreArray.length;i++) {

            //If the score is greater than the current point in the array
            if (score > highScoreArray[i]) {
                //Temporary score
                int tempScore = highScoreArray[i];
                //Place the score there
                highScoreArray[i] = score;
                //Go through and add the temp score
                addScore(tempScore);
                return;
            }
        }
    }

    /**
     * Uses an ArrayAdapter to display high scores
     * @param context
     * @param listView the view scores are displayed in
     */
    public void displayScores(Context context, ListView listView) {
         //Create an array adapter
        ArrayAdapter<Integer> highScoreArrayAdapter =
                new ArrayAdapter<Integer>(context, android.R.layout.simple_list_item_1, highScoreArray);
        //Sets this in the list view
        listView.setAdapter(highScoreArrayAdapter);
    }
}

