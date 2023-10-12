package robotics.scouting.current;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeforeMatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeforeMatchFragment extends Fragment implements View.OnClickListener{

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
    CheckBox redCheck;
    CheckBox blueCheck;
    CheckBox coneCheck;
    CheckBox cubeCheck;
    BlueAllianceAPI blueAllianceAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_before_match, container,false);
        context = container.getContext();
        team = root.findViewById(R.id.teamID);
        match = root.findViewById(R.id.matchID);
        blueCheck = root.findViewById(R.id.blueColorCheck);
        redCheck = root.findViewById(R.id.redColorCheck);
        coneCheck = root.findViewById(R.id.preLoadConeCheck);
        cubeCheck = root.findViewById(R.id.preLoadCubeCheck);
        blueCheck.setOnClickListener(this);
        redCheck.setOnClickListener(this);
        cubeCheck.setOnClickListener(this);
        coneCheck.setOnClickListener(this);
        team.setOnClickListener(this);
        match.setOnClickListener(this);
        blueAllianceAPI = new BlueAllianceAPI(this);

        // Make the API request
        blueAllianceAPI.getTeamData("/status", response -> {
            // Handle the response
            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
        }, error -> {
            // Handle the error
            Log.d("API", "onErrorResponse: " + error.toString());
        });
        if (MainActivity.getTeam() == -1 && MainActivity.getMatch() == -1) {
            team.setText("");
            match.setText("");
        }else {
            team.setText(String.valueOf(MainActivity.getTeam()));
            match.setText(String.valueOf(MainActivity.getMatch()));
        }
        return root;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teamID:
                team.setText("");
                break;
            case R.id.matchID:
                match.setText("");
                break;
            case (R.id.blueColorCheck):
                    redCheck.setChecked(false);
                break;
            case (R.id.redColorCheck):
                    blueCheck.setChecked(false);
                break;
            case (R.id.preLoadConeCheck):
                cubeCheck.setChecked(false);
                break;
            case (R.id.preLoadCubeCheck):
                coneCheck.setChecked(false);
                break;
        }
        }
    @Override
    public void onStop() {
        if (match != null && match.getText() != null && team != null && team.getText() != null){
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
}