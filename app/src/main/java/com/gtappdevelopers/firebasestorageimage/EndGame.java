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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


    // TODO: Rename and change types and number of parameters
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
    GridView gridView;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();
    static final String[] numbers = new String[] {
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",};

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
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" );
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

        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        generateQrBtn.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
        fab.setOnClickListener(this);
      //  gridView = gridView.findViewById(R.id.gridView1);
        return root;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGenerate:
                Bundle result = new Bundle();
                String balance = result.getString("Balance");
                String name = result.getString("Name");
                //TODO: Add the balance and name to the QR code
                WindowManager manager = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
                //initializing a variable for default display.
                Display display = manager.getDefaultDisplay();
                //creating a variable for point which is to be displayed in QR Code.
                Point point = new Point();
                display.getSize(point);
                //getting width and height of a point
                int width = point.x;
                int height = point.y;
                //generating dimension from width and height.
                int dimen = width < height ? width : height;
                dimen = dimen * 3 / 4;
                //setting this dimensions inside our qr code encoder to generate our qr code.

                String allDataWithLine =  gridQR.toString();
                qrgEncoder = new QRGEncoder(allDataWithLine, null, QRGContents.Type.TEXT, dimen);
                try {
                    //getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    saveImage(bitmap);
                    // the bitmap is set inside our image view using .setimagebitmap method.
                } catch (WriterException e) {
                    //this method is called for exception handling.
                    Log.e("Tag", e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Complete! When you're done entering QR's, hit Open QR Code Folder", Toast.LENGTH_LONG).show();
                break;
                case R.id.fabFolder:
                    Intent intent = new Intent(context,FileListActivity.class);
                    intent.putExtra("path",imagesDir);
                    startActivity(intent);
                    break;
            case R.id.fab:
                intent = new Intent(context,SplashActivity.class);
                startActivity(intent);
                break;
        }
    }
}