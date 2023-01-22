package com.gtappdevelopers.firebasestorageimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Auto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Auto extends Fragment implements View.OnClickListener{

    public Auto() {
        // Required empty public constructor
    }

    public static Auto newInstance() {
        Auto fragment = new Auto();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    Context context;
    GridView gridV;
    EditText dataEdt1;
    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                assert object != null;
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));

    }
    int[] images = {R.drawable.empty_cone};/*,R.drawable.empty_cube,R.drawable.empty_cone,R.drawable.empty_cone,R.drawable.empty_cube,R.drawable.empty_cone,R.drawable.empty_cone,R.drawable.empty_cube,R.drawable.empty_cone,
            R.drawable.empty_cone,R.drawable.empty_cube,R.drawable.empty_cone,R.drawable.empty_cone,R.drawable.empty_cube,R.drawable.empty_cone,R.drawable.empty_cone,R.drawable.empty_cube,R.drawable.empty_cone,
            R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid,R.drawable.empty_hybrid};
    */String[] names = {"Cone"};/*,"Cube","Cone","Cone","Cube","Cone","Cone","Cube","Cone","Cone","Cube","Cone","Cone","Cube","Cone","Cone","Cube","Cone","Hybrid","Hybrid","Hybrid","Hybrid","Hybrid","Hybrid","Hybrid","Hybrid","Hybrid"};
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_auto, container,false);
        context = container.getContext();
        dataEdt1 = root.findViewById(R.id.textComment);
        gridV = root.findViewById(R.id.gridView1);
        GridAdapter gridAdapter = new GridAdapter(names,images,context);
        gridV.setAdapter(gridAdapter);
        dataEdt1.setOnClickListener(this);
        dataEdt1.setText(MainActivity.getAutoC());
        return root;
    }

    @Override
    public void onStop() {
        MainActivity.setAutoC(dataEdt1.getText().toString());
        super.onStop();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textComment:
                dataEdt1.setText("");
                break;
        }
    }
}