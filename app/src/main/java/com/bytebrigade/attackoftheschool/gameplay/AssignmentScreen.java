package com.bytebrigade.attackoftheschool.gameplay;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.*;
import com.bytebrigade.attackoftheschool.databinding.ActivityAssignmentScreenBinding;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.gameplay.assignment.animations.AssignmentAnimationListener;
import com.bytebrigade.attackoftheschool.gameplay.assignment.animations.CheatSheetAnimator;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;
import com.bytebrigade.attackoftheschool.helper.Helper;

import java.util.Random;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class AssignmentScreen extends AppCompatActivity implements Assignment.CallBack {

    private Assignment assignment = new Assignment(10L, AssignmentName.values()[0]);
    public ActivityAssignmentScreenBinding binding;
    private View menuLayout;
    private boolean isMenuOpen = false;
    private boolean isBossTimerRunning = false;
    private GestureDetector gestureDetector;
    private Handler handler;
    private Random random;
    private Runnable runnable;
    AssignmentAnimationListener animator;
    CheatSheetAnimator cheatSheetAnimator;
    private Helper helper = new Helper(SchoolType.ELEMENTARY, assignment);
    private SchoolType schoolType = SchoolType.ELEMENTARY;
    private Handler critSpotHandler = new Handler(Looper.getMainLooper());
    private Runnable critSpotRunnable;
    ObjectAnimator bossTimerAnimation;
    ObjectAnimator progressBarAnimator;


    int x5Tracker;
    int x2Tracker;
    int x10Tracker;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment_screen);
        assignment.setBackgroundSetter(this);
        binding.setAssignment(assignment);
        animator = new AssignmentAnimationListener(binding.clickableBlock);
        cheatSheetAnimator = new CheatSheetAnimator(binding.cheatSheet);
        menuLayout = binding.menuLayout;
        handler = new Handler(Looper.getMainLooper());
        random = new Random();

        critSpotRunnable = new Runnable() {

            @Override
            public void run() {
                if (binding.clickableBlock.getChildCount() > 0) {
                    binding.clickableBlock.removeAllViews();
                }

                addRandomPointToAssignment();

                critSpotHandler.postDelayed(this, 10000);
            }
        };

        gestureDetector = gestureDectorSetter();

        binding.clickableBlock.post(this::addRandomPointToAssignment);

        bossTimerAnimation = ObjectAnimator.ofInt(binding.bossTimer, "progress", 0, 1000);
        bossTimerAnimation.setDuration(30000);
        bossTimerAnimation.setInterpolator(new LinearInterpolator());


        setupAllButtons();
        startPowerUpGenerator();
        closeMenu();
        refreshStats();
        resetProgressBar();
        setButtonVisibility();
        checkStartBossTimer();
        startCritSpotRunnable();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            String intentData = getIntent().getExtras().getString("Uniqid");
            if (intentData.equals("From_Store")) {
                toggleMenu();

            }
        }
//
    }

    private void animateProgress(int end) {
        if (progressBarAnimator != null && progressBarAnimator.isRunning()) {
            progressBarAnimator.cancel();
        }
        int start = binding.progressBar.getProgress();
        if (start < end) {
            progressBarAnimator = ObjectAnimator.ofInt(binding.progressBar, "progress", binding.progressBar.getProgress(), end);
            progressBarAnimator.setDuration(250);
            progressBarAnimator.start();
        } else {
            binding.progressBar.setProgress(0);
        }
    }

    public void setupAllButtons() {
        binding.to1000.setOnClickListener(v -> {

            CurrentLevel = 1001;
            FurthestLevel = CurrentLevel;
            resetProgressBar();
            refreshStats();
            setButtonVisibility();
            changeClickableBackground();
            changeMainBackground();
        });
        binding.clickableBlock.setOnClickListener(v -> {
            assignment.incrementClick();
            double progress = (assignment.getCurrentClickAmount() / (double) assignment.getMaxClickAmount()) * 1000;
            animateProgress((int) progress);
            Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount() + " " + (int) progress);
            Log.i("CURRENTSTATS", "Health: " + (int) ((assignment.getCurrentClickAmount() / (double) assignment.getMaxClickAmount()) * 1000));
            changeMainBackground();
            setButtonVisibility();
        });

        binding.godMode.setOnClickListener(v -> clickStrength += 100000000);
        binding.plus49.setOnClickListener(v -> FurthestLevel += 49);
        binding.backtoDefaultButtons.setOnClickListener(v -> {

            binding.bottomButtonsLayout.setVisibility(View.VISIBLE);
            binding.libraryMenu.setVisibility(View.INVISIBLE);
        });
        menuLayout.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
        binding.menuButton.setOnTouchListener((v, event) -> {
            toggleMenu();
            return true;
        });
        binding.getRoot().setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
        binding.backtoMainMenu.setOnClickListener(this::onClick);


        binding.upgradeClick.setOnClickListener(v -> {
            if (points >= assignment.getUpgradePrice()) {
                points -= assignment.getUpgradePrice();
                amountOfClickIncreasedUpgrades++;
                clickStrength += ((long) Math.pow(1.1, (double) (amountOfClickIncreasedUpgrades) / 5)) + 3;
                Log.i("CURR", "curr: " + clickStrength + " added: " + Math.pow(1.1, (double) (amountOfClickIncreasedUpgrades) / 5) + " amtofupgrades: " + amountOfClickIncreasedUpgrades);
                refreshStats();
            }
        });
        binding.LibraryUpgrades.setOnClickListener(v -> {
            binding.bottomButtonsLayout.setVisibility(View.INVISIBLE);
            binding.libraryMenu.setVisibility(View.VISIBLE);

        });
        binding.cheatSheet.setOnClickListener(v -> {
            clickedPowerUp();
        });

        binding.helperButton.setOnClickListener(v -> {
            assignment.currentClickAmount = assignment.getMaxClickAmount();
            assignment.incrementClick();
            resetProgressBar();
            changeMainBackground();
            setButtonVisibility();
        });

        binding.menuButton.setOnClickListener(v -> toggleMenu());

        binding.prevStage.setOnClickListener(v -> {
            if (CurrentLevel > 1) {
                CurrentLevel--;
                resetProgressBar();
                changeMainBackground();
                setButtonVisibility();
            }
        });
        binding.prev10Stages.setOnClickListener(v -> {
            if (CurrentLevel > 9) {
                if (CurrentLevel - 10 < 0)
                    CurrentLevel = 1;
                else
                    CurrentLevel -= 10;
                resetProgressBar();
                changeMainBackground();
                setButtonVisibility();
            }
        });
        binding.nextStage.setOnClickListener(v -> {
            if (CurrentLevel < FurthestLevel) {
                CurrentLevel++;
                resetProgressBar();
                changeMainBackground();
                setButtonVisibility();
            }
        });
        binding.maxStage.setOnClickListener(v -> {
            if (CurrentLevel < FurthestLevel) {
                CurrentLevel = FurthestLevel;
                changeMainBackground();
                setButtonVisibility();
                resetProgressBar();
            }
        });

        binding.storeButton.setOnClickListener(this::onClick);


    }


    public void startCheatSheetCountDownTimer() {

        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(binding.cheatSheetTimer, "progress", 1000, 0);
        progressAnimator.setDuration(30000);
        clickStrengthMultiplier = 2;
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        refreshStats();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.cheatSheetTimer.setVisibility(View.INVISIBLE);
            clickStrengthMultiplier = 1;
            refreshStats();
        }, 30000);
    }

    public void checkStartBossTimer() {
        if (CurrentLevel % 5 == 0) {
            start30SecondBossTimer();
        } else stop30SecondBossTimer();
    }

    public void resetProgressBar() {
        binding.progressBar.setProgress(0);
        String crrntlvl = "Level " + CurrentLevel;
        binding.nameEditText.setText(crrntlvl);
        assignment.currentLevelChanged();
    }

    @Override
    public void showAddedPoints(String message) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.bottomToBottom = binding.clickableBlock.getId();
        params.startToStart = binding.clickableBlock.getId();
        textView.setLayoutParams(params);
        binding.mainRootAssignmentScreen.addView(textView);

        // Move up animation
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(textView, "translationY", textView.getY(), textView.getY() - 400);
        moveUp.setDuration(2000);

        // Fade out animation
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);

        ObjectAnimator moveSide = ObjectAnimator.ofFloat(textView, "translationX", textView.getX(), random.nextFloat() * 200 - 100);
        moveSide.setDuration(2000);

        // Combine the animations into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveUp, fadeOut, moveSide);

        // Remove the TextView after the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                binding.mainRootAssignmentScreen.removeView(textView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        // Start the animation
        animatorSet.start();
    }

    private GestureDetector gestureDectorSetter() {
        return new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffY = e2.getY() - e1.getY();
                Log.i("CURRENTSTAS", "ACTION distance: " + diffY + " Velocity: " + velocityY);
                if (Math.abs(diffY) > 250 && Math.abs(velocityY) > 100) {

                    if (diffY > 0 && isMenuOpen) {
                        // Swipe down
                        closeMenu();
                    } else if (diffY < 0 && !isMenuOpen) {
                        // Swipe up
                        openMenu();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void clickedPowerUp() {
        cheatSheetAnimator.stop();
        binding.cheatSheet.setVisibility(View.GONE);

        startCheatSheetCountDownTimer();
        binding.cheatSheetTimer.setVisibility(View.VISIBLE);
        refreshStats();


    }


    private void showPowerUp() {
        cheatSheetAnimator.start(1000, 10);
    }

    private void startPowerUpGenerator() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // 50% chance to show power-up
                if (random.nextBoolean() && binding.cheatSheet.getVisibility() == View.GONE) {
                    showPowerUp();
                }
                // Schedule the next run after 1 minute
                handler.postDelayed(this, 60000);//60000); // 60000 milliseconds = 1 minute
            }
        };
        handler.post(runnable);
    }

    public void setButtonVisibility() {

        if (CurrentLevel != 1) {
            binding.prevStage.setVisibility(View.VISIBLE);
            if (CurrentLevel > 9) binding.prev10Stages.setVisibility(View.VISIBLE);
            else if (binding.prev10Stages.getVisibility() == View.VISIBLE)
                binding.prev10Stages.setVisibility(View.INVISIBLE);
        } else if (binding.prevStage.getVisibility() == View.VISIBLE)
            binding.prevStage.setVisibility(View.INVISIBLE);
        if (CurrentLevel < FurthestLevel) {
            binding.nextStage.setVisibility(View.VISIBLE);
            binding.maxStage.setVisibility(View.VISIBLE);
        } else if (binding.nextStage.getVisibility() == View.VISIBLE) {
            binding.nextStage.setVisibility(View.INVISIBLE);
            binding.maxStage.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void start30SecondBossTimer() {

        if (!isBossTimerRunning) {
            binding.bossTimer.setVisibility(View.VISIBLE);
            bossTimerAnimation.start();
            isBossTimerRunning = true;
            handler.postDelayed(() -> {
                binding.cheatSheetTimer.setVisibility(View.INVISIBLE);
                if (isBossTimerRunning) bossTimerAnimation.start();
                resetProgressBar();
                assignment.currentLevelChanged();
            }, 30000);


        }
    }

    @Override
    public void stop30SecondBossTimer() {
        if (isBossTimerRunning) {
            bossTimerAnimation.cancel();
            binding.bossTimer.clearAnimation();
            binding.bossTimer.setVisibility(View.INVISIBLE);
            isBossTimerRunning = false;
        }
    }

    @Override
    public void changeMainBackground() {
        if (CurrentLevel <= 200) {
            binding.getRoot().setBackgroundResource(R.drawable.english_0);
            assignment.setClassName("English");
        } else if (CurrentLevel <= 400) {
            binding.getRoot().setBackgroundResource(R.drawable.math_0);
            assignment.setClassName("Math");
        } else if (CurrentLevel <= 600) {
            binding.getRoot().setBackgroundResource(R.drawable.pe_0);
            assignment.setClassName("P.E");
        } else if (CurrentLevel <= 800) {
            binding.getRoot().setBackgroundResource(R.drawable.science_0);
            assignment.setClassName("Science");
        } else if (CurrentLevel <= 999) {
            binding.getRoot().setBackgroundResource(R.drawable.history_0);
            assignment.setClassName("History");
        } else if (CurrentLevel == 1000) {
            binding.getRoot().setBackgroundResource(R.drawable.history_0);
            assignment.setClassName("Department Of Education");
        }
        binding.currentClassText.setText(assignment.getClassName());
    }

    private void refreshStats() {
        String[] stats = binding.statsDisplay.getText().toString().split("\n");
        stats[1] = "Points: " + points;
        stats[2] = "Time: " + "00:00:00";
        stats[3] = "Click Strength: " + clickStrength * clickStrengthMultiplier;
        StringBuilder updatedStats = new StringBuilder();
        for (String stat : stats) {
            updatedStats.append(stat).append("\n");
        }
        binding.statsDisplay.setText(updatedStats.toString().trim());
        String s = "Price: " + assignment.getUpgradePrice() + " points";
        binding.upgradeClick.setText(s);
    }

    @Override
    public void changeClickableBackground() {
        checkStartBossTimer();
        refreshStats();
        String crrntlvl = "Level " + CurrentLevel;
        binding.nameEditText.setText(crrntlvl);

        int completionPercentage = (int) Math.floor((assignment.getCurrentClickAmount() / (double) assignment.getMaxClickAmount()) * 10);
        // Adjust for special stages
        int imgID;


        if (CurrentLevel % 1001 == 0) {
            // Final boss
            imgID = R.drawable.doe;
            animator.start(1000, 2.5F);

        } else if (CurrentLevel % 200 == 0) {

            //EVERY 200 BOSS

            // Professor boss

            animator.start(150, 2F);
            imgID = switch (CurrentLevel) {
                //ENGLISH PROFESSOR BELOW
                case 200 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //MATH PROFESSOR BELOW
                case 400 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //PE PROFESSOR BELOW
                case 600 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //SCIENCE PROFESSOR BELOW
                case 800 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //HISTORY PROFESSOR BELOW
                case 1000 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };
                //DOE BOSS
                case 1001 -> switch (playthroughs.ordinal()) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                default -> R.drawable.englishproff_0;
            };

            //EVERY 200 BOSS ^^^^^^^^^^^

        } else if (CurrentLevel % 50 == 0) {
            // Test

            animator.start(250, 1.5F);
            imgID = switch (completionPercentage) {
                case 0 -> R.drawable.testtemp1;
                case 1 -> R.drawable.testtemp2;
                case 2 -> R.drawable.testtemp3;
                case 3 -> R.drawable.testtemp4;
                case 4 -> R.drawable.testtemp5;
                case 5 -> R.drawable.testtemp6;
                case 6 -> R.drawable.testtemp7;
                case 7 -> R.drawable.testtemp8;
                case 8 -> R.drawable.testtemp9;
                case 9 -> R.drawable.testtemp10;
                default -> R.drawable.testtemp1;
            };

            //test's picture animation


        } else if (CurrentLevel % 5 == 0) {
            // quiz/packet
            animator.start(350, 1.5F);
            imgID = switch (completionPercentage) {
                case 0 -> R.drawable.quiztemp1;
                case 1 -> R.drawable.quiztemp2;
                case 2 -> R.drawable.quiztemp3;
                case 3 -> R.drawable.quiztemp4;
                case 4 -> R.drawable.quiztemp5;
                case 5 -> R.drawable.quiztemp6;
                case 6 -> R.drawable.quiztemp7;
                case 7 -> R.drawable.quiztemp8;
                case 8 -> R.drawable.quiztemp9;
                case 9 -> R.drawable.quiztemp10;
                default -> R.drawable.quiztemp1;
            };


        } else {
            animator.stop();
            imgID = switch (completionPercentage) {
                case 0 -> R.drawable.assignmenttemp1;
                case 1 -> R.drawable.assignmenttemp2;
                case 2 -> R.drawable.assignmenttemp3;
                case 3 -> R.drawable.assignmenttemp4;
                case 4 -> R.drawable.assignmenttemp5;
                case 5 -> R.drawable.assignmenttemp6;
                case 6 -> R.drawable.assignmenttemp7;
                case 7 -> R.drawable.assignmenttemp8;
                case 8 -> R.drawable.assignmenttemp9;
                case 9 -> R.drawable.assignmenttemp10;
                default -> R.drawable.assignmenttemp1;
            };
        }
        binding.clickableBlock.setBackground(ContextCompat.getDrawable(binding.clickableBlock.getContext(), imgID));
    }

    private void toggleMenu() {
        if (isMenuOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    private void openMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        menuLayout.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(300)
                .withEndAction(() -> isMenuOpen = true);
    }

    private void closeMenu() {

        menuLayout.animate()
                .translationY(menuLayout.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .withEndAction(() -> {
                    menuLayout.setVisibility(View.GONE);
                    isMenuOpen = false;
                    binding.bottomButtonsLayout.setVisibility(View.VISIBLE);
                    binding.libraryMenu.setVisibility(View.INVISIBLE);
                });
    }

    @Override
    public void sendToCredits() {
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
        finish();
    }

    private void addRandomPointToAssignment() {
        if (binding.clickableBlock == null) {
            Log.e("AssignmentScreen", "clickableBlock is null");
            return;
        }
        int blockWidth = binding.clickableBlock.getWidth();
        int blockHeight = binding.clickableBlock.getHeight();

        // Check if blockWidth or blockHeight is zero, which means the layout might not be ready yet
        if (blockWidth <= 0 || blockHeight <= 0) {
            Log.e("AssignmentScreen", "clickableBlock dimensions are not ready or invalid!");
            return; // Avoid crashing by returning early
        }

        // Generate random coordinates within the bounds of clickableBlock
        int randomX = random.nextInt(blockWidth);
        int randomY = random.nextInt(blockHeight);

        // Create a new View to represent the random point (crit spot)
        View pointView = new View(this);
        pointView.setBackground(ContextCompat.getDrawable(this, R.drawable.star_shape)); // Set the color of the crit spot
        int pointSize = 50; // Set the size of the crit spot (20x20 pixels)

        // Set the layout parameters for the crit spot
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(pointSize, pointSize);
        params.leftToLeft = binding.clickableBlock.getId();
        params.topToTop = binding.clickableBlock.getId();
        params.setMargins(randomX, randomY, 0, 0);

        pointView.setLayoutParams(params);

        // Add the crit spot to the clickableBlock
        binding.clickableBlock.addView(pointView);

        // Set a click listener on the crit spot
        pointView.setOnClickListener(v -> {
            int additionalClicks = (int) (assignment.getMaxClickAmount() * 0.10); // Set the number of additional clicks awarded

            // Award the additional clicks to the player
            assignment.incrementClickBy(additionalClicks);
            double progress = (assignment.getCurrentClickAmount() / (double) assignment.getMaxClickAmount()) * 1000;
            animateProgress((int) progress);

            // Remove the crit spot after it's clicked
            binding.clickableBlock.removeView(pointView);

            // Optionally, show feedback to the player
            showAddedPoints("Critical Hit! +" + additionalClicks + " clicks!");
        });
    }

    private void startCritSpotRunnable() {
        // Start immediately, then every 30 seconds
        critSpotHandler.post(critSpotRunnable);
    }

    private void stopCritSpotRunnable() {
        // Stop the Runnable from running
        critSpotHandler.removeCallbacks(critSpotRunnable);
    }

    private void onClick(View v) {
        Bundle x5B = getIntent().getExtras();
        Intent intent = new Intent();
        if(v == binding.storeButton)
        {
            intent.setClass(AssignmentScreen.this, StoreFunctionality.class);
        }
        else if (v == binding.backtoMainMenu)
        {
            intent.setClass(AssignmentScreen.this, MainMenu.class);


        }



        if (x5B != null) {
            intent.putExtra("Uniqid", "From_Activity_B");
            String x5changetext = x5ButtonText;
            String x2changetext = x2ButtonText;
            String x10changetext =x10ButtonText;
            intent.putExtra("x5B", x5changetext);
            intent.putExtra("x2", x2changetext);
            intent.putExtra("x10", x10changetext);
            x5Tracker = x5B.getInt("x5Tracker");
            x2Tracker = x5B.getInt("x2Tracker");
            x10Tracker = x5B.getInt("x10Tracker");
            intent.putExtra("x5Tracker", x5Tracker);
            intent.putExtra("x2Tracker", x2Tracker);
            intent.putExtra("x10Tracker", x10Tracker);

        }
        x5B = getIntent().getExtras();

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}