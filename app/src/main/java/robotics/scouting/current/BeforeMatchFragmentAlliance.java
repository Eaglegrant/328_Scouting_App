package robotics.scouting.current;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeforeMatchFragmentAlliance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeforeMatchFragmentAlliance extends Fragment implements View.OnClickListener{

    public BeforeMatchFragmentAlliance() {
        // Required empty public constructor
    }
    public static BeforeMatchFragmentAlliance newInstance() {
        BeforeMatchFragmentAlliance fragment = new BeforeMatchFragmentAlliance();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    Context context;
    SwitchCompat alliance;
    EditText match;
    EditText team1;
    EditText team2;
    EditText team3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_before_match_alliance, container,false);
        context = container.getContext();
        alliance = root.findViewById(R.id.switch1);
        match = root.findViewById(R.id.matchID);
        team1 = root.findViewById(R.id.team1);
        team2 = root.findViewById(R.id.team2);
        team3 = root.findViewById(R.id.team3);
        match.setOnClickListener(this);
        team1.setOnClickListener(this);
        team2.setOnClickListener(this);
        team3.setOnClickListener(this);
        if (AllianceActivity.getMatch() == -1) {
            match.setText("");
        }else {
            match.setText(String.valueOf(AllianceActivity.getMatch()));
        }
        if (AllianceActivity.getAlliance().equals("Red")) {
            alliance.setChecked(true);
        }else {
            alliance.setChecked(false);
        }
        if (AllianceActivity.getTeam1().equals("NA")){
            team1.setText("");
        }else {
            team1.setText(AllianceActivity.getTeam1());
        }
        if (AllianceActivity.getTeam2().equals("NA")){
            team2.setText("");
        }else {
            team2.setText(AllianceActivity.getTeam2());
        }
        if (AllianceActivity.getTeam3().equals("NA")){
            team3.setText("");
        }else {
            team3.setText(AllianceActivity.getTeam3());
        }
        alliance.setOnClickListener(this);
        return root;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.matchID:
                match.setText("");
                break;
            case R.id.team1:
                team1.setText("");
                break;
            case R.id.team2:
                team2.setText("");
                break;
            case R.id.team3:
                team3.setText("");
                break;
            case R.id.switch1:
                if (alliance.isChecked()){
                    alliance.setChecked(false);
                }else{
                    alliance.setChecked(true);
                }
                break;
        }
        }
    @Override
    public void onStop() {
        if (match != null && match.getText() != null){
            String matchText = match.getText().toString();
            String team1Text = team1.getText().toString();
            String team2Text = team2.getText().toString();
            String team3Text = team3.getText().toString();
            if (!team1Text.isEmpty()) {
                AllianceActivity.setTeam1(team1Text);
            }
            if (!team2Text.isEmpty()) {
                AllianceActivity.setTeam2(team2Text);
            }
            if (!team3Text.isEmpty()) {
                AllianceActivity.setTeam3(team3Text);
            }
            if (alliance.isChecked()){
                AllianceActivity.setAlliance("Red");
                AllianceActivity.setAllianceName("Red");
            }else{
                AllianceActivity.setAlliance("Blue");
                AllianceActivity.setAllianceName("Blue");
            }
            if (!matchText.isEmpty()) {
                try {
                    int matchValue = Integer.parseInt(matchText);
                    AllianceActivity.setMatch(matchValue);
                    AllianceActivity.setMatchNum(String.valueOf(matchValue));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onStop();
    }
}