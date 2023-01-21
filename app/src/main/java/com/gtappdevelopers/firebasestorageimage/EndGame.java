package com.gtappdevelopers.firebasestorageimage;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGame extends Fragment implements View.OnClickListener {

    public EndGame() {
        // Required empty public constructor
    }


    public static EndGame newInstance() {
        EndGame fragment = new EndGame();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    MaterialButton generateQrBtn;
    Bitmap bitmap;
    MaterialButton openQRFolder;
    FloatingActionButton fab;
    QRGEncoder qrgEncoder;
    Context context;
    String imagesDir;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();
    EditText intEdt1;
    EditText intEdt2;
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
    private boolean saveImage(Bitmap bitmap) throws IOException {
        boolean saved;
        OutputStream fos=null;
        int teamNumber = MainActivity.getTeam();
        int matchNumber = MainActivity.getMatch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues contentValues =new  ContentValues();
            Toast.makeText(context, "QR Generated Successfully1", Toast.LENGTH_SHORT).show();

            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + matchNumber + " Team " + teamNumber);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "QR");

            Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            try {
                fos = context.getContentResolver().openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";

            File file =new File(imagesDir);
            if (!file.exists()) {
                file.mkdir();
            }

            File image =new File(imagesDir,  "M" + matchNumber + " Team " + teamNumber+".png");
            try {
                fos =new FileOutputStream(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        assert fos != null;
        fos.flush();
        fos.close();
        return saved;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
        //initializing all variables.
        //intializing onclick listner for button.
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end_game, container,false);
        context = container.getContext();
       generateQrBtn = (MaterialButton) root.findViewById(R.id.fabGenerate);
        openQRFolder  = (MaterialButton) root.findViewById(R.id.fabFolder);
        generateQrBtn.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
      //  gridView = gridView.findViewById(R.id.gridView1);
        return root;
    }
    String data;
    int dimen;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGenerate:
                Activity activity = getActivity();
                if (activity != null && activity instanceof MainActivity) {
                    data = MainActivity.getAllData(); //error here
                    dimen = MainActivity.getDimen();
                }

                qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    saveImage(bitmap);
                    Toast.makeText(context, "QR Generated Successfully", Toast.LENGTH_SHORT).show();
                } catch (WriterException | IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.fabFolder:
                Intent intent = new Intent(context,FileListActivity.class);
                intent.putExtra("path",imagesDir);
                startActivity(intent);
                    break;
        }
    }
}