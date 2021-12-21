package com.example.assignmentthree;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Rolls a ball downwards on the screen towards a target.
 * Increases the number of obstacles if the target is reached.
 * Contains obstacles that end the game if they are hit.
 */
public class GameActivity extends AppCompatActivity {

    //Gets the display height and width
    public int screenWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
    public int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    //Determines whether the game has ended or not
    private boolean gameOver = false;
    //Number of points earned
    private int points = 0;

    //Creates an instance of a playerball in the same location
    protected PlayerBall playerBall = new PlayerBall(screenWidth, screenHeight);
    //Creates an instance of the player target
    protected PlayerTarget playerTarget = new PlayerTarget(screenWidth, screenHeight);
    //Creates an instance of the PlayerObstacleArray class that holds an array of obstacles
    protected PlayerObstacleArray playerObstacleArray;// =
          //  new PlayerObstacleArray(1, screenWidth, screenHeight);

    //Sensors required for the accelerometer
    protected SensorManager sensorManager;
    protected Sensor accelerometerSensor;

    //The action bar
    protected ActionBar actionBar;

    //Views in the XML
    protected TextView textViewPoints;
    protected ConstraintLayout constraintLayoutMain;
    protected GraphicsView graphicsView;
    protected LinearLayout linearLayoutGameOver;
    protected TextView textViewFinalScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        playerObstacleArray = new PlayerObstacleArray(1, screenWidth, screenHeight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Creates a new instance of the GraphicsView
        graphicsView = new GraphicsView(this);
        //Adds this to the constraint layout
        constraintLayoutMain = (ConstraintLayout)findViewById(R.id.cl_main);
        constraintLayoutMain.addView(graphicsView);

        //Finds the textview
        textViewPoints = (TextView)findViewById(R.id.pointTextView);

        //Hides the actionbar
        actionBar = getSupportActionBar();
        actionBar.hide();

        //Enables sticky immersive mode
        int uiOptions = View. SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View. SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        //Creates the sensor
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Resumes the accelerometer sensor
        sensorManager.registerListener(accelerometerListener, accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Pauses the accelerometer sensor
        sensorManager.unregisterListener(accelerometerListener, accelerometerSensor);
    }

    /**
     * A GraphicsView class that sets paint colours
     * Draws a target, player ball and obstacles on a canvas
     */
    public class GraphicsView extends View {

        //Constructor
        public GraphicsView(Context context) {
            super(context);
            //Sets the color of the game objects
            PlayerObstacle.paintObstacle.setColor(getColor(R.color.colorObstacle));
            PlayerTarget.paintTarget.setColor(getColor(R.color.colorTarget));
            PlayerBall.paintPlayer.setColor(getColor(R.color.colorPlayer));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //Draws the player target on the view
            playerTarget.onDraw(canvas);
            //Draws each obstacle in the array
            playerObstacleArray.onDraw(canvas);
            //Draws the player ball on the view
            playerBall.onDraw(canvas);

            //Stop redrawing if the game ends
            if (gameOver) {
                return; }

            //Redraws itself over and over
            invalidate();
        }
    }

    //Sets up a new sensor for the accelerometer
    SensorEventListener accelerometerListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            //Moves the playerball in response to the accelerometer's X and Y values
            playerBall.move((int)event.values[0],(int)event.values[1]);
            //Checks if the player has collided with the target, and if so increases the difficulty
            if (playerBall.collisionCheck(playerTarget)) {
                levelUp(); }

             if (playerObstacleArray.checkCollisionsArray(playerBall)) {
                 gameOver();
             }

            //Checks if it has collided with the wall
            if (playerBall.collisionCheckWall(screenWidth, screenHeight)) {
                gameOver();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    /**
     * Shows a game over screen when the user fails
     * Includes a button to see the high score screen and to return to the main menu
     */
    public void gameOver() {
        //Colours the background to red
        constraintLayoutMain.setBackgroundColor(getColor(R.color.colorHit));
        //Unregister the listener so that it doesn't move anymore
        sensorManager.unregisterListener(accelerometerListener, accelerometerSensor);
        //Set game over to true so that it stops drawing
        gameOver = true;

        //Make the game over screen visible and place on top of game
        linearLayoutGameOver = (LinearLayout)findViewById(R.id.ll_game_activity);
        linearLayoutGameOver.setVisibility(View.VISIBLE);
        linearLayoutGameOver.bringToFront();

        //Sets the points on the specific text view
        textViewFinalScore = (TextView)findViewById(R.id.finalScoreTextView);
        textViewFinalScore.setText(Integer.toString(points));


        //Removes the point text in the corner
        textViewPoints = (TextView)findViewById(R.id.pointTextView);
        textViewPoints.setVisibility(View.INVISIBLE);

        //Get the HighScoreList and add the current score to it
        HighScoreList highScoreList = HighScoreList.getInstance();
        highScoreList.addScore(points);
    }

    /**
     * Increases the number of obstacles by 1 and the number of points
     * Initializes a new ball and target
     */
    public void levelUp() {
        playerObstacleArray = new PlayerObstacleArray(
                playerObstacleArray.getArrayLength() + 1, screenWidth, screenHeight);
        //Initializes a new target and ball
        playerBall = new PlayerBall(screenWidth, screenHeight);
        playerTarget = new PlayerTarget(screenWidth, screenHeight);
        //Add a point
        points++;
        //Set the text to the points given
        textViewPoints.setText(Integer.toString(points));
    }

    /**
     * Opens up the high score activity
     * @param v view
     */
    public void seeHighScoresButton(View v) {

        //Creates an intent related to the high score activity class
        Intent intent = new Intent(this, HighScoreActivity.class);
        //Starts the high score activity class
        startActivity(intent);
    }

    /**
     * Returns to the main menu activity
     * @param v view
     */
    public void returnToMainMenuButton(View v) {
        //Destroys this window
        this.onStop();
        //Creates an intent related to the main activity class
        Intent intent = new Intent(this, MainActivity.class);
        //Starts the high score activity class
        startActivity(intent);
    }
}