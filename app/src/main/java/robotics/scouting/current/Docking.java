package robotics.scouting.current;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Docking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docking extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    public Docking() {
        // Required empty public constructor
    }
    public static Docking newInstance() {
        Docking fragment = new Docking();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    String balance = "Did Not Dock";
    Spinner dockSpinner;
    Context context;
    private int seconds = 0;
    private int minutes = 0;
    private int milliseconds = 0;

    private boolean running;

    private boolean wasRunning;
    TextView timeView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        balance = parent.getItemAtPosition(pos).toString();
    }
Handler handler;
    public void onNothingSelected(AdapterView<?> parent) {
        balance = "Did Not Dock";
    }
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
    public void onStop() {
        MainActivity.setBalance(balance);
        MainActivity.setTime(minutes,seconds,milliseconds);
        super.onStop();
    }
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabStopWatch:
                if (running) {
                    running = false;
                    setStartButton("Start Timer");
                } else{
                running = true;
                setStartButton("Stop Timer");
                }
                break;
            case R.id.fabReset:
                running = false;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;
                MainActivity.setTime(minutes,seconds,milliseconds);
                updateTime( String.valueOf(seconds) + "." + String.valueOf(milliseconds) + " Seconds");
                setStartButton("Start Timer");
                break;
        }
    }
MaterialButton startButton;
    MaterialButton resetButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_docking, container,false);
        context = container.getContext();
        dockSpinner = (Spinner) root.findViewById(R.id.DockingSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context,
                R.layout.view_spinner_item,
                getResources().getStringArray(R.array.dockingOrder)
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dockSpinner.setAdapter(adapter);
           dockSpinner.setOnItemSelectedListener(this);
           balance = MainActivity.getBalance();
           dockSpinner.setSelection(adapter.getPosition(balance));
           startButton = root.findViewById(R.id.fabStopWatch);
           handler = new Handler();
           startButton.setOnClickListener(this);
           resetButton = root.findViewById(R.id.fabReset);
              resetButton.setOnClickListener(this);
           timeView = root.findViewById(R.id.timerText);
              minutes = MainActivity.getTimeMin();
                seconds = MainActivity.getTimeSec();
                milliseconds = MainActivity.getTimeMillis();
                updateTime(String.valueOf(seconds) + "." + String.valueOf(milliseconds)+" Seconds");
        runTimer();
        return root;
    }
    public void updateTime(String newText){
        timeView.setText(newText);
    }
    public void setStartButton(String newText){
        startButton.setText(newText);
    }
}