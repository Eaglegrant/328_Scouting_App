package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gtappdevelopers.firebasestorageimage.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    static String balance = "NA";
    static int match = -1;
    static int team = -1;
    static String autoC = "NA";
    public static int dimen;
    static ArrayList<Integer> grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new BeforeMatchFragment(),"before");
        fab = findViewById(R.id.fab);



        //TODO: Fix this. It's not working, specifically when dimen is called.
        WindowManager manager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        //initializing a variable for default display.
        Display display = manager.getDefaultDisplay();
        //creating a variable for point which is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);
        //getting width and height of a point
        int width = point.x;
        int height = point.y;
        //generating dimension from width and height.
        dimen = Math.min(width, height);
        dimen = dimen * 3 / 4;


        fab.setOnClickListener(this);
        while (!checkPermission()) {
            requestPermission();
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.before:
                    replaceFragment(new BeforeMatchFragment(),"before");
                    break;
                case R.id.auto:
                    replaceFragment(new Auto(),"auto");
                    break;
                case R.id.tele:
                    replaceFragment(new TeleOp(),"tele");
                    break;
                case R.id.end:
                    replaceFragment(new EndGame(),"end");
                    break;
                case R.id.dock:
                    replaceFragment(new Docking(),"docking");
                    break;
            }
            return true;
        });

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:
                BeforeMatchFragment beforeFrag = (BeforeMatchFragment)getSupportFragmentManager().findFragmentByTag("before");
                EndGame endFrag = (EndGame)getSupportFragmentManager().findFragmentByTag("end");
                Auto autoFrag = (Auto)getSupportFragmentManager().findFragmentByTag("auto");
                TeleOp teleFrag = (TeleOp)getSupportFragmentManager().findFragmentByTag("tele");
                Docking dockFrag = (Docking)getSupportFragmentManager().findFragmentByTag("docking");

                if (beforeFrag != null && beforeFrag.isVisible()) {
                    Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(intent);
                }else if (autoFrag != null && autoFrag.isVisible()) {
                    replaceFragment(new BeforeMatchFragment(), "before");
                }else if (teleFrag != null && teleFrag.isVisible()) {
                    replaceFragment(new Auto(), "auto");
                }else if (endFrag != null && endFrag.isVisible()) {
                    replaceFragment(new Docking(), "docking");
                }else if (dockFrag != null && dockFrag.isVisible()) {
                    replaceFragment(new TeleOp(), "tele");
                }
                break;
        }
    }

    private void replaceFragment(Fragment fragment,String title) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out)
            .setReorderingAllowed(true)
            .replace(R.id.frameLayout, fragment,title)
            .addToBackStack(null);
    fragmentTransaction.commit();
    }
    public boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;

        }
    }
    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this,"Storage permission is required, please allow it from settings.",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
        }
    }
    public void setBalance(String balance) {
        MainActivity.balance = balance;
    }
    public void setMatch(int match) {
        MainActivity.match = match;
    }
    public void setTeam(int team) {
        MainActivity.team = team;
    }
    public void setAutoC(String autoC) {
        MainActivity.autoC = autoC;
    }
    public void setGrid( ArrayList<Integer> grid) {
        MainActivity.grid = grid;
    }
    public static int getDimen() {
        return dimen;
    }
    public static int getMatch() {
        return match;
    }
    public static int getTeam() {
        return team;
    }
    public static String getAllData(){
        return String.valueOf(match) + "\n" + String.valueOf(team) + "\n" + autoC + "\n" + "NA" + "\n" + balance;
    }
}