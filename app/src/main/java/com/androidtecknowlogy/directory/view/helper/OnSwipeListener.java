package com.androidtecknowlogy.directory.view.helper;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nezspencer on 12/8/16.
 */

public class OnSwipeListener implements View.OnTouchListener {

    private static final int SWIPE_THRESHOLD=150;

    public OnSwipeListener(Context context) {
        super();
        //gestureDetector = new GestureDetector(context,new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        float x1=0;
        float x2=0;
        float y1=0;
        float y2=0;
        //gestureDetector.onTouchEvent(motionEvent);
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1=motionEvent.getX();
                y1=motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=motionEvent.getX();
                y2=motionEvent.getY();
                break;
        }
        float diffX=x2-x1;
        float diffY=y2-y1;
        if (Math.abs(diffX)>Math.abs(diffY)&&Math.abs(diffX)>SWIPE_THRESHOLD)
        {
            Log.e("0ne"," swipe!");
            if (diffX>0)
            {
                onSwipeRight();
                Log.e("two"," right swipe");
            }

            else
            {
                Log.e("three"," left swipe");
                onSwipeLeft();
            }

            return true;
        }

        return false;
    }

    /**called when user's fingers move from left to right edge of the screen
     * In a viewpager, it should open pages to left of the screen*/
    public void onSwipeRight(){}

    /**called when user's fingers move from right to left edge of the screen
     * In a viewpager, it should open pages to right of the screen*/
    public void onSwipeLeft(){}

}
