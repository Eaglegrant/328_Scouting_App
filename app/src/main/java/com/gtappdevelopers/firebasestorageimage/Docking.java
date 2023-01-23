package com.gtappdevelopers.firebasestorageimage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Docking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docking extends Fragment implements AdapterView.OnItemSelectedListener {
    public Docking() {
        // Required empty public constructor
    }
    public static Docking newInstance() {
        Docking fragment = new Docking();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    String balance = "Not Balanced";
    Spinner dockSpinner;
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
        balance = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        balance = "Not Balanced";
    }
    @Override
    public void onStop() {
        MainActivity.setBalance(balance);
        super.onStop();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_docking, container,false);
        context = container.getContext();
        dockSpinner = (Spinner) root.findViewById(R.id.DockingSpinner);
            ArrayAdapter<String> DockAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.dockingOrder));
          DockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           dockSpinner.setAdapter(DockAdapter);
           dockSpinner.setOnItemSelectedListener(this);
           balance = MainActivity.getBalance();
           dockSpinner.setSelection(DockAdapter.getPosition(balance));
        return root;
    }
}