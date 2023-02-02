package robotics.scouting.current;

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
    public static String event;
    static public String setNight(){
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);

            editor.putBoolean("isDarkModeOn", false);
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
    ArrayList<Integer> AgridQR = new ArrayList<>();
    ArrayList<Integer> AteleQR = new ArrayList<>();
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
            AgridQR.add(i,0);
            AteleQR.add(i,0);
        }

        MainActivity.setGrid(gridQR);
        MainActivity.setTeleGrid(teleQR);
        AllianceActivity.setGrid(AgridQR);
        AllianceActivity.setTeleGrid(AteleQR);
        ImageView imageView = findViewById(R.id.imageView);
        ImageView imageViewB = findViewById(R.id.imageViewB);
        MaterialButton fabAlliance  = findViewById(R.id.fabAlliance);
        MaterialButton fabRobot  = findViewById(R.id.fabRobot);
        MaterialButton fabSettings = findViewById(R.id.fabSettings);
        sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);

        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        event = sharedPreferences.getString("event","");

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }


        new StarterAnimation(getAnimList(), new OnAnimationListener() {
            @Override
            public void onStartAnim() {

            }

            @Override
            public void onRepeat() {
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
                Intent intent = new Intent(SplashActivity.this,AllianceActivity.class);
                startActivity(intent);
            }
        });
        fabRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,faqActivityMain.class);
                startActivity(intent);
            }
        });
    }
}