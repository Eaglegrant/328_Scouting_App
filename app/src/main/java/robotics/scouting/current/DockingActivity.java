package robotics.scouting.current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DockingActivity extends Fragment {

    public DockingActivity() {
        // Required empty public constructor
    }

    public static DockingActivity newInstance() {
        DockingActivity fragment = new DockingActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_docking, container, false);

        // Assuming you have checkboxes in your fragment_docking layout
        final CheckBox harmonyCheckBox = view.findViewById(R.id.checkbox_harmony);
        final CheckBox trapCheckBox = view.findViewById(R.id.checkbox_trap);
        final CheckBox spotlightCheckBox = view.findViewById(R.id.checkbox_spot);
        final CheckBox hangCheckBox = view.findViewById(R.id.checkbox_hang);
        final CheckBox parkedCheckBox = view.findViewById(R.id.checkbox_hang345);
        final CheckBox firstCheckBox = view.findViewById(R.id.checkbox_trap_2);
        final CheckBox secondThirdCheckBox = view.findViewById(R.id.checkbox_trap_22);

        // Set initial states of checkboxes
        boolean harmonyChecked = MainActivity.getHarmonyCheck();
        harmonyCheckBox.setChecked(harmonyChecked);

        boolean trapChecked = MainActivity.getTrapCheck();
        trapCheckBox.setChecked(trapChecked);

        boolean spotlightChecked = MainActivity.getSpotCheck();
        spotlightCheckBox.setChecked(spotlightChecked);

        boolean hangChecked = MainActivity.getHangCheck();
        hangCheckBox.setChecked(hangChecked);

        boolean parkedChecked = MainActivity.getParkedCheck();
        parkedCheckBox.setChecked(parkedChecked);

        boolean firstChecked = MainActivity.getFirstChecked();
        firstCheckBox.setChecked(firstChecked);

        boolean secondThirdChecked = MainActivity.getSecondThirdChecked();
        secondThirdCheckBox.setChecked(secondThirdChecked);

        // Set OnCheckedChangeListener for each checkbox to update points when clicked
        harmonyCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setHarmonyCheck(isChecked);
            calculateAndShowPoints();
        });

        trapCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setTrapCheck(isChecked);
            calculateAndShowPoints();
        });

        spotlightCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setSpotCheck(isChecked);
            calculateAndShowPoints();
        });

        hangCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setHangCheck(isChecked);
            calculateAndShowPoints();
        });

        parkedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setParkedCheck(isChecked);
            calculateAndShowPoints();
        });

        firstCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setFirstChecked(isChecked);
            calculateAndShowPoints();
        });

        secondThirdCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.setSecondThirdChecked(isChecked);
            calculateAndShowPoints();
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

        CheckBox firstCheckBox = getView().findViewById(R.id.checkbox_trap_2);
        if (firstCheckBox.isChecked()) {

        }

        // Display the calculated points
        TextView totalPointsView = getView().findViewById(R.id.total_points);
        totalPointsView.setText("Total Points: " + totalPoints);
        // Return the calculated total points
        return totalPoints;
    }

    public void clearData() {
        // Other variable assignments...
        int endgamePoints;
        // Call calculateAndShowPoints method and assign its return value to endgamePoints
        endgamePoints = calculateAndShowPoints();

        // Other variable assignments...
    }
}
