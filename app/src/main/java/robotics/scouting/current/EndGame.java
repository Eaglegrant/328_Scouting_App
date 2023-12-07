package robotics.scouting.current;


import static com.android.volley.VolleyLog.TAG;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import androidmads.library.qrgenearator.QRGSaver;

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
    TextView reviewText;
    MaterialButton openQRFolder;
    QRGEncoder qrgEncoder;
    Context context;

    String reviewData;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();
    MaterialButton openDataView;
    String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
   /* private boolean saveImage(Bitmap bitmap) throws IOException {
        boolean saved;
        OutputStream fos=null;
        int teamNumber = MainActivity.getTeam();
        int matchNumber = MainActivity.getMatch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentValues contentValues =new  ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "M" + matchNumber + " Team " + teamNumber);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "QR");

            Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            try {
                fos = context.getContentResolver().openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {


            File file =new File(imagesDir);
            if (!file.exists()) {
                file.mkdir();
            }

                    File image =new File(imagesDir,  "M" + matchNumber + " Team " + teamNumber+".png");
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
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade_f));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right_f));
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
        reviewData = MainActivity.getAllDataChangeable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end_game, container,false);
        context = container.getContext();
       generateQrBtn = (MaterialButton) root.findViewById(R.id.fabGenerate);
        openQRFolder  = (MaterialButton) root.findViewById(R.id.fabFolder);
        reviewText = (TextView) root.findViewById(R.id.reviewText);
        openDataView = (MaterialButton) root.findViewById(R.id.fabRead);
        generateQrBtn.setOnClickListener(this);
        openQRFolder.setOnClickListener(this);
        openDataView.setOnClickListener(this);
        reviewText.setText(reviewData);

      //  gridView = gridView.findViewById(R.id.gridView1);
        return root;
    }
    String data;
    int dimen;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabGenerate:
                //DO NOT TOUCH THIS. IT WORKS. I SWEAR.
                Activity activity = getActivity();
                if (activity != null && activity instanceof MainActivity) {
                    data = MainActivity.getAllData();
                    dimen = MainActivity.getDimen();
                }

                qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
               // qrgEncoder.setColorBlack(Color.RED);
             //   qrgEncoder.setColorWhite(Color.BLUE);
                bitmap = qrgEncoder.getBitmap();
                QRGSaver qrgSaver = new QRGSaver();
                int teamNumber = MainActivity.getTeam();
                int matchNumber = MainActivity.getMatch();
                String text = "M" + matchNumber + " Team " + teamNumber;
                qrgSaver.save(imagesDir + File.separator, text,bitmap, QRGContents.ImageType.IMAGE_JPEG);
                Toast.makeText(context, "QR Generated Successfully", Toast.LENGTH_SHORT).show();
                MainActivity.clearData();
                break;
                //Okay you can touch stuff from here.
            case R.id.fabFolder:
                Intent intent = new Intent(context,FileListActivity.class);
                intent.putExtra("path",imagesDir);
                startActivity(intent);
                    break;
            case R.id.fabRead:
                Intent dataViewIntent = new Intent(context,QRCodeScanner.class);
                dataViewIntent.putExtra("location", true);
                startActivity(dataViewIntent);
                break;
        }
    }
}