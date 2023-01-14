package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     EditText dataEdt1;
     EditText dataEdt2;
    EditText intEdt1;
    EditText intEdt2;
    Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String Hang = "Not On Bot";
    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;

        }
    }
    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this,"Storage permission is required, please allow it from settings.",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         Hang = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Hang = "Not On Bot";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing all variables.
        dataEdt1 = findViewById(R.id.idEdt1);
        intEdt1 = findViewById(R.id.idInt1);
        intEdt2 = findViewById(R.id.idInt2);
        dataEdt2 = findViewById(R.id.idEdt2);
        Spinner dockSpinner = (Spinner) findViewById(R.id.DockingSpinner);
        Spinner speedSpinner = (Spinner) findViewById(R.id.SpeedSpinner);
        Spinner defenseSpinner = (Spinner) findViewById(R.id.DefenseSpinner);

        generateQrBtn = findViewById(R.id.fabGenerate);
        MaterialButton openQRFolder  = findViewById(R.id.fabFolder);
        ArrayAdapter<String> DockAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.dockingOrder));
        DockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dockSpinner.setAdapter(DockAdapter);
        dockSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> SpeedAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.DefenseSpeed));
        SpeedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speedSpinner.setAdapter(SpeedAdapter);
        speedSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> DefenseAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.DefenseSpeed));
        DefenseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defenseSpinner.setAdapter(DefenseAdapter);
        defenseSpinner.setOnItemSelectedListener(this);
        //intializing onclick listner for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(dataEdt1.getText().toString()) || (TextUtils.isEmpty(dataEdt2.getText().toString())) ||
                        (TextUtils.isEmpty(intEdt1.getText().toString())) || (TextUtils.isEmpty(intEdt2.getText().toString()))){
                    //if the edittext inputs are empty then execute this method showing a toast message.
                    Toast.makeText(MainActivity.this, "Enter Values in Everything.", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkPermission()) {
                        //below line is for getting the windowmanager service.
                        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
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

                        String allDataWithLine = intEdt1.getText().toString() + System.lineSeparator() + intEdt2.getText().toString()
                                + System.lineSeparator() + dataEdt1.getText().toString() + System.lineSeparator() +
                                dataEdt2.getText().toString() + System.lineSeparator() + Hang;
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
                        Toast.makeText(MainActivity.this, "Complete! When you're done entering QR's, hit Open QR Code Folder", Toast.LENGTH_LONG).show();
                    }else {
                        requestPermission();
                    }

                }
            }
        });
        String imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
        openQRFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()){
                    Intent intent = new Intent(MainActivity.this,FileListActivity.class);
                    intent.putExtra("path",imagesDir);
                    startActivity(intent);
                }else{
                    requestPermission();
                }
            }
    });
    }

    private boolean saveImage(Bitmap bitmap) throws IOException {
        boolean saved;
        OutputStream fos=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues contentValues =new  ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + intEdt2.getText().toString() + " Team " + intEdt1.getText().toString());
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "QR");
            Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                fos = getContentResolver().openOutputStream(imageUri);
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

            File image =new File(imagesDir,  "M" + intEdt2.getText().toString() + " Team " + intEdt1.getText().toString()+".png");
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
}