package robotics.scouting.current;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;
import com.opencsv.CSVWriter;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeScanner extends AppCompatActivity implements  View.OnClickListener {
    MaterialButton scanBtnAlliance;
    MaterialButton openQRFolder;
    MaterialButton GridButton;
    FloatingActionButton fab;
    String imagesDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        openQRFolder = (MaterialButton) findViewById(R.id.fabFolder);
        scanBtnAlliance = (MaterialButton) findViewById(R.id.scanBtnAlliance);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        scanBtnAlliance.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtnAlliance:
                Intent intent2 = new Intent(QRCodeScanner.this, CameraScannerAlliance.class);
                startActivity(intent2);
                break;
            case R.id.fabFolder:
                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                Intent intent1 = new Intent(QRCodeScanner.this, FileListActivity.class);
                intent1.putExtra("path", imagesDir);
                intent1.putExtra("sendBack", false);
                startActivity(intent1);
                break;
            case R.id.fab:
                if (getIntent().getBooleanExtra("location",false)){
                    Intent intent5 = new Intent(QRCodeScanner.this, MainActivity.class);
                    intent5.putExtra("local", true);
                    imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                    intent5.putExtra("path", imagesDir);
                    startActivity(intent5);
            }else{
                    Intent intent5 = new Intent(QRCodeScanner.this, AllianceActivity.class);
                    intent5.putExtra("local", true);
                    imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                    intent5.putExtra("path", imagesDir);
                    startActivity(intent5);
                }

                break;
        }
    }
}