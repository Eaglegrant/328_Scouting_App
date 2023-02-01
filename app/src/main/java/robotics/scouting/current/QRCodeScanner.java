package robotics.scouting.current;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.io.File;

public class QRCodeScanner extends AppCompatActivity {
    MaterialButton scanBtn;
    MaterialButton openQRFolder;
    String imagesDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        scanBtn = (MaterialButton) findViewById(R.id.scanBtn);
        openQRFolder = (MaterialButton) findViewById(R.id.fabFolder2);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRCodeScanner.this, CameraScanner.class);
                startActivity(intent);
            }
        });
        openQRFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                Intent intent = new Intent(QRCodeScanner.this, FileListActivity.class);
                intent.putExtra("path",imagesDir);
                intent.putExtra("sendBack",false);
                startActivity(intent);
            }
        });
    }
}