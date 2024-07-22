package com.bytebrigade.attackoftheschool.gameplay;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Random;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class AssignmentScreen extends AppCompatActivity implements Assignment.CallBack {

    private Assignment assignment = new Assignment(10L, AssignmentName.values()[0]);
    public ActivityAssignmentScreenBinding binding;
    private View menuLayout;
    private boolean isMenuOpen = false;
    private ImageView powerUp;
    private GestureDetector gestureDetector;
    private Handler handler;
    private Random random;
    Button backtoDefaultButtons;
    private Runnable runnable;
    AssignmentAnimationListener animator;
    CheatSheetAnimator cheetSheetAnimator;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment_screen);
        assignment.setBackgroundSetter(this);
        binding.setAssignment(assignment);
        animator = new AssignmentAnimationListener(binding.clickableBlock);
        binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
        setButtonVisibility();
        powerUp = findViewById(R.id.cheatSheet);

        handler = new Handler();
        random = new Random();

        startPowerUpGenerator();
        binding.clickableBlock.setOnClickListener(v -> {
            assignment.incrementClick();
            binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
            binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
            binding.nameEditText.setText(new String("Level " + CurrentLevel));
            Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
            changeMainBackground();
            setButtonVisibility();
        });

        cheetSheetAnimator = new CheatSheetAnimator(powerUp);
        binding.godMode.setOnClickListener(v -> assignment.clickStrength += 1000000);
        binding.plus49.setOnClickListener(v -> FurthestLevel += 49);


        menuLayout = findViewById(R.id.menuLayout);
        Button store = findViewById(R.id.button3);
        Button backtoMainMenu = findViewById(R.id.backtoMainMenu);
        Button classes = findViewById(R.id.Classes);
        Button ChallengeTeahcer = findViewById(R.id.button5);
        Button PGTBotter = findViewById(R.id.botter);
        Button LibraryUpgrades = findViewById(R.id.LibraryUpgrades);
        ImageView downArrow = findViewById(R.id.imageView2);
        backtoDefaultButtons = findViewById(R.id.backtoDefaultButtons);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

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
        backtoDefaultButtons.setOnClickListener(v -> {

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
/*        downArrow.setOnClickListener(view -> {
            toggleMenu();
        });*/
        store.setOnClickListener(v -> {

        });
        backtoMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AssignmentScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        classes.setOnClickListener(v -> {

        });
        ChallengeTeahcer.setOnClickListener(v -> {

        });
        PGTBotter.setOnClickListener(v -> {

        });
        LibraryUpgrades.setOnClickListener(v -> {
            binding.bottomButtonsLayout.setVisibility(View.INVISIBLE);
            binding.libraryMenu.setVisibility(View.VISIBLE);

        });
        powerUp.setOnClickListener(v -> {
            clickedPowerUp();
        });

        binding.menuButton.setOnClickListener(v -> toggleMenu());
        binding.prevStage.setOnClickListener(v -> {
            if (CurrentLevel > 1) {
                CurrentLevel--;
                assignment.currentLevelChanged();
                binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
                binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
                binding.nameEditText.setText(new String("Level " + CurrentLevel));
                Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
                changeMainBackground();
                setButtonVisibility();
            }
        });
        binding.nextStage.setOnClickListener(v -> {
            if (CurrentLevel < FurthestLevel) {
                CurrentLevel++;
                assignment.currentLevelChanged();
                binding.progressBar.setProgress(0);
                binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
                binding.nameEditText.setText(new String("Level " + CurrentLevel));
                Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
                changeMainBackground();
                setButtonVisibility();
            }
        });
        binding.maxStage.setOnClickListener(v -> {
            if (CurrentLevel < FurthestLevel) {
                CurrentLevel = FurthestLevel;
                assignment.currentLevelChanged();
                binding.progressBar.setProgress(0);
                binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
                binding.nameEditText.setText(new String("Level " + CurrentLevel));
                Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
                changeMainBackground();
                setButtonVisibility();
            }
        });
        closeMenu();
        refreshStats();
    }

    @Override
    public void showAddedPoints(String message) {
        // Create a new TextView
        TextView textView = new TextView(this);
        textView.setText(message);

        // Add the TextView to the main layout
        binding.clickableBlock.addView(textView, new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // Get the starting Y position
        float startY = textView.getY();
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(40);
        // Set up the translation and fade-out animations
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(textView, "translationY", startY, startY - 200);
        moveUp.setDuration(2000);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);

        // Combine the animations into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveUp, fadeOut);

        // Remove the TextView after the animation ends
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                binding.clickableBlock.removeView(textView);
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
    private void clickedPowerUp() {
        cheetSheetAnimator.stop();
        powerUp.setVisibility(View.GONE);

        //CLICKED POWER UP CODE HERE


    }


    private void showPowerUp() {
        cheetSheetAnimator.start(1000, 10);
    }

    private void startPowerUpGenerator() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // 50% chance to show power-up
                if (random.nextBoolean() && powerUp.getVisibility() == View.GONE) {
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
        //stats[3] = "Time: " + "00:00:00";
        StringBuilder updatedStats = new StringBuilder();
        for (String stat : stats) {
            updatedStats.append(stat).append("\n");
        }
        binding.statsDisplay.setText(updatedStats.toString().trim());
    }

    @Override
    public void changeClickableBackground() {

        refreshStats();


        int completionPercentage = (int) Math.floor((assignment.getCurrentClickAmount() / (double) assignment.getMaxClickAmount()) * 10);
        // Adjust for special stages
        int imgID;
        if (CurrentLevel % 1001 == 0) {
            // Final boss
            imgID = R.drawable.assignmenttemp1;
            animator.start(100, 1.5F);
        } else if (CurrentLevel % 200 == 0) {

            //EVERY 200 BOSS

            // Professor boss

            animator.start(150, 1.5F);
            imgID = switch (CurrentLevel) {
                //ENGLISH PROFESSOR BELOW
                case 200 -> switch (playthroughs) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //MATH PROFESSOR BELOW
                case 400 -> switch (playthroughs) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //PE PROFESSOR BELOW
                case 600 -> switch (playthroughs) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //SCIENCE PROFESSOR BELOW
                case 800 -> switch (playthroughs) {
                    case 0 -> R.drawable.englishproff_0;
                    case 1 -> R.drawable.englishproff_0;
                    case 2 -> R.drawable.englishproff_0;
                    default -> R.drawable.englishproff_0;
                };

                //HISTORY PROFESSOR BELOW
                case 1000 -> switch (playthroughs) {
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
            imgID = R.drawable.testtemp;


            //test's picture animation


        } else if (CurrentLevel % 5 == 0) {
            // quiz/packet
            animator.start(350, 1F);
            imgID = R.drawable.quiztemp;


            //more quiztemp's animation


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
