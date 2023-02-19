package robotics.scouting.current;


import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGameAlliance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGameAlliance extends Fragment implements View.OnClickListener {

    public EndGameAlliance() {
        // Required empty public constructor
    }


    public static EndGameAlliance newInstance() {
        EndGameAlliance fragment = new EndGameAlliance();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    MaterialButton generateQrBtn;
    MaterialButton readQRBtn;
    Bitmap bitmap;
    MaterialButton openQRFolder;
    FloatingActionButton fab;
    QRGEncoder qrgEncoder;
    Context context;
    String imagesDir;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();
    private boolean saveImage(Bitmap bitmap) throws IOException {
        boolean saved;
        OutputStream fos=null;
        String alliance = AllianceActivity.getAlliance();
        int matchNumber = AllianceActivity.getMatch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues contentValues =new  ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + matchNumber + " Team " + alliance);
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

            File image =new File(imagesDir,  "M" + matchNumber + " Team " + alliance+".png");
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
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end_game_alliance, container,false);
        context = container.getContext();
       generateQrBtn = (MaterialButton) root.findViewById(R.id.fabGenerate);
        openQRFolder  = (MaterialButton) root.findViewById(R.id.fabFolder);
        readQRBtn = (MaterialButton) root.findViewById(R.id.fabRead);
        generateQrBtn.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
        readQRBtn.setOnClickListener(this);
      //  gridView = gridView.findViewById(R.id.gridView1);
        return root;
    }
    String data;
    int dimen;
    String finalDataAdd;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGenerate:
                //DO NOT TOUCH THIS. IT WORKS. I SWEAR.
                Activity activity = getActivity();
                if (activity != null && activity instanceof AllianceActivity) {
                    data = AllianceActivity.getAllData();
                    dimen = AllianceActivity.getDimen();
                }
                qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    saveImage(bitmap);
                    Toast.makeText(context, "QR Generated Successfully", Toast.LENGTH_SHORT).show();
                } catch (WriterException | IOException e) {
                    e.printStackTrace();
                }
                finalDataAdd = data;
                if (finalDataAdd != null) {
                    SharedPreferences sh = context.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor shEditor = sh.edit();
                    shEditor.putString("alliance", finalDataAdd);
                    shEditor.apply();
                    finalDataAdd = null;
                }
                AllianceActivity.clearData();
                break;
                //Okay you can touch stuff from here.
            case R.id.fabFolder:
                Intent intent = new Intent(context,FileListActivity.class);
                intent.putExtra("path",imagesDir);
                intent.putExtra("sendBack",false);
                startActivity(intent);
                    break;
            case R.id.fabRead:
                Intent intent1 = new Intent(context,QRCodeScanner.class);
                /*if (finalDataAdd != null) {
                    intent1.putExtra("alliance",finalDataAdd);
                }
                */
                startActivity(intent1);
                break;
        }
    }
}