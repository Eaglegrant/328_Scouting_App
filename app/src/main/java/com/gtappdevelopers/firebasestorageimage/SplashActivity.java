package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.app.lets_go_splash.CreateAnim;
import com.app.lets_go_splash.OnAnimationListener;
import com.app.lets_go_splash.StarterAnimation;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private ArrayList<Animation> getAnimList() {
        ArrayList<Animation> animList = new ArrayList<>();
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.no_animaiton));
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.rotate));
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.zoom_out_fast));
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.fade_in));
        return animList;
    }
    private Button btnToggleDark;
    public SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static boolean isDarkModeOn;
    static public String setNight(){
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);

            editor.putBoolean(
                    "isDarkModeOn", false);
            editor.apply();
            return "Disable Dark Mode";

        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            editor.putBoolean(
                    "isDarkModeOn", true);
            editor.apply();
            return "Enable Dark Mode";
        }
    }
    ArrayList<Integer> gridQR = new ArrayList<>();
    ArrayList<Integer> teleQR = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash2);
        for (int i = 0; i < 27; i++) {
            gridQR.add(i,0);
            teleQR.add(i,0);
        }

        MainActivity.setGrid(gridQR);
        MainActivity.setTeleGrid(teleQR);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageViewB = findViewById(R.id.imageViewB);
        MaterialButton fabAlliance  = findViewById(R.id.fabAlliance);
        MaterialButton fabRobot  = findViewById(R.id.fabRobot);
        btnToggleDark = findViewById(R.id.fabDarkLight);
        sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);

        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            btnToggleDark.setText(
                    "Disable Dark Mode");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btnToggleDark
                    .setText(
                            "Enable Dark Mode");
        }

        btnToggleDark.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(
                                    "Enable Dark Mode");
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(
                                    "Disable Dark Mode");
                        }
                    }
                });
        new StarterAnimation(getAnimList(), new OnAnimationListener() {
            @Override
            public void onStartAnim() { // TODO::

            }

            @Override
            public void onRepeat() { // TODO::
            }

            @Override
            public void onEnd() {
                imageView.setVisibility(View.GONE);
                imageViewB.setVisibility(View.VISIBLE);
            }
        }).startSequentialAnimation(imageView);

        fabAlliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      //  <!-- TODO: Create the Alliance Menu/Scouting Menu -->
            }
        });
        fabRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}