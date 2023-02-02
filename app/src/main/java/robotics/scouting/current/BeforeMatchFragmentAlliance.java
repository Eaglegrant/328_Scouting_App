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
        match.setOnClickListener(this);

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
        alliance.setOnClickListener(this);
        return root;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.matchID:
                match.setText("");
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