package com.gtappdevelopers.firebasestorageimage;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.window.layout.WindowMetricsCalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Spinner;
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
import java.util.Objects;

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
    private void saveImage(Bitmap bitmap) throws IOException {
        boolean saved;
        OutputStream fos=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues contentValues =new  ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + intEdt2.getText().toString() + " Team " + intEdt1.getText().toString());
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

            File file =new File(imagesDir,  "M" + intEdt2.getText().toString() + " Team " + intEdt1.getText().toString()+".png");
            if (!file.exists()) {
                file.mkdir();
            }

            File image =new File(imagesDir,  "M" +".png");
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGenerate:
                qrgEncoder = new QRGEncoder(((MainActivity)getActivity()).getAllData(), null, QRGContents.Type.TEXT, ((MainActivity)getActivity()).getDimen());
    try {
        //getting our qrcode in the form of bitmap.
        bitmap = qrgEncoder.encodeAsBitmap();
        saveImage(bitmap);
        // the bitmap is set inside our image view using .setimagebitmap method.
    } catch (WriterException e) {
        //this method is called for exception handling.
        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(context, "Complete! When you're done entering QR's, hit Open QR Code Folder", Toast.LENGTH_LONG).show();

                break;
            case R.id.fabFolder:
                Intent intent = new Intent(context,FileListActivity.class);
                intent.putExtra("path",imagesDir);
                startActivity(intent);
                    break;
        }
    }
}