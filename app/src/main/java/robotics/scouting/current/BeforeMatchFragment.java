package robotics.scouting.current;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeforeMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeforeMatchFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    public BeforeMatchFragment() {
        // Required empty public constructor
    }

    public static BeforeMatchFragment newInstance() {
        BeforeMatchFragment fragment = new BeforeMatchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    Context context;
    EditText team;
    EditText match;
    CheckBox blueColorCheck;
    CheckBox redColorCheck;
    private boolean redAlliance ;
   private boolean blueAlliance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_before_match, container, false);
        context = container.getContext();
        team = root.findViewById(R.id.teamID);
        match = root.findViewById(R.id.matchID);
        blueColorCheck = root.findViewById(R.id.blueColorCheck);
        redColorCheck = root.findViewById(R.id.redColorCheck);
        redAlliance = MainActivity.getRedAlliance();
        blueAlliance = MainActivity.getBlueAlliance();
        team.setOnFocusChangeListener(this);
        match.setOnFocusChangeListener(this);
        blueColorCheck.setOnClickListener(this);
        redColorCheck.setOnClickListener(this);


        if (MainActivity.getTeam() == -1 && MainActivity.getMatch() == -1) {
            team.setText("");
            match.setText("");
        } else {
            team.setText(String.valueOf(MainActivity.getTeam()));
            match.setText(String.valueOf(MainActivity.getMatch()));
        }
        blueColorCheck.setChecked(blueAlliance == true);
        redColorCheck.setChecked(redAlliance == true);
        return root;
    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // Restore the values of blueAlliance and redAlliance
        if (savedInstanceState != null) {
            blueAlliance = savedInstanceState.getBoolean("blueAlliance", false); // false is the default value
            redAlliance = savedInstanceState.getBoolean("redAlliance", false);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blueColorCheck:
                if (blueAlliance == false) {
                    blueAlliance = true;
                    redAlliance = false;
                    blueColorCheck.setChecked(true);
                    redColorCheck.setChecked(false);
                } else if (blueAlliance == true){
                    blueAlliance = false;
                    blueColorCheck.setChecked(false);
                }
                MainActivity.setBlueAlliance(blueAlliance);
                MainActivity.setRedAlliance(redAlliance);
                break;

            case R.id.redColorCheck:
                if (redAlliance == false){
                    blueAlliance = false;
                    redAlliance = true;
                    blueColorCheck.setChecked(false);
                    redColorCheck.setChecked(true);
                } else if(redAlliance == true){
                    redAlliance = false;
                    redColorCheck.setChecked(false);
                }
                MainActivity.setRedAlliance(redAlliance);
                MainActivity.setBlueAlliance(blueAlliance);
                break;
        }
    }

    @Override
    public void onStop() {
        if (match != null && match.getText() != null && team != null && team.getText() != null) {
            String matchText = match.getText().toString();
            String teamText = team.getText().toString();
            if (!matchText.isEmpty()) {
                try {
                    int matchValue = Integer.parseInt(matchText);
                    int teamValue = Integer.parseInt(teamText);
                    MainActivity.setMatch(matchValue);
                    MainActivity.setTeam(teamValue);
                    MainActivity.setTeamName(String.valueOf(teamValue));
                    MainActivity.setMatchNum(String.valueOf(matchValue));
                } catch (NumberFormatException e) {
                    MainActivity.setTeam(0);
                }
            }
        }
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the values of blueAlliance and redAlliance
        outState.putBoolean("blueAlliance", blueAlliance);
        outState.putBoolean("redAlliance", redAlliance);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.teamID:
                    team.setText("");
                    break;
                case R.id.matchID:
                    match.setText("");
                    break;
            }
        }
    }
}
