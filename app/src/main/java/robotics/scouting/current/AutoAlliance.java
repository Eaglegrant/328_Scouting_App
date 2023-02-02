package robotics.scouting.current;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutoAlliance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoAlliance extends Fragment implements View.OnClickListener{

    public AutoAlliance() {
        // Required empty public constructor
    }

    public static AutoAlliance newInstance() {
        AutoAlliance fragment = new AutoAlliance();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    Context context;
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
    ImageView leftGrid1;
    ImageView leftGrid2;
    ImageView leftGrid3;
    ImageView leftGrid4;
    ImageView leftGrid5;
    ImageView leftGrid6;
    ImageView leftGrid7;
    ImageView leftGrid8;
    ImageView leftGrid9;
    ImageView leftGrid10;
    ImageView leftGrid11;
    ImageView leftGrid12;
    ImageView leftGrid13;
    ImageView leftGrid14;
    ImageView leftGrid15;
    ImageView leftGrid16;
    ImageView leftGrid17;
    ImageView leftGrid18;
    ImageView coopGrid1;
    ImageView coopGrid2;
    ImageView coopGrid3;
    ImageView coopGrid4;
    ImageView coopGrid5;
    ImageView coopGrid6;
    ImageView coopGrid7;
    ImageView coopGrid8;
    ImageView coopGrid9;
    ImageView coopGrid10;
    ImageView coopGrid11;
    ImageView coopGrid12;
    ImageView coopGrid13;
    ImageView coopGrid14;
    ImageView coopGrid15;
    ImageView coopGrid16;
    ImageView coopGrid17;
    ImageView coopGrid18;
    ImageView rightGrid1;
    ImageView rightGrid2;
    ImageView rightGrid3;
    ImageView rightGrid4;
    ImageView rightGrid5;
    ImageView rightGrid6;
    ImageView rightGrid7;
    ImageView rightGrid8;
    ImageView rightGrid9;
    ImageView rightGrid10;
    ImageView rightGrid11;
    ImageView rightGrid12;
    ImageView rightGrid13;
    ImageView rightGrid14;
    ImageView rightGrid15;
    ImageView rightGrid16;
    ImageView rightGrid17;
    ImageView rightGrid18;
    int Grid1_OG = R.drawable.tile000;
    int Grid2_OG = R.drawable.tile001;
    int Grid3_OG = R.drawable.tile002;
    int Grid7_OG = R.drawable.tile006;
    int Grid8_OG = R.drawable.tile007;
    int Grid9_OG = R.drawable.tile008;
    int Grid12_OG = R.drawable.tile011;
    int Grid13_OG = R.drawable.tile012;
    int Grid14_OG = R.drawable.tile013;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_auto_alliance, container,false);
        context = container.getContext();
        dataEdt1 = root.findViewById(R.id.textComment);
        dataEdt1.setOnClickListener(this);
        leftGrid1 = root.findViewById(R.id.imageView1);
        leftGrid2 = root.findViewById(R.id.imageView2);
        leftGrid3 = root.findViewById(R.id.imageView3);
        leftGrid4 = root.findViewById(R.id.imageView4);
        leftGrid5 = root.findViewById(R.id.imageView5);
        leftGrid6 = root.findViewById(R.id.imageView6);
        leftGrid7 = root.findViewById(R.id.imageView7);
        leftGrid8 = root.findViewById(R.id.imageView8);
        leftGrid9 = root.findViewById(R.id.imageView9);
        leftGrid10 = root.findViewById(R.id.imageView10);
        leftGrid11 = root.findViewById(R.id.imageView11);
        leftGrid12 = root.findViewById(R.id.imageView12);
        leftGrid13 = root.findViewById(R.id.imageView13);
        leftGrid14 = root.findViewById(R.id.imageView14);
        leftGrid15 = root.findViewById(R.id.imageView15);
        leftGrid16 = root.findViewById(R.id.imageView16);
        leftGrid17 = root.findViewById(R.id.imageView17);
        leftGrid18 = root.findViewById(R.id.imageView18);
        coopGrid1 = root.findViewById(R.id.CimageView1);
        coopGrid2 = root.findViewById(R.id.CimageView2);
        coopGrid3 = root.findViewById(R.id.CimageView3);
        coopGrid4 = root.findViewById(R.id.CimageView4);
        coopGrid5 = root.findViewById(R.id.CimageView5);
        coopGrid6 = root.findViewById(R.id.CimageView6);
        coopGrid7 = root.findViewById(R.id.CimageView7);
        coopGrid8 = root.findViewById(R.id.CimageView8);
        coopGrid9 = root.findViewById(R.id.CimageView9);
        coopGrid10 = root.findViewById(R.id.CimageView10);
        coopGrid11 = root.findViewById(R.id.CimageView11);
        coopGrid12 = root.findViewById(R.id.CimageView12);
        coopGrid13 = root.findViewById(R.id.CimageView13);
        coopGrid14 = root.findViewById(R.id.CimageView14);
        coopGrid15 = root.findViewById(R.id.CimageView15);
        coopGrid16 = root.findViewById(R.id.CimageView16);
        coopGrid17 = root.findViewById(R.id.CimageView17);
        coopGrid18 = root.findViewById(R.id.CimageView18);
        rightGrid1 = root.findViewById(R.id.RimageView1);
        rightGrid2 = root.findViewById(R.id.RimageView2);
        rightGrid3 = root.findViewById(R.id.RimageView3);
        rightGrid4 = root.findViewById(R.id.RimageView4);
        rightGrid5 = root.findViewById(R.id.RimageView5);
        rightGrid6 = root.findViewById(R.id.RimageView6);
        rightGrid7 = root.findViewById(R.id.RimageView7);
        rightGrid8 = root.findViewById(R.id.RimageView8);
        rightGrid9 = root.findViewById(R.id.RimageView9);
        rightGrid10 = root.findViewById(R.id.RimageView10);
        rightGrid11 = root.findViewById(R.id.RimageView11);
        rightGrid12 = root.findViewById(R.id.RimageView12);
        rightGrid13 = root.findViewById(R.id.RimageView13);
        rightGrid14 = root.findViewById(R.id.RimageView14);
        rightGrid15 = root.findViewById(R.id.RimageView15);
        rightGrid16 = root.findViewById(R.id.RimageView16);
        rightGrid17 = root.findViewById(R.id.RimageView17);
        rightGrid18 = root.findViewById(R.id.RimageView18);
        leftGrid1.setOnClickListener(this);
        leftGrid2.setOnClickListener(this);
        leftGrid3.setOnClickListener(this);
        leftGrid4.setOnClickListener(this);
        leftGrid5.setOnClickListener(this);
        leftGrid6.setOnClickListener(this);
        leftGrid7.setOnClickListener(this);
        leftGrid8.setOnClickListener(this);
        leftGrid9.setOnClickListener(this);
        leftGrid10.setOnClickListener(this);
        leftGrid11.setOnClickListener(this);
        leftGrid12.setOnClickListener(this);
        leftGrid13.setOnClickListener(this);
        leftGrid14.setOnClickListener(this);
        leftGrid15.setOnClickListener(this);
        leftGrid16.setOnClickListener(this);
        leftGrid17.setOnClickListener(this);
        leftGrid18.setOnClickListener(this);
        coopGrid1.setOnClickListener(this);
        coopGrid2.setOnClickListener(this);
        coopGrid3.setOnClickListener(this);
        coopGrid4.setOnClickListener(this);
        coopGrid5.setOnClickListener(this);
        coopGrid6.setOnClickListener(this);
        coopGrid7.setOnClickListener(this);
        coopGrid8.setOnClickListener(this);
        coopGrid9.setOnClickListener(this);
        coopGrid10.setOnClickListener(this);
        coopGrid11.setOnClickListener(this);
        coopGrid12.setOnClickListener(this);
        coopGrid13.setOnClickListener(this);
        coopGrid14.setOnClickListener(this);
        coopGrid15.setOnClickListener(this);
        coopGrid16.setOnClickListener(this);
        coopGrid17.setOnClickListener(this);
        coopGrid18.setOnClickListener(this);
        rightGrid1.setOnClickListener(this);
        rightGrid2.setOnClickListener(this);
        rightGrid3.setOnClickListener(this);
        rightGrid4.setOnClickListener(this);
        rightGrid5.setOnClickListener(this);
        rightGrid6.setOnClickListener(this);
        rightGrid7.setOnClickListener(this);
        rightGrid8.setOnClickListener(this);
        rightGrid9.setOnClickListener(this);
        rightGrid10.setOnClickListener(this);
        rightGrid11.setOnClickListener(this);
        rightGrid12.setOnClickListener(this);
        rightGrid13.setOnClickListener(this);
        rightGrid14.setOnClickListener(this);
        rightGrid15.setOnClickListener(this);
        rightGrid16.setOnClickListener(this);
        rightGrid17.setOnClickListener(this);
        rightGrid18.setOnClickListener(this);
        dataEdt1.setText(AllianceActivity.getAutoC());
        gridQR = AllianceActivity.getGrid();
        if (gridQR.contains(1)) {
            if (gridQR.get(0) == 1){
                leftGrid1.setImageResource(R.drawable.cone);
                leftGrid1.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(1) == 1){
                leftGrid2.setImageResource(R.drawable.cube);
                leftGrid2.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(2) == 1){
                leftGrid3.setImageResource(R.drawable.cone);
                leftGrid3.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(3) == 1){
                leftGrid7.setImageResource(R.drawable.cone);
                leftGrid7.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(4) == 1){
                leftGrid8.setImageResource(R.drawable.cube);
                leftGrid8.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(5) == 1){
                leftGrid9.setImageResource(R.drawable.cone);
                leftGrid9.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(6) == 1){
                leftGrid12.setImageResource(R.drawable.cone);
                leftGrid12.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(7) == 1){
                leftGrid13.setImageResource(R.drawable.cube);
                leftGrid13.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(8) == 1){
                leftGrid14.setImageResource(R.drawable.cone);
                leftGrid14.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(9) == 1){
                coopGrid1.setImageResource(R.drawable.cone);
                coopGrid1.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(10) == 1){
                coopGrid2.setImageResource(R.drawable.cube);
                coopGrid2.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(11) == 1){
                coopGrid3.setImageResource(R.drawable.cone);
                coopGrid3.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(12) == 1){
                coopGrid7.setImageResource(R.drawable.cone);
                coopGrid7.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(13) == 1){
                coopGrid8.setImageResource(R.drawable.cube);
                coopGrid8.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(14) == 1){
                coopGrid9.setImageResource(R.drawable.cone);
                coopGrid9.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(15) == 1){
                coopGrid12.setImageResource(R.drawable.cone);
                coopGrid12.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(16) == 1){
                coopGrid13.setImageResource(R.drawable.cube);
                coopGrid13.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(17) == 1){
                coopGrid14.setImageResource(R.drawable.cone);
                coopGrid14.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(18) == 1){
                rightGrid1.setImageResource(R.drawable.cone);
                rightGrid1.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(19) == 1){
                rightGrid2.setImageResource(R.drawable.cube);
                rightGrid2.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(20) == 1){
                rightGrid3.setImageResource(R.drawable.cone);
                rightGrid3.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(21) == 1){
                rightGrid7.setImageResource(R.drawable.cone);
                rightGrid7.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(22) == 1){
                rightGrid8.setImageResource(R.drawable.cube);
                rightGrid8.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(23) == 1){
                rightGrid9.setImageResource(R.drawable.cone);
                rightGrid9.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(24) == 1){
                rightGrid12.setImageResource(R.drawable.cone);
                rightGrid12.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(25) == 1){
                rightGrid13.setImageResource(R.drawable.cube);
                rightGrid13.setBackgroundColor(Color.RED);
            }
            if (gridQR.get(26) == 1){
                rightGrid14.setImageResource(R.drawable.cone);
                rightGrid14.setBackgroundColor(Color.RED);
            }

        }
        return root;
    }

    @Override
    public void onStop() {
        AllianceActivity.setAutoC(dataEdt1.getText().toString());
        AllianceActivity.setGrid(gridQR);
        super.onStop();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textComment){
            dataEdt1.setText("");
        }else{
        ImageView image = (ImageView) v;
        switch (v.getId()) {
            case R.id.imageView1:
            case R.id.CimageView1:
            case R.id.RimageView1:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid1_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(0, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(9, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(18, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(0, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(9, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(18, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView2:
            case R.id.CimageView2:
            case R.id.RimageView2:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid2_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(1, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(10, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(19, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cube);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(1, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(10, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(19, 1);
                            break;
                    }

                }
                break;
            case R.id.imageView3:
            case R.id.CimageView3:
            case R.id.RimageView3:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid3_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(2, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(11, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(20, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(2, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(11, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(20, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView7:
            case R.id.CimageView7:
            case R.id.RimageView7:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid7_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(3, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(12, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(21, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(3, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(12, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(21, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView8:
            case R.id.CimageView8:
            case R.id.RimageView8:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid8_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(4, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(13, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(22, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cube);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(4, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(13, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(22, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView9:
            case R.id.CimageView9:
            case R.id.RimageView9:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid9_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(5, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(14, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(23, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(5, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(14, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(23, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView12:
            case R.id.CimageView12:
            case R.id.RimageView12:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid12_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(6, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(15, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(24, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(6, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(15, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(24, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView13:
            case R.id.CimageView13:
            case R.id.RimageView13:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid13_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(7, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(16, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(25, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cube);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(7, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(16, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(25, 1);
                            break;
                    }
                }
                break;
            case R.id.imageView14:
            case R.id.CimageView14:
            case R.id.RimageView14:
                if (getBackgroundColor(image) == Color.RED) {
                    image.setImageResource(Grid14_OG);
                    image.setBackgroundColor(Color.WHITE);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(8, 0);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(17, 0);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(26, 0);
                            break;
                    }
                } else {
                    image.setImageResource(R.drawable.cone);
                    image.setBackgroundColor(Color.RED);
                    switch (((View) v.getParent()).getId()) {
                        case R.id.leftGrid:
                            gridQR.set(8, 1);
                            break;
                        case R.id.coopGrid:
                            gridQR.set(17, 1);
                            break;
                        case R.id.rightGrid:
                            gridQR.set(26, 1);
                            break;
                    }
                }
                break;
        }
        }
    }
}
