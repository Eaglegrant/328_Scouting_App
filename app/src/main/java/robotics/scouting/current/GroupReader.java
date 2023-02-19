package robotics.scouting.current;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class GroupReader extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int cam = Camera.CameraInfo.CAMERA_FACING_BACK; //New Version: CameraCharacteristics.LENS_FACING_BACK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion >= Build.VERSION_CODES.M){
            if(checkPermission()){
                //  Toast.makeText(getApplicationContext(),"Permission is granted",Toast.LENGTH_LONG).show();
            }else{
                requestPermission();
            }
        }
       // saveData("Penn\n1\n135\nFast\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nSlow\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nFirst\n10.00");
    //    saveData("Penn\n1\n328\nSlow\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nSlow\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nThird\n20.00");
   //     saveData("Penn\n1\n45\nMedium\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nSlow\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\nSecond\n15.00");
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(GroupReader.this,new String[] {android.Manifest.permission.CAMERA},REQUEST_CAMERA);
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
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
        new AlertDialog.Builder(GroupReader.this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
    private boolean saveImage(Bitmap bitmap,String first, String second) throws IOException {
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
        Toast.makeText(this, "Group QR Generated", Toast.LENGTH_SHORT).show();
        return saved;
    }
    int dimen;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    List<String> groupList = new ArrayList<>();
    int index = 0;
    private void groupQR(List<String> listOfLists){
        String data = listOfLists.toString();
        String formattedData = null;
        String pattern = "(?<!\\b(?:1|0)\\b),\\s"; // If any , is followed by a space, and NOT preceded by 1 or 0, replace with a new line
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(data);
        if (m.find( )) {
            formattedData = m.replaceAll("\n");
        }
        String testString = formattedData;
        testString = testString.substring(1,testString.length()-1); //removes { and } from the string.
        testString = testString.replace("[","");
        testString = testString.replace("]","");
        SharedPreferences sh = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String allianceData = sh.getString("alliance", "na");
        if (!allianceData.equals("na")){
            testString +="\n";
            allianceData = allianceData.replace("[","");
            allianceData = allianceData.replace("]","");
            testString += allianceData;

        }
        testString = testString.replaceAll(",\\s"," ");
        Log.d("GROUP", "groupQR: "+testString);
        qrgEncoder = new QRGEncoder(testString, null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            saveImage(bitmap, String.valueOf(matchNumber),allianceNum+" Group");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
    private void groupCheck(String data){
        groupList.add(data+"\n|");
        index++;
        if (index == 3){
            groupQR(groupList);
            groupList.clear();
            index = 0;
        }
    }
    private void saveData(String dataRaw){
        dimen = AllianceActivity.getDimen();
        String[] splitData = dataRaw.split("\n");
        matchNumber = Integer.parseInt(splitData[1]);
        allianceNum = splitData[2];
        qrgEncoder = new QRGEncoder(dataRaw, null, QRGContents.Type.TEXT, dimen);
        Log.d("ROBOT", "soloQR: "+dataRaw);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            saveImage(bitmap, String.valueOf(matchNumber),allianceNum);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        groupCheck(dataRaw);
    }
    @Override
    public void handleResult(Result result) {
        final String rawResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("Scan Next Robot QR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(GroupReader.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDestroy();
            }
        });
        saveData(rawResult);
        builder.setMessage(rawResult + "\nSCANNNED AND SAVED, SCAN NEXT ROBOT QR");
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}