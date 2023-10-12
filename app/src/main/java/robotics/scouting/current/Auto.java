package robotics.scouting.current;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

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
    int highConeCount;
    int midConeCount;
    int lowConeCount;
    int highCubeCount;
    int midCubeCount;
    int lowCubeCount;
    int missCount;
    boolean downedBool;
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
        highCube.setOnClickListener(this);
        midCube.setOnClickListener(this);
        lowCube.setOnClickListener(this);
        highCone.setOnClickListener(this);
        midCone.setOnClickListener(this);
        lowCone.setOnClickListener(this);
        miss.setOnClickListener(this);
        downed.setOnClickListener(this);
        return root;
    }

    @Override
    public void onStop() {
     //   MainActivity.setAutoC(dataEdt1.getText().toString());
       // MainActivity.setGrid(gridQR);
        super.onStop();
    }
    private int updater(int value,Button button,String name){
        value +=1;
        String tempText = name+String.valueOf(value);
        button.setText(tempText);
        return value;
    }
    @Override
    public void onClick(View v) {
        MaterialButton image = (MaterialButton) v;
        switch (v.getId()) {
            case R.id.HighConeButton:
                highConeCount = updater(highConeCount, highCone, "High Cone: ");
                break;
            case R.id.MidConeButton:
                midConeCount = updater(midConeCount, midCone, "Mid Cone: ");
                break;
            case R.id.LowConeButton:
                ConeButton:
                lowConeCount = updater(lowConeCount, lowCone, "Low Cone: ");
                break;
            case R.id.HighCubeButton:
                highCubeCount = updater(highCubeCount, highCube, "High Cube: ");
                break;
            case R.id.MidCubeButton:
                midCubeCount = updater(midCubeCount, midCube, "Mid Cube: ");
                break;
            case R.id.LowCubeButton:
                lowCubeCount = updater(lowCubeCount, lowCube, "Low Cube: ");
                break;
            case R.id.MissButton:
                missCount = updater(missCount, miss, "Miss: ");
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
