package com.bytebrigade.attackoftheschool.gameplay;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.MainActivity;
import com.bytebrigade.attackoftheschool.R;
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
    private CountDownTimer countDownTimer;
    private CountDownTimer cheatSheetCountDownTimer;
    private Runnable runnable;
    AssignmentAnimationListener animator;
    CheatSheetAnimator cheatSheetAnimator;
    CheatSheetAnimator cheetSheetAnimator;
    private Helper helper = new Helper(SchoolType.ELEMENTARY, assignment);
    private SchoolType schoolType = SchoolType.ELEMENTARY;

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
        handler = new Handler();
        random = new Random();

        binding.clickableBlock.setOnClickListener(v -> {
            assignment.incrementClick();
            binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
            binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
            Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
            changeMainBackground();
            setButtonVisibility();
        });

        cheetSheetAnimator = new CheatSheetAnimator(binding.cheatSheet);
        //binding.godMode.setOnClickListener(v -> assignment.clickStrength += 1000000);
        //binding.plus49.setOnClickListener(v -> FurthestLevel += 49);

        gestureDetector = gestureDectorSetter();

        binding.godMode.setOnClickListener(v -> clickStrength += 1000000);
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
        binding.backtoMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AssignmentScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        binding.upgradeClick.setOnClickListener(v -> {
            if (points >= assignment.getUpgradePrice()) {
                points -= assignment.getUpgradePrice();
                amountOfClickIncreasedUpgrades++;
                clickStrength += (long) Math.pow(1.1, (double) (amountOfClickIncreasedUpgrades - 1) / 5);
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

        countDownTimer = getBossCountDownTimer();
        cheatSheetCountDownTimer = getCheatSheetCountDownTimer();
        startPowerUpGenerator();
        closeMenu();
        refreshStats();
        resetProgressBar();
        setButtonVisibility();
        checkStartBossTimer();
    }

    public CountDownTimer getBossCountDownTimer() {
        return new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Calculate the remaining time in seconds
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                // Update the progress bar (reverse it to count down)
                binding.bossTimer.setProgress(secondsRemaining);
            }

            public void onFinish() {
                // Timer finished
                binding.bossTimer.setProgress(30);
                resetProgressBar();
                assignment.currentLevelChanged();
                stop30SecondBossTimer();
                start30SecondBossTimer();
            }
        };
    }

    public CountDownTimer getCheatSheetCountDownTimer() {
        return new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                binding.cheatSheetTimer.setProgress(secondsRemaining);
            }

            public void onFinish() {
                binding.cheatSheetTimer.setProgress(0);
                binding.cheatSheetTimer.setVisibility(View.INVISIBLE);
                assignment.endCheatSheet();
                cheatSheetCountDownTimer.cancel();

            }
        };
    }

    public void checkStartBossTimer() {
        if (CurrentLevel % 5 == 0) {
            start30SecondBossTimer();
        } else stop30SecondBossTimer();
    }

    public void resetProgressBar() {
        binding.progressBar.setProgress(0);
        binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
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

        assignment.startCheatSheet();
        cheatSheetCountDownTimer.start();
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

        if (CurrentLevel != 1)
            binding.prevStage.setVisibility(View.VISIBLE);
        else if (binding.prevStage.getVisibility() == View.VISIBLE) binding.prevStage.setVisibility(View.INVISIBLE);
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
            countDownTimer.start();
            isBossTimerRunning = true;
        }
    }

    @Override
    public void stop30SecondBossTimer() {
        if (isBossTimerRunning) {
            countDownTimer.cancel();
            isBossTimerRunning = false;
            binding.bossTimer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void changeMainBackground() {
        switch (CurrentLevel) {
            //starts with english
            case 201:// switch to math
                binding.getRoot().setBackgroundResource(R.drawable.math_0);
                assignment.setClassName("Math");
                break;
            case 401:// switch to PE
                binding.getRoot().setBackgroundResource(R.drawable.pe_0);
                assignment.setClassName("P.E");
                break;
            case 601: // switch to Science
                binding.getRoot().setBackgroundResource(R.drawable.science_0);
                assignment.setClassName("Science");
                break;
            case 801: // switch to History
                binding.getRoot().setBackgroundResource(R.drawable.history_0);
                assignment.setClassName("History");
                break;
            case 1000: // switch to final boss
                binding.getRoot().setBackgroundResource(R.drawable.math_0);
                assignment.setClassName("Department Of Education");
                break;
        }
        binding.currentClassText.setText(assignment.getClassName());
        Log.i("CURRENTSTATS", CurrentLevel + " is current level");
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
            imgID = R.drawable.assignmenttemp1;
            animator.start(100, 2.5F);

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
}
