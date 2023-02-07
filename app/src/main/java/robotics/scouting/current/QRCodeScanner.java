package robotics.scouting.current;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QRCodeScanner extends AppCompatActivity implements  View.OnClickListener{
    MaterialButton scanBtn;
    MaterialButton scanBtnAlliance;
    MaterialButton openQRFolder1;
    MaterialButton openQRFolder2;
    MaterialButton GridButton;
    FloatingActionButton fab;
    String imagesDir;
        private boolean saveImage(String dataRaw) throws IOException {
            imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
            try {
                String content = dataRaw.replace("\n", ";");
                File file = new File(imagesDir+"Test" +".csv");
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile(); //This crashes, due to some Android Q Bug. I'm not sure how to fix it. I'm not sure if it's a bug in the app or in Android Q.
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        scanBtn = (MaterialButton) findViewById(R.id.scanBtnSuper);
        openQRFolder1 = (MaterialButton) findViewById(R.id.fabFolder2);
        openQRFolder2 = (MaterialButton) findViewById(R.id.fabFolder1);
        scanBtnAlliance = (MaterialButton) findViewById(R.id.scanBtnAlliance);
        GridButton = (MaterialButton) findViewById(R.id.GridButton);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        scanBtn.setOnClickListener(this);
        scanBtnAlliance.setOnClickListener(this);
        openQRFolder1.setOnClickListener(this);
        openQRFolder2.setOnClickListener(this);
        GridButton.setOnClickListener(this);
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtnSuper:
                try {
                    saveImage("test\nNice");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //Intent intent = new Intent(QRCodeScanner.this, CameraScanner.class);
                //startActivity(intent);
                break;
            case R.id.scanBtnAlliance:
                Intent intent2 = new Intent(QRCodeScanner.this, CameraScannerAlliance.class);
                startActivity(intent2);
                break;
            case R.id.fabFolder2:
            case R.id.fabFolder1:
                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                Intent intent1 = new Intent(QRCodeScanner.this, FileListActivity.class);
                intent1.putExtra("path", imagesDir);
                intent1.putExtra("sendBack", false);
                startActivity(intent1);
                break;
            case R.id.GridButton:
                Intent intent3 = new Intent(QRCodeScanner.this, GridActivity.class);
                startActivity(intent3);
                break;
            case R.id.fab:
                Intent intent4 = new Intent(QRCodeScanner.this, AllianceActivity.class);
                intent4.putExtra("local",true);
                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                intent4.putExtra("path", imagesDir);
                startActivity(intent4);
                break;
        }
    }
}