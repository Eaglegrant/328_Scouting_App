package robotics.scouting.current;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThoughtsAlliance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThoughtsAlliance extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    public ThoughtsAlliance() {
        // Required empty public constructor
    }
    public static ThoughtsAlliance newInstance() {
        ThoughtsAlliance fragment = new ThoughtsAlliance();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    String bestDefense;
    String worstDefense;
    String defense;
    String bestOffense;
    String worstOffense;
    String offense;
    Spinner defenseSpinner;
    Spinner bestDefenseSpinner;
    Spinner worstDefenseSpinner;
    Spinner offenseSpinner;
    Spinner bestOffenseSpinner;
    Spinner worstOffenseSpinner;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (parent == defenseSpinner) {
            defense = parent.getItemAtPosition(pos).toString();
        }else if (parent == offenseSpinner) {
            offense = parent.getItemAtPosition(pos).toString();
        }else if (parent == bestDefenseSpinner) {
            bestDefense = parent.getItemAtPosition(pos).toString();
        }else if (parent == worstDefenseSpinner) {
            worstDefense = parent.getItemAtPosition(pos).toString();
        }else if (parent == bestOffenseSpinner) {
            bestOffense = parent.getItemAtPosition(pos).toString();
        }else if (parent == worstOffenseSpinner) {
            worstOffense = parent.getItemAtPosition(pos).toString();
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent == defenseSpinner) {
            defense = "NA";
        }else if (parent == offenseSpinner) {
            offense = "NA";
        }else if (parent == bestDefenseSpinner) {
            bestDefense = "NA";
        }else if (parent == worstDefenseSpinner) {
            worstDefense = "NA";
        }else if (parent == bestOffenseSpinner) {
            bestOffense = "NA";
        }else if (parent == worstOffenseSpinner) {
            worstOffense = "NA";
        }
    }
    @Override
    public void onStop() {
        AllianceActivity.setDefense1(bestDefense);
        AllianceActivity.setDefense2(defense);
        AllianceActivity.setDefense3(worstDefense);
        AllianceActivity.setOffense1(bestOffense);
        AllianceActivity.setOffense2(offense);
        AllianceActivity.setOffense3(worstOffense);
        super.onStop();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_thoughts, container,false);
        context = container.getContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context,
                R.layout.view_spinner_item,
                AllianceActivity.getTeams()
        );
        bestDefenseSpinner = (Spinner) root.findViewById(R.id.bestDefenseSpinner);
        defenseSpinner = (Spinner) root.findViewById(R.id.defenseSpinner);
        worstDefenseSpinner = (Spinner) root.findViewById(R.id.worstDefenseSpinner);
        offenseSpinner = (Spinner) root.findViewById(R.id.offenseSpinner);
        bestOffenseSpinner = (Spinner) root.findViewById(R.id.bestOffenseSpinner);
        worstOffenseSpinner = (Spinner) root.findViewById(R.id.worstOffenseSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defenseSpinner.setAdapter(adapter);
        bestDefenseSpinner.setAdapter(adapter);
        worstDefenseSpinner.setAdapter(adapter);
        offenseSpinner.setAdapter(adapter);
        bestOffenseSpinner.setAdapter(adapter);
        worstOffenseSpinner.setAdapter(adapter);
           defenseSpinner.setOnItemSelectedListener(this);
        bestDefenseSpinner.setOnItemSelectedListener(this);
        worstDefenseSpinner.setOnItemSelectedListener(this);
        offenseSpinner.setOnItemSelectedListener(this);
        bestOffenseSpinner.setOnItemSelectedListener(this);
        worstOffenseSpinner.setOnItemSelectedListener(this);
        defense = AllianceActivity.getDefense2();
        bestDefense = AllianceActivity.getDefense1();
        worstDefense = AllianceActivity.getDefense3();
        offense = AllianceActivity.getOffense2();
        bestOffense = AllianceActivity.getOffense1();
        worstOffense = AllianceActivity.getOffense3();
           defenseSpinner.setSelection(adapter.getPosition(defense));
        bestDefenseSpinner.setSelection(adapter.getPosition(bestDefense));
        worstDefenseSpinner.setSelection(adapter.getPosition(worstDefense));
        offenseSpinner.setSelection(adapter.getPosition(offense));
        bestOffenseSpinner.setSelection(adapter.getPosition(bestOffense));
        worstOffenseSpinner.setSelection(adapter.getPosition(worstOffense));
        return root;
    }
}