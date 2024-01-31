package robotics.scouting.current;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Auto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Auto extends Fragment implements View.OnClickListener {

    public Auto() {
        // Required empty public constructor
    }

    public static Auto newInstance() {
        Auto fragment = new Auto();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    Context context;
    EditText dataEdt1;
    Button highGoal;
    Button lowGoal;
    Button amped;

    Button miss;
    Button downed;
    Button undo;
    TextView downedTimer;

    int highGoalCount;
    int lowGoalCount;
    boolean ampedBool;
    int missCount;
    boolean downedBool;
    int undoValue;
    int points;
    private Button resetButton;

    @Override
    public void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                assert object != null;
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_auto, container, false);
        context = container.getContext();
        resetButton = root.findViewById(R.id.ResetButton);
        highGoal = root.findViewById(R.id.HighGoalButton);
        lowGoal = root.findViewById(R.id.LowGoalButton);
        amped = root.findViewById(R.id.AmpedButton);
        points = MainActivity.getPoints();
        miss = root.findViewById(R.id.MissButton);
        downed = root.findViewById(R.id.DownedButton);
        undo = root.findViewById(R.id.UndoButton);
        downedTimer = root.findViewById(R.id.DownedTime);
        highGoal.setOnClickListener(this);
        lowGoal.setOnClickListener(this);
        amped.setOnClickListener(this);
        miss.setOnClickListener(this);
        downed.setOnClickListener(this);
        undo.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        runTimer();
        if(highGoalCount == -1 && lowGoalCount == -1 && missCount == -1) {
            highGoal.setText("");
            lowGoal.setText("");
            miss.setText("");
        } else {
     /*       highCube.setText(String.valueOf(MainActivity.setAutoScores(HighCubeCount));
            midCube.setText(String.valueOf(MainActivity.setAutoScores(MidCubeCount));
            midCube.setText(String.valueOf(MainActivity.setAutoScores(LowCubeCount));
            highCone.setText(String.valueOf(MainActivity.setAutoScores(HighConeCount));
        */}
        return root;
    }

    @Override
    public void onStop() {
        //   MainActivity.setAutoC(dataEdt1.getText().toString());
        // MainActivity.setGrid(gridQR);
        super.onStop();
    }

    private int updater(int value, Button button, String name) {
        //Ups the value of button pressed by one
        value += 1;
        String tempText = name + String.valueOf(value);
        button.setText(tempText);
        return value;
    }
    public void updateTime(String newText){
        downedTimer.setText("Downed Time: "+newText);
    }
    private int updaterMinus(int value, Button button, String name) {
        //brings down the value of button pressed by one
        if (value > 0) {
            value -= 1;
        }
        String tempText = name + String.valueOf(value);
        button.setText(tempText);
        return value;
    }
    private int seconds = 0;
    private int minutes = 0;
    private int milliseconds = 0;

    private boolean running;

    private boolean wasRunning;
    Handler handler = new Handler();
    //Undo value correspond to what button was last presses, i couldn't figure out how to make it text
    //Undo value works like this: High cone = 1, Mid cone = 2, Low cone = 3, High cube = 4, Mid cube = 5, Low cube = 6, and Miss = 7
    private void runTimer() {
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                if (running) {
                    milliseconds+=4;
                    if (milliseconds %100 == 0){
                        milliseconds = 0;
                        seconds++;
                        if (seconds %60 == 0){
                            seconds = 0;
                            minutes++;
                        }
                    }

                }

                updateTime(String.valueOf(seconds) + "." + String.valueOf(milliseconds)+" Seconds");
                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 40);
            }
        });
    }
    @Override
    public void onClick(View v) {
        MaterialButton image = (MaterialButton) v;
        switch (v.getId()) {
            case R.id.HighGoalButton:
                highGoalCount = updater(highGoalCount, highGoal, "High Goal: ");
                undoValue = 1;
                points = points + 5;
                MainActivity.setPoints(points);
                break;
            case R.id.LowGoalButton:
                lowGoalCount = updater(lowGoalCount, lowGoal, "Low Goal: ");
                undoValue = 2;
                MainActivity.setPoints(points);
                break;
            case R.id.MissButton:
                missCount = updater(missCount, miss, "Miss: ");
                undoValue = 3;
                points = points + 2;
                break;
            case R.id.UndoButton:
                if (undoValue == 1) {
                    highGoalCount = updaterMinus(highGoalCount, highGoal, "High Goal: ");
                    points = points - 5;
                    MainActivity.setPoints(points);
                } else if (undoValue == 2) {
                    lowGoalCount = updaterMinus(lowGoalCount, lowGoal, "Low Goal: ");
                    points = points - 2;
                    MainActivity.setPoints(points);
                } else if (undoValue == 3) {
                    missCount = updaterMinus(missCount, miss, "Miss: ");
                }
                break;
            case R.id.AmpedButton:
                ampedBool = !ampedBool;
                if (ampedBool) {
                    amped.setText("AMPED!!!!");
                    amped.setBackgroundColor(Color.RED);
                } else {
                    amped.setText("Amped?");
                    amped.setBackgroundColor(Color.rgb(15, 157, 88));
                }
                break;
            case R.id.DownedButton:
                downedBool = !downedBool;
                if (downedBool) {
                    downed.setText("Working?");
                    downed.setBackgroundColor(Color.RED);
                    running = true;
                } else {
                        downed.setText("Downed?");
                        downed.setBackgroundColor(Color.rgb(15, 157, 88));
                        running = false;
                }
                break;
            case R.id.ResetButton:
                running = false;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;
                }
        }
}
