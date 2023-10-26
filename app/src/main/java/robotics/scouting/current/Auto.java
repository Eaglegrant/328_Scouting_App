package robotics.scouting.current;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    EditText dataEdt1;
    Button highCube;
    Button midCube;
    Button lowCube;
    Button highCone;
    Button midCone;
    Button lowCone;
    Button miss;
    Button downed;
    Button undo;
    int highConeCount;
    int midConeCount;
    int lowConeCount;
    int highCubeCount;
    int midCubeCount;
    int lowCubeCount;
    int missCount;
    boolean downedBool;
    int undoValue;



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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_auto, container,false);
        context = container.getContext();
        highCube = root.findViewById(R.id.HighCubeButton);
        midCube = root.findViewById(R.id.MidCubeButton);
        lowCube = root.findViewById(R.id.LowCubeButton);
        highCone = root.findViewById(R.id.HighConeButton);
        midCone = root.findViewById(R.id.MidConeButton);
        lowCone = root.findViewById(R.id.LowConeButton);
        miss = root.findViewById(R.id.MissButton);
        downed = root.findViewById(R.id.DownedButton);
        undo = root.findViewById(R.id.UndoButton);
        highCube.setOnClickListener(this);
        midCube.setOnClickListener(this);
        lowCube.setOnClickListener(this);
        highCone.setOnClickListener(this);
        midCone.setOnClickListener(this);
        lowCone.setOnClickListener(this);
        miss.setOnClickListener(this);
        downed.setOnClickListener(this);
        undo.setOnClickListener(this);
        return root;
    }

    @Override
    public void onStop() {
     //   MainActivity.setAutoC(dataEdt1.getText().toString());
       // MainActivity.setGrid(gridQR);
        super.onStop();
    }
    private int updater(int value,Button button,String name){
        //Ups the value of button pressed by one
        value +=1;
        String tempText = name+String.valueOf(value);
        button.setText(tempText);
        return value;
    }

    private int updaterMinus(int value,Button button,String name){
        //brings down the value of button pressed by one
        if (value > 0){
        value -=1;
        }
        String tempText = name+String.valueOf(value);
        button.setText(tempText);
        return value;
    }
    //Undo value correspond to what button was last presses, i couldn't figure out how to make it text
    //Undo value works like this: High cone = 1, Mid cone = 2, Low cone = 3, High cube = 4, Mid cube = 5, Low cube = 6, and Miss = 7
    @Override
    public void onClick(View v) {
        MaterialButton image = (MaterialButton) v;
        switch (v.getId()) {
            case R.id.HighConeButton:
                highConeCount = updater(highConeCount, highCone, "High Cone: ");
                undoValue = 1;
                break;
            case R.id.MidConeButton:
                midConeCount = updater(midConeCount, midCone, "Mid Cone: ");
                undoValue = 2;
                break;
            case R.id.LowConeButton:
                ConeButton:
                lowConeCount = updater(lowConeCount, lowCone, "Low Cone: ");
                undoValue = 3;
                break;
            case R.id.HighCubeButton:
                highCubeCount = updater(highCubeCount, highCube, "High Cube: ");
                undoValue = 4;
                break;
            case R.id.MidCubeButton:
                midCubeCount = updater(midCubeCount, midCube, "Mid Cube: ");
                undoValue = 5;
                break;
            case R.id.LowCubeButton:
                lowCubeCount = updater(lowCubeCount, lowCube, "Low Cube: ");
                undoValue = 6;
                break;
            case R.id.MissButton:
                missCount = updater(missCount, miss, "Miss: ");
                undoValue = 7;
                break;
            case R.id.UndoButton:
                if (undoValue == 1){
                    highConeCount = updaterMinus(highConeCount, highCone, "High Cone: ");
                } else if (undoValue == 2) {
                    midConeCount = updaterMinus(midConeCount, midCone, "Mid Cone: ");
                } else if (undoValue == 3) {
                    lowConeCount = updaterMinus(lowConeCount, lowCone, "Low Cone: ");
                } else if (undoValue == 4) {
                    highCubeCount = updaterMinus(highCubeCount, highCube, "High Cube: ");
                } else if (undoValue == 5) {
                    midCubeCount = updaterMinus(midCubeCount, midCube, "Mid Cube: ");
                } else if (undoValue == 6) {
                    lowCubeCount = updaterMinus(lowCubeCount, lowCube, "Low Cube: ");
                } else if (undoValue == 7) {
                    missCount = updaterMinus(missCount, miss, "Miss: ");
                }
                break;
            case R.id.DownedButton:
                downedBool = !downedBool;
                if (downedBool) {
                    downed.setText("Working?");
                    downed.setBackgroundColor(Color.RED);
                } else {
                    downed.setText("Downed?");
                    downed.setBackgroundColor(Color.rgb(15,157,88));
                }
        }
    }
}
