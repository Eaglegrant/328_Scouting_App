package robotics.scouting.current;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class CameraScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int cam = Camera.CameraInfo.CAMERA_FACING_BACK; //New Version: CameraCharacteristics.LENS_FACING_BACK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
            if (checkPermission()) {
                //  Toast.makeText(getApplicationContext(),"Permission is granted",Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
      //  saveData("event\n1\ntest\nnan\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\n135\n328\n45\n328\n45\n135\n328\n135\n45\n150\n|\nevent\n1\n4\nnan\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\ntest\n|\nevent\n2\n236\nnan\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\ntest\n|\nevent\n1\ntest\nnan\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\ntest\n1 1 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0\n135\n328\n45\n328\n45\n135\n328\n135\n45\n120\n|");
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(CameraScanner.this,new String[] {android.Manifest.permission.CAMERA},REQUEST_CAMERA);
    }
    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResult){
        switch (requestCode){
            case REQUEST_CAMERA:
                if(grantResult.length > 0){
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                    REQUEST_CAMERA);
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(checkPermission()){
            if(scannerView == null){
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
        scannerView = null;
    }
    String allianceNum;
    int matchNumber;
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(CameraScanner.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
    private boolean saveImageT(Bitmap bitmap,String first, String second) throws IOException {
        boolean saved;
        OutputStream fos=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues contentValues =new  ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + first + " T " + second);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "QR");
            Uri imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            try {
                fos = getApplicationContext().getContentResolver().openOutputStream(imageUri);
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

            File image =new File(imagesDir,  "M" + matchNumber + " T " + allianceNum+".png");
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
    int dimen;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    String imagesDir;
    ContentValues contentValues;
    private void saveFileToExternalStorage(String content) {
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        if (csvFile.exists()){
            try {
                    FileWriter f = new FileWriter(csvFile.getPath(), true);
                    BufferedWriter b = new BufferedWriter(f);
                    b.write(content);
                    b.flush();
                    b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
                String header = "Event;Match;Team Or Alliance;Auto Comment;Auto Grid;Teleop Comment;Teleop Grid;Docking;Time To Dock\n";
                String total = header+content;
                writeTextData(csvFile, total);
        }
    }
    private void writeTextData(File file, String data) {
        try  {
            File f = new File(file.getPath());
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            if (!f.exists())
                f.createNewFile();
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean saveCSV(String dataRaw) throws IOException {
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR" + File.separator;
        String content = dataRaw.replace("\n", ";");
        content = content.replace(";|;", "\n");
        Log.d("CSV Data", content);
        saveFileToExternalStorage(content);
        return true;
    }
    private void writeDataAtOnce(String dataRaw)
    {
        try {
            saveCSV(dataRaw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveData(String dataRaw){
        dimen = AllianceActivity.getDimen();
        String[] splitData = dataRaw.split("\n");
        matchNumber = Integer.parseInt(splitData[1]);
        allianceNum = String.valueOf(splitData[2]);
       qrgEncoder = new QRGEncoder(dataRaw, null, QRGContents.Type.TEXT, dimen);
       try {
           // bitmap = qrgEncoder.encodeAsBitmap();
            saveImageT(bitmap, String.valueOf(matchNumber),allianceNum+" Group");
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDataAtOnce(dataRaw);
    }
    @Override
    public void handleResult(Result result) {
        final String rawResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
        builder.setPositiveButton("Scan Another?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(CameraScanner.this);
            }
        });
        builder.setNegativeButton("View Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    intent = new Intent(CameraScanner.this, GridActivity.class);
                    intent.putExtra("csv", contentValues);
                }
                startActivity(intent);
            }
        });
        saveData(rawResult);
        builder.setMessage(rawResult + "\nSCANNNED AND SAVED");
        AlertDialog alert1 = builder.create();
        alert1.show();
        }
    }