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
    private OnFragmentInteractionListener mListener;


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
    FloatingActionButton fab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        balance = parent.getItemAtPosition(pos).toString();
        Bundle balanceB = new Bundle();
        balanceB.putString("Balance", balance);
        getParentFragmentManager().setFragmentResult("Balance", balanceB);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        balance = "Not Balanced";
        Bundle balanceB = new Bundle();
        balanceB.putString("Balance", balance);
        getParentFragmentManager().setFragmentResult("Balance", balanceB);
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

        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new childFab();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void messageFromParentFragment(Uri uri);
    }
}