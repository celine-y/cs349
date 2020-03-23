package com.example.a4_clcyau;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.a4_clcyau.shapes.Rectangle;

import java.util.ArrayList;

public class DummyView extends View {
    private Paint paint;
    private final int space = 5;

    private int wDisplay;

    private boolean canMove;
    int prevX;
    int prevY;

    private ArrayList<Rectangle> rectangles;

    public DummyView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setCanvas();
    }

    public DummyView(Context context) {
        super(context);
        setCanvas();
    }

    private void setCanvas(){
        paint = new Paint();
    }

    public void setDummy(){
        rectangles = new ArrayList<>();
        setDimensions();
        setHead();
        setBody();
        setLegs();
        setArms();
        setFeet();

        invalidate();
    }

    private void setHead(){
        int width = 100;
        int height = 100;
        int top = 80;
        int left = wDisplay/2 - width/2;

        Rectangle head = new Rectangle(left, top, width, height);

        rectangles.add(head);
    }

    private void setBody(){
        int bWidth = 300;
        int bHeight = 400;
        int top = rectangles.get(0).getBottom();
        int left = wDisplay/2 - bWidth/2;

        Rectangle body = new Rectangle(left, top, bWidth, bHeight);
        rectangles.add(body);
    }

    private void setLegs(){
        Rectangle body = rectangles.get(1);
        int left = body.getLeft();
        int width = body.getWidth()/2 - space;
        int top = body.getBottom();
        int height = 200;

//        Left Leg
        Rectangle upLegLeft = new Rectangle(left, top, width, height);
        Rectangle lowLegLeft = new Rectangle(left, upLegLeft.getBottom(), width, height);

//        Right Leg
        left = body.getRight() - width;
        Rectangle upLegRight = new Rectangle(left, top, width, height);
        Rectangle lowLegRight = new Rectangle(left, upLegRight.getBottom(), width, height);

        rectangles.add(upLegLeft);
        rectangles.add(lowLegLeft);
        rectangles.add(upLegRight);
        rectangles.add(lowLegRight);
    }

    private void setArms(){
        Rectangle body = rectangles.get(1);
        int width = 100;
        int left = body.getLeft() - width;
        int top = body.getTop();
        int height = 200;

//        Left Arm
        Rectangle upArmLeft = new Rectangle(left, top, width, height);
        Rectangle lowArmLeft = new Rectangle(left, upArmLeft.getBottom(), width, height);

//        Right Arm
        left = body.getRight();
        Rectangle upArmRight = new Rectangle(left, top, width, height);
        Rectangle lowArmRight = new Rectangle(left, upArmRight.getBottom(), width, height);

        rectangles.add(upArmLeft);
        rectangles.add(lowArmLeft);
        rectangles.add(upArmRight);
        rectangles.add(lowArmRight);
    }

    private void setFeet(){
        Rectangle lowLegLeft = rectangles.get(3);
        Rectangle lowLegRight = rectangles.get(5);
        int width = 200;
        int height = 100;

        int lLeft = lowLegLeft.getRight() - width;
        int tLeft = lowLegLeft.getBottom();

        rectangles.add(new Rectangle(lLeft, tLeft, width, height));

        int lRight = lowLegRight.getLeft();
        int tRight = lowLegRight.getBottom();

        rectangles.add(new Rectangle(lRight, tRight, width, height));
    }

    private void setDimensions(){
        wDisplay = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rectangles == null){
            setDummy();
        }

        canvas.drawColor(Color.WHITE);

        for (int i = 0; i < rectangles.size(); i++){
            if (i == 0){
                paint.setColor(Color.YELLOW);
            } else if (i==1) {
                paint.setColor(Color.GREEN);
            } else if (i <= 5){
                paint.setColor(Color.BLUE);
            } else {
                paint.setColor(Color.GRAY);
            }
            Rect rect = rectangles.get(i).getRect();
            canvas.drawRect(rect, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int positionX = (int)event.getRawX();
        int positionY = (int)event.getRawY();

        Rect body = rectangles.get(1).getRect();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {

                if(body.contains(positionX, positionY))
                {
                    prevX = positionX;
                    prevY = positionY;
                    canMove = true;
                }
            }
            break;

            case MotionEvent.ACTION_MOVE:
            {
                if(canMove) {
                    int deltaX = positionX - prevX;
                    int deltaY = positionY - prevY;

                    for (Rectangle rectangle : rectangles) {
                        rectangle.moveX(deltaX);
                        rectangle.moveY(deltaY);
                    }
                    prevX = positionX;
                    prevY = positionY;

                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_UP:
                canMove = false;
                break;
        }
        return true;

    }
}
