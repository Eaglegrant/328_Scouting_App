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
public class Auto extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    public Auto() {
        // Required empty public constructor
    }

    public static Auto newInstance() {
        Auto fragment = new Auto();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
//Establishes all the elements the code will interact with in the layout
    Context context;
    EditText dataEdt1;
    Button speaker;
    Button amp;
    Button amped;

    Button miss;
    Button downed;
    Button undo;
    TextView downedTimer;
    EditText intake;
//Establishes all the variables that will be used
    int speakerCount;
    int ampCount;
    boolean ampedBool;
    int missCount;
    int autoMissCount;
    boolean downedBool;
    int undoValue;
    int totalPoints;
    int autoPoints;
    int highCount;
    int lowCount;
    int totalCount;
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
        //Links all the elements to the layout so they can interact
        intake = root.findViewById(R.id.EditTextIntake);
        resetButton = root.findViewById(R.id.ResetButton);
        speaker = root.findViewById(R.id.HighGoalButton);
        amp = root.findViewById(R.id.LowGoalButton);
        amped = root.findViewById(R.id.AmpedButton);
        miss = root.findViewById(R.id.MissButton);
        downed = root.findViewById(R.id.DownedButton);
        undo = root.findViewById(R.id.UndoButton);
        downedTimer = root.findViewById(R.id.DownedTime);
        //set variables to the ones stored in main activity
        totalPoints = MainActivity.getTotalPoints();
        autoPoints = MainActivity.getAutoPoints();
        speakerCount = MainActivity.getAutoHighCount();
        ampCount = MainActivity.getAutoLowCount();
        missCount = MainActivity.getMissCount();
        autoMissCount = MainActivity.getAutoMissCount();
        highCount = MainActivity.getAutoHighCount();
        lowCount = MainActivity.getAutoLowCount();
        totalCount = MainActivity.getTotalCount();
        //sets ;isteners for all the buttons
        speaker.setOnClickListener(this);
        amp.setOnClickListener(this);
        amped.setOnClickListener(this);
        miss.setOnClickListener(this);
        downed.setOnClickListener(this);
        undo.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        intake.setOnFocusChangeListener(this);
        //sets the text of the buttons to the count stored in main activity
        speaker.setText("Speaker Goal: " + speakerCount);
        amp.setText("Amp Goal: " + ampCount);
        runTimer();
        if(speakerCount == -1 && ampCount == -1 && missCount == -1) {
            speaker.setText("");
            amp.setText("");
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
        String intakeText = intake.getText().toString();
        if (intake.getText() != null) {
            String matchText = intake.getText().toString();
            if (!intakeText.isEmpty()) {
                try {
                    MainActivity.setIntakeOrder(intakeText);
                } catch (NumberFormatException e) {
                    MainActivity.setIntakeOrder("Not inputted");
                }
            }
        }

        super.onStop();
    }
    private int updater(int value, Button button, String name) {
        //This function ups the value of button pressed by one
        value += 1;
        String tempText = name + String.valueOf(value);
        button.setText(tempText);
        return value;
    }
    public void updateTime(String newText){
        downedTimer.setText("Downed Time: "+newText);
    }
    private int updaterMinus(int value, Button button, String name) {
        //This function brings down the value of button pressed by one
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.EditTextIntake:
                    intake.setText("");
                    break;
            }
        }
    }
    @Override
    //Define what happens on click for each instance
    public void onClick(View v) {
        MaterialButton image = (MaterialButton) v;
        switch (v.getId()) {
            case R.id.HighGoalButton:
                //Update the value of goals by one, and records points and other information to main activity
                speakerCount = updater(speakerCount, speaker, "Speaker Goal: ");
                undoValue = 1;
                totalPoints = totalPoints + 5;
                autoPoints = autoPoints + 5;
                highCount = highCount +1;
                totalCount = totalCount + 1;
                MainActivity.setTotalCount(totalCount);
                MainActivity.setAutoHighCount(highCount);
                MainActivity.setTotalPoints(totalPoints);
                MainActivity.setAutoPoints(autoPoints);
                MainActivity.setAutoHighCount(speakerCount);
                break;
            case R.id.LowGoalButton:
                //Same as the last one, but for the amp goal
                ampCount = updater(ampCount, amp, "Amp Goal: ");
                undoValue = 2;
                totalPoints = totalPoints + 2;
                autoPoints = autoPoints + 2;
                lowCount = lowCount +1;
                totalCount = totalCount + 1;
                MainActivity.setTotalCount(totalCount);
                MainActivity.setAutoHighCount(lowCount);
                MainActivity.setTotalPoints(totalPoints);
                MainActivity.setAutoPoints(autoPoints);
                MainActivity.setAutoLowCount(ampCount);
                break;
            case R.id.MissButton:
                //records the number of misses
                autoMissCount = updater(autoMissCount, miss, "Miss: ");
                undoValue = 3;
                missCount ++ ;
                MainActivity.setAutoMissCount(autoMissCount);
                MainActivity.setMissCount(missCount);
                totalCount = totalCount + 1;
                MainActivity.setTotalCount(totalCount);
                break;
            case R.id.UndoButton:
                //undoes an action based on undo value, which is specific for each button
                if (undoValue == 1) {
                    speakerCount = updaterMinus(speakerCount, speaker, "Speaker Goal: ");
                    totalPoints = totalPoints - 5;
                    autoPoints = autoPoints - 5;
                    highCount = highCount -1;
                    totalCount = totalCount - 1;
                    MainActivity.setTotalCount(totalCount);
                    MainActivity.setAutoHighCount(highCount);
                    MainActivity.setTotalPoints(totalPoints);
                    MainActivity.setAutoPoints(autoPoints);
                    MainActivity.setAutoHighCount(speakerCount);
                } else if (undoValue == 2) {
                    ampCount = updaterMinus(ampCount, amp, "Amp Goal: ");
                    totalPoints = totalPoints - 2;
                    autoPoints = autoPoints - 2;
                    lowCount = lowCount -1;
                    totalCount = totalCount - 1;
                    MainActivity.setTotalCount(totalCount);
                    MainActivity.setAutoHighCount(lowCount);
                    MainActivity.setTotalPoints(totalPoints);
                    MainActivity.setAutoPoints(autoPoints);
                    MainActivity.setAutoLowCount(ampCount);
                } else if (undoValue == 3) {
                    autoMissCount = updaterMinus(autoMissCount, miss, "Miss: ");
                    MainActivity.setMissCount(autoMissCount);
                    missCount -- ;
                    MainActivity.setAutoMissCount(missCount);
                    totalCount = totalCount - 1;
                    MainActivity.setTotalCount(totalCount);
                }
                break;
            case R.id.AmpedButton:
                //if thsi bool is active, it means it is amped
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
                //this records how long a robot was downed
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
