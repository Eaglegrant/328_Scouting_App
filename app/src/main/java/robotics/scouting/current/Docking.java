import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class Docking extends Fragment {

    private int endgamePoints = 0; // Declare endgamePoints as a field
    private TextView pointsTextView; // Declare TextView for live updates

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

        // Assuming you have checkboxes and TextView in your fragment_docking layout
        final CheckBox harmonyCheckBox = view.findViewById(R.id.checkbox_harmony);
        final CheckBox trapCheckBox = view.findViewById(R.id.checkbox_trap);
        final CheckBox spotCheckBox = view.findViewById(R.id.checkbox_spot);
        final CheckBox hangCheckBox = view.findViewById(R.id.checkbox_hang);
        final CheckBox parkedCheckBox = view.findViewById(R.id.checkbox_hang345);
        pointsTextView = view.findViewById(R.id.pointsTextView); // Initialize TextView

        // Calculate and show points initially
        calculateAndShowPoints();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        // Update endgamePoints before fragment stops
        endgamePoints = calculateAndShowPoints();
        MainActivity.setEndgamePoints(endgamePoints);
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

        // Update the TextView with the calculated total points
        pointsTextView.setText("Points: " + totalPoints);

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
