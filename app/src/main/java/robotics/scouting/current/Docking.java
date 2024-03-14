package robotics.scouting.current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Docking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docking extends Fragment {

    private int endgamePoints; // Declare endgamePoints as a field

    public Docking() {
        // Required empty public constructor
    }

    public static Docking newInstance() {
        Docking fragment = new Docking();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_docking, container, false);

        // Assuming you have checkboxes in your fragment_docking layout
        final CheckBox harmonyCheckBox = view.findViewById(R.id.checkbox_harmony);
        final CheckBox trapCheckBox = view.findViewById(R.id.checkbox_trap);
        final CheckBox spotCheckBox = view.findViewById(R.id.checkbox_spot);
        final CheckBox hangCheckBox = view.findViewById(R.id.checkbox_hang);
        final CheckBox parkedCheckBox = view.findViewById(R.id.checkbox_hang345);

        boolean harmonyChecked = MainActivity.getHarmonyCheck();
        if (harmonyChecked){
            harmonyCheckBox.setChecked(true);
        }else{
            harmonyCheckBox.setChecked(false);
        }
        boolean trapChecked = MainActivity.getTrapCheck();
        if (trapChecked){
            trapCheckBox.setChecked(true);
        }else{
            trapCheckBox.setChecked(false);
        }
        boolean spotChecked = MainActivity.getSpotCheck();
        if (spotChecked){
            spotCheckBox.setChecked(true);
        }else{
            spotCheckBox.setChecked(false);
        }

        boolean hangChecked = MainActivity.getHangCheck();
        if (hangChecked){
            hangCheckBox.setChecked(true);
        }else{
            hangCheckBox.setChecked(false);
        }

        boolean parkedChecked = MainActivity.getParkedCheck();
        if (parkedChecked){
            parkedCheckBox.setChecked(true);
        }else{
            parkedCheckBox.setChecked(false);
        }
        // Set OnCheckedChangeListener for each checkbox to update points when clicked
        harmonyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endgamePoints = calculateAndShowPoints();
                MainActivity.setHarmonyCheck(isChecked);
            }
        });

        trapCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endgamePoints = calculateAndShowPoints();
                MainActivity.setTrapCheck(isChecked);

            }
        });

        spotCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endgamePoints = calculateAndShowPoints();
                MainActivity.setSpotCheck(isChecked);

            }
        });

        hangCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endgamePoints = calculateAndShowPoints();
                MainActivity.setHangCheck(isChecked);

            }
        });

        parkedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endgamePoints = calculateAndShowPoints();
                MainActivity.setParkedCheck(isChecked);

            }
        });

        

        return view;
    }

    public int calculateAndShowPoints() {
        // Assign values to each checkbox
        int harmonyValue = 2;
        int trapValue = 5;
        int spotlightValue = 1;
        int chainHangValue = 3;
        int parkedValue = 1; // Value to add if "If not hang, they parked" is selected

        // Initialize total points
        int totalPoints = 0;

        // Check each checkbox and add corresponding values to total points
        CheckBox harmonyCheckBox = getView().findViewById(R.id.checkbox_harmony);
        if (harmonyCheckBox.isChecked()) {
            totalPoints += harmonyValue;
        }

        CheckBox trapCheckBox = getView().findViewById(R.id.checkbox_trap);
        if (trapCheckBox.isChecked()) {
            totalPoints += trapValue;
        }

        CheckBox spotlightCheckBox = getView().findViewById(R.id.checkbox_spot);
        if (spotlightCheckBox.isChecked()) {
            totalPoints += spotlightValue;
        }

        CheckBox hangCheckBox = getView().findViewById(R.id.checkbox_hang);
        if (hangCheckBox.isChecked()) {
            totalPoints += chainHangValue;
        }

        CheckBox parkedCheckBox = getView().findViewById(R.id.checkbox_hang345);
        if (parkedCheckBox.isChecked()) {
            totalPoints += parkedValue;
        }

        // Display the calculated points
        TextView totalPointsView = getView().findViewById(R.id.total_points);
        totalPointsView.setText("Total Points: " + totalPoints);
        // Return the calculated total points
        return totalPoints;
    }

    public void clearData() {
        // Other variable assignments...

        // Call calculateAndShowPoints method and assign its return value to endgamePoints
        endgamePoints = calculateAndShowPoints();

        // Other variable assignments...
    }
}
