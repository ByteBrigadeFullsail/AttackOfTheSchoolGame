package com.bytebrigade.attackoftheschool.gameplay;


import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.Random;

public class AssignmentAnimationListener extends TranslateAnimation implements Animation.AnimationListener {
    public boolean isMoving = false;
    private Random random = new Random();
    private long duration;
    private int translationCount = 0;

    float fromXDelta;
    float toXDelta;
    float fromYDelta;
    float toYDelta;

    private View animatedObject;

    public AssignmentAnimationListener(View v) {
        super(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        setDuration(0);
        duration = 500;
        animatedObject = v;
        setAnimationListener(this);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (isMoving) {
            translationCount++;
            if (translationCount % 2 == 0) {
                //Log.i("ASD", animation.getRepeatMode() + " STARTING REVERSE " + translationCount);
                TranslateAnimation newAnimation = createReverseTranlsation();
                newAnimation.setAnimationListener(this);
                animatedObject.startAnimation(newAnimation);
            } else {
                //Log.i("ASD", animation.getRepeatMode() + " STARTING FORWARD " + translationCount);
                TranslateAnimation newAnimation = createRandomTranlsation();
                newAnimation.setAnimationListener(this);
                animatedObject.startAnimation(newAnimation);
            }
        } else if (translationCount != 0) {
            if (translationCount % 2 != 0) {
                //Log.i("ASD", animation.getRepeatMode() + " STARTING RETURN HOME " + translationCount);
                TranslateAnimation newAnimation = createReverseTranlsation();
                newAnimation.setAnimationListener(this);
                translationCount = 0;
                animatedObject.startAnimation(newAnimation);
            }
        }else{
            animatedObject.clearAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void setRandomValues() {
        //fromXDelta = random.nextFloat() * 0.5f;
        toXDelta = random.nextFloat() * 1.0f - 0.5f;
        //fromYDelta = random.nextFloat() * 0.5f;
        toYDelta = random.nextFloat() * 1.0f - 0.5f;
    }

    private TranslateAnimation createRandomTranlsation() {
        setRandomValues();
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromXDelta,
                Animation.RELATIVE_TO_PARENT, toXDelta,
                Animation.RELATIVE_TO_PARENT, fromYDelta,
                Animation.RELATIVE_TO_PARENT, toYDelta
        );
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(this);
        translateAnimation.setDuration(duration);
        return translateAnimation;


    }

    private TranslateAnimation createReverseTranlsation() {
        TranslateAnimation reverseAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, toXDelta,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, toYDelta,
                Animation.RELATIVE_TO_PARENT, 0
        );
        reverseAnimation.setAnimationListener(this);
        reverseAnimation.setRepeatCount(0);
        reverseAnimation.setFillAfter(true);
        reverseAnimation.setDuration(duration);
        return reverseAnimation;


    }

    public void stop() {
        isMoving = false;
    }

    public void start(int duration) {
        if (!isMoving) {
            TranslateAnimation newAnimation = createRandomTranlsation();
            newAnimation.setAnimationListener(this);
            this.duration = duration;
            animatedObject.startAnimation(newAnimation);
            translationCount = 1;
            //Log.i("ASD", " STARTING FROM START " + translationCount);
            isMoving = true;
        }
    }

    public void toggle() {
        if (!isMoving) start();
        else stop();
    }

    @Override
    public void setDuration(long duration) {
        super.setDuration(duration);
        this.duration = duration;
    }
}
