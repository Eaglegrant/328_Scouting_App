package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gtappdevelopers.firebasestorageimage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new BeforeMatchFragment());
        fab = findViewById(R.id.fab);
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
        EndGame myFragment = (EndGame) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
        switch (view.getId()) {
            case R.id.fab:
                if (EndGame.newInstance().isAdded()){
                    replaceFragment(new TeleOp(),"tele");
                } else if (Docking.newInstance().isAdded()){
                    replaceFragment(new EndGame(),"end");
                }else if (TeleOp.newInstance().isAdded()){
                    replaceFragment(new Auto(),"auto");
                }else if (Auto.newInstance().isAdded()){
                    replaceFragment(new BeforeMatchFragment(),"before");
                }else if (BeforeMatchFragment.newInstance().isAdded()){
                    Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"error.",Toast.LENGTH_SHORT).show();

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
}