package com.gtappdevelopers.firebasestorageimage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link childFab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class childFab extends Fragment implements View.OnClickListener{

    public childFab() {
        // Required empty public constructor
    }

Context context;
    public static childFab newInstance() {
        childFab fragment = new childFab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_docking, container,false);
        context = container.getContext();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void messageFromChildFragment(Uri uri);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mListener.messageFromChildFragment(Uri.parse("tele"));
                break;
        }
    }
}