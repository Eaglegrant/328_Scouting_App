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
    MaterialButton scanBtn;
    MaterialButton scanBtnAlliance;
    MaterialButton openQRFolder;
    MaterialButton GridButton;
    MaterialButton groupReader;
    MaterialButton resetData;
    FloatingActionButton fab;
    String imagesDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        scanBtn = (MaterialButton) findViewById(R.id.scanBtnSuper);
        openQRFolder = (MaterialButton) findViewById(R.id.fabFolder);
        scanBtnAlliance = (MaterialButton) findViewById(R.id.scanBtnAlliance);
        GridButton = (MaterialButton) findViewById(R.id.GridButton);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        groupReader = (MaterialButton) findViewById(R.id.groupReader);
        resetData = (MaterialButton) findViewById(R.id.resetData);
        scanBtn.setOnClickListener(this);
        scanBtnAlliance.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
        GridButton.setOnClickListener(this);
        groupReader.setOnClickListener(this);
        fab.setOnClickListener(this);
        resetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scanBtnSuper:
                Intent intent = new Intent(QRCodeScanner.this, CameraScanner.class);
                startActivity(intent);
                break;
            case R.id.resetData:
                File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
                if (csvFile.exists()){
                    Boolean succeeded = csvFile.delete();
                    if (succeeded){
                        Toast.makeText(this, "Data Reset", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Data Reset Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
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
            case R.id.GridButton:
                Intent intent3 = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    intent3 = new Intent(QRCodeScanner.this, GridActivity.class);
                }
                startActivity(intent3);
                break;
            case R.id.groupReader:
                Intent intent4 = new Intent(QRCodeScanner.this, GroupReader.class);
                startActivity(intent4);
                break;
            case R.id.fab:
                Intent intent5 = new Intent(QRCodeScanner.this, AllianceActivity.class);
                intent5.putExtra("local", true);
                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                intent5.putExtra("path", imagesDir);
                startActivity(intent5);
                break;
        }
    }
}