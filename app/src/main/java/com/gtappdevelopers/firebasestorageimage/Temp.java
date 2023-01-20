package com.gtappdevelopers.firebasestorageimage;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class Temp {
    /*
    BEFORE
        EditText intEdt1;
    EditText intEdt2;
            intEdt1 = findViewById(R.id.idInt1);
        intEdt2 = findViewById(R.id.idInt2);





        AUTO
             EditText dataEdt1;
    GridView gridView;
    ArrayList<Integer> gridQR = new ArrayList<Integer>();
    static final String[] numbers = new String[] {
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",
            "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜",};

    gridView = (GridView) findViewById(R.id.gridView1);
             dataEdt1 = findViewById(R.id.idEdt2);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, numbers);
        gridView.setHorizontalSpacing(2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            if (getBackgroundColor(v) == Color.parseColor("#FFFFFF")){
                v.setBackgroundColor(Color.parseColor("#FF0000"));
                gridQR.set(position, 0);

            }else{
                v.setBackgroundColor(Color.parseColor("#FFFFFF"));
                gridQR.set(position, 1);

            }
        }

    });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(intent);
            }
        });

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
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }










        END


        String imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
                generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((TextUtils.isEmpty(dataEdt1.getText().toString())) ||
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
                                + System.lineSeparator() +
                                dataEdt1.getText().toString() + System.lineSeparator()+ gridQR.toString() + System.lineSeparator() + Hang;
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
     */
}
