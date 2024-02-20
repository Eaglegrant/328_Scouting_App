package robotics.scouting.current;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Docking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docking extends Fragment {

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

        // Assuming you have a checkbox in your fragment_docking layout with the id checkbox_id
        final CheckBox checkBox = view.findViewById(R.id.checkbox_harmony);

        // Do something with the checkbox, for example, uncheck it if checked
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
        }

        // Assuming you have a checkbox for "Did you do Trap?" with the id checkbox_trap
        final CheckBox trapCheckBox = view.findViewById(R.id.checkbox_trap);

        // Do something with the "Did you do Trap?" checkbox, for example, check it if unchecked
        if (!trapCheckBox.isChecked()) {
            trapCheckBox.setChecked(true);
        }

        // Assuming you have a checkbox for "Did you Spotlight?" with the id checkbox_spot
        final CheckBox spotCheckBox = view.findViewById(R.id.checkbox_spot);

        // Do something with the "Did you Spotlight?" checkbox, for example, uncheck it if checked
        if (spotCheckBox.isChecked()) {
            spotCheckBox.setChecked(false);
        }

        // Button to calculate points
        Button calculatePointsButton = view.findViewById(R.id.btnCalculatePoints);
        calculatePointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndShowPoints();
            }
        });

        return view;
    }
    private void calculateAndShowPoints() {
        // Assign values to each checkbox
        int harmonyValue = 2;
        int trapValue = 5;     // Example value for "Did you do Trap?"
        int spotlightValue = 1;
        int chainHangValue = 3;// Example value for "Did you Spotlight?"

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

        // Display the calculated points
        Toast.makeText(getContext(), "Total Points: " + totalPoints, Toast.LENGTH_SHORT).show();
    }


}