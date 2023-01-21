package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash2);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageViewB = findViewById(R.id.imageViewB);
        MaterialButton fabAlliance  = findViewById(R.id.fabAlliance);
        MaterialButton fabRobot  = findViewById(R.id.fabRobot);

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