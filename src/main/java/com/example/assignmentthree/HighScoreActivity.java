package com.example.assignmentthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Displays the five highest scores achieved in the game.
 * Includes a return to main menu button.
 */
public class HighScoreActivity extends AppCompatActivity {

    //High Score List
    public HighScoreList highScoreList = HighScoreList.getInstance();
    //Points from the previous game
    protected int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //Displays the high scores
        displayHighScores(highScoreList);
    }

    /**
     * Displays the highest scores of the current game session
     * @param highScoreList the list of the highest scores
     */
    public void displayHighScores(HighScoreList highScoreList) {
        //Displays it in a list
        ListView highScoreListView = (ListView)findViewById(R.id.highScoreListView);
        //Displays the highScoreList in a global scope ListView
        highScoreList.displayScores(getApplicationContext(), highScoreListView);
    }

    /**
     * Stops the current window and returns to the main menu
     * @param v view
     */
    public void mainMenuButton(View v) {
        //Destroys this window
        this.onStop();
        //Creates an intent related to the main activity class
        Intent intent = new Intent(this, MainActivity.class);
        //Starts the high score activity class
        startActivity(intent);
    }
}