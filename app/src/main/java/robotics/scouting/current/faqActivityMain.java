package robotics.scouting.current;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class faqActivityMain extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static boolean isDarkModeOn;
    MaterialButton DarkMode;
    TextView dataText1;
    LinearLayout dataLayout1;
    TextView dataText2;
    LinearLayout dataLayout2;
    EditText event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_faq_main);
        fab = findViewById(R.id.fab);
        CardView card1 = findViewById(R.id.cardView1);
        event = findViewById(R.id.eventLocation);
        CardView card2 = findViewById(R.id.cardView2);
        dataText1 = findViewById(R.id.data1);
        dataText2 = findViewById(R.id.data2);
        dataLayout1 = findViewById(R.id.layout1);
        dataLayout2 = findViewById(R.id.layout2);
        dataLayout1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        dataLayout2.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        DarkMode = findViewById(R.id.fabDark);
        fab.setOnClickListener(this);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);

        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        event.setText(sharedPreferences.getString("event",""));
        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            DarkMode.setText(
                    "Disable Dark Mode");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            DarkMode
                    .setText(
                            "Enable Dark Mode");
        }
        DarkMode.setOnClickListener(
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
                            DarkMode.setText(
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
                            DarkMode.setText(
                                    "Disable Dark Mode");
                        }
                    }
                });
    }
    @Override
    public void onStop() {
        super.onStop();
        editor.putString("event",event.getText().toString());
        editor.apply();
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(faqActivityMain.this,MainActivity.class);
                intent.putExtra("event",event.getText().toString());
                startActivity(intent);
                break;
            case R.id.cardView1:
                int v = (dataText1.getVisibility() == View.GONE)?View.VISIBLE:View.GONE;
                TransitionManager.beginDelayedTransition(dataLayout1, new android.transition.AutoTransition());
                dataText1.setVisibility(v);
                break;
            case R.id.cardView2:
                int v2 = (dataText2.getVisibility() == View.GONE)?View.VISIBLE:View.GONE;
                TransitionManager.beginDelayedTransition(dataLayout2, new android.transition.AutoTransition());
                dataText2.setVisibility(v2);
                break;
        }
    }
}