package com.bytebrigade.attackoftheschool.gameplay.assignment.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class CheatSheetAnimator {
    public boolean isMoving = false;
    private Random random = new Random();
    private long duration = 1000;
    private int translationCount = 10;

    private View animatedObject;

    public CheatSheetAnimator(View v) {
        animatedObject = v;
    }

    private void setRandomValues() {
        int rootViewWidth = animatedObject.getRootView().getWidth();
        int animatedObjectWidth = animatedObject.getWidth();
        int bound = rootViewWidth - animatedObjectWidth;

        if (bound > 0) {
            int randomX = random.nextInt(bound);
            animatedObject.setTranslationX(randomX);
        } else {
            animatedObject.setTranslationX(0); // Default to 0 if bound is not positive
        }

        float fromYDelta = 0;
        float toYDelta = animatedObject.getRootView().getHeight() + animatedObject.getHeight();

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(animatedObject, "translationY", fromYDelta, toYDelta);
        translateYAnimator.setDuration(duration);

        translateYAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isMoving && translationCount > 0) {
                    translationCount--;
                    setRandomValues();
                    //Log.i("ASD", "ANIMATIONENDED  " + animatedObject.getTranslationX() + "   " + animatedObject.getRootView().getWidth());
                } else {
                    animatedObject.clearAnimation();
                    animatedObject.setVisibility(View.GONE);
                    isMoving = false;
                }
            }
        });

        translateYAnimator.start();
    }

    public void stop() {
        isMoving = false;
        animatedObject.setVisibility(View.GONE);
        animatedObject.clearAnimation();

    }

    public void start(long duration, int translationS) {
        if (!isMoving) {
            this.duration = duration;
            translationCount = translationS;
            animatedObject.setVisibility(View.VISIBLE);
            isMoving = true;
            setRandomValues();
        }
    }


    public void toggle() {
        if (!isMoving) start(duration, 15);
        else stop();
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }
}
