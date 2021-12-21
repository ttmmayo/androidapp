package com.example.assignmentthree;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

/**
 * Abstract class for a game object circle that has a radius and (x, y) coordinates
 * The game object is also drawn on a canvas
 */
public abstract class GameObject {

    private int _radius = 75;
    private int _xCoord = 0;
    private int _yCoord = 0;
    protected Random rand;

    public int getRadius() {
        return _radius;
    }

    public void setXCoord(int value) {
        _xCoord = value;
    }

    public int getXCoord() {
        return _xCoord;
    }

    public void setYCoord(int value) {
        _yCoord = value;
    }

    public int getYCoord() {
        return _yCoord;
    }

    public abstract void onDraw(Canvas canvas);

}

