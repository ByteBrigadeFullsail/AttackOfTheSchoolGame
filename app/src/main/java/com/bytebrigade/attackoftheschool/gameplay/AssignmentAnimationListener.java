package com.bytebrigade.attackoftheschool.gameplay;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.Random;

public class AssignmentAnimationListener{
    public boolean isMoving = false;
    private Random random = new Random();
    private long duration;
    private int translationCount = 0;

    private View animatedObject;

    public AssignmentAnimationListener(View v) {
        duration = 500;
        animatedObject = v;
    }

    private void setRandomValues() {
        float fromXDelta = animatedObject.getTranslationX();
        float fromYDelta = animatedObject.getTranslationY();
        float toXDelta = random.nextFloat() * animatedObject.getWidth() ;
        float toYDelta = random.nextFloat() * animatedObject.getHeight();

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(animatedObject, "translationX", fromXDelta, toXDelta);
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(animatedObject, "translationY", fromYDelta, toYDelta);
        translateXAnimator.setDuration(duration);
        translateYAnimator.setDuration(duration);

        translateXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isMoving) {
                    translationCount++;
                    setRandomValues();
                } else if (translationCount != 0) {
                    returnToStart();

                } else {
                    animatedObject.clearAnimation();
                }
            }
        });

        translateXAnimator.start();
        translateYAnimator.start();
    }

    private void returnToStart() {
        // Create and start the reverse animation
        float toXDelta = animatedObject.getTranslationX();
        float toYDelta = animatedObject.getTranslationY();
        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(animatedObject, "translationX", toXDelta, 0);
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(animatedObject, "translationY", toYDelta, 0);
        translateXAnimator.setDuration(duration);
        translateYAnimator.setDuration(duration);

        // Add listeners to handle animation end events
        translateXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                translationCount = 0;
            }
        });

        translateXAnimator.start();
        translateYAnimator.start();
    }

    public void stop() {
        isMoving = false;
    }

    public void start(int duration) {
        if (!isMoving) {
            this.duration = duration;
            setRandomValues();
            translationCount = 1;
            isMoving = true;
        }
    }

    public void toggle() {
        if (!isMoving) start((int) duration);
        else stop();
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
