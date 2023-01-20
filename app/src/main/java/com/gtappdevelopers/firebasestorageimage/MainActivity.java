package com.gtappdevelopers.firebasestorageimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.gtappdevelopers.firebasestorageimage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements childFab.OnFragmentInteractionListener{
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new BeforeMatchFragment());
        while (!checkPermission()) {
            requestPermission();
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.before:
                    replaceFragment(new BeforeMatchFragment());
                   break;
                case R.id.auto:
                    replaceFragment(new Auto());
                    break;
                case R.id.tele:
                    replaceFragment(new TeleOp());
                    break;
                case R.id.end:
                    replaceFragment(new EndGame());
                    break;
                case R.id.dock:
                    replaceFragment(new Docking());
                    break;
            }
            return true;
        });
        }
private void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out)
            .setReorderingAllowed(true)
            .replace(R.id.frameLayout, fragment,null)
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
    @Override
    public void messageFromChildFragment(Uri uri) {
        switch (uri.toString()){
            case "before":
                replaceFragment(new BeforeMatchFragment());
                break;
            case "auto":
                replaceFragment(new Auto());
                break;
            case "tele":
                replaceFragment(new TeleOp());
                break;
            case "end":
                replaceFragment(new EndGame());
                break;
            case "dock":
                replaceFragment(new Docking());
                break;
        }
    }
}