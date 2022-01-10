package com.camp.project2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private boolean enabled;
    public MyViewPager(Context context){
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (enabled) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
            // ignore move action
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev);
                }
            }
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (enabled) {
            return super.onTouchEvent(ev);
        } else {
            return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
        }
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
