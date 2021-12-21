package com.example.assignmentthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Homepage of the application.
 * Contains informative text and a "Start Play" button
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Button click event for the startPlay button.
     * Opens up the GameActivity page
     * @param v view
     */
    public void startPlay(View v) {

        //Creates an intent related to the detail activity class
        Intent intent = new Intent(this, GameActivity.class);

        //Starts the detail activity class
        startActivity(intent);

    }
}