package com.example.a4_clcyau.shapes;

public class Circle {

    private int cx, cy, radius;

    public Circle(int left, int top, int radius){
        cx = left + radius/2;
        cy = top + radius/2;
        this.radius = radius;
    }

//    public Circle(int cx, int cy, int radius){
//        this.cx = cx;
//        this.cy = cy;
//        this.radius = radius;
//    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public int getRadius() {
        return radius;
    }

    public int getTop(){
        return cy  - getRadius();
    }

    public int getBottom(){
        return cy + getRadius();
    }
}
