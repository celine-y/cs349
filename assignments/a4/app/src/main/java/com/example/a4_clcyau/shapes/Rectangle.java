package com.example.a4_clcyau.shapes;

import android.graphics.Rect;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Rectangle {
    private int x, y, width, height;

    public Rectangle(int left, int top, int width, int height){
        this.x = left;
        this.y = top;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getLeft() {
        return x;
    }

    public int getTop() {
        return y;
    }

    public int getBottom() {
        return y + height;
    }

    public int getRight() {
        return x + width;
    }

    public void moveX(int deltaX) {
        x += deltaX;
    }

    public void moveY(int deltaY) {
        y += deltaY;
    }

    public Rect getRect(){
        return new Rect(getLeft(), getTop(), getRight(), getBottom());
    }
}
