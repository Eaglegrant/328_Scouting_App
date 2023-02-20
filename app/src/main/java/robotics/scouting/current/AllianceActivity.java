package robotics.scouting.current;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

import robotics.scouting.current.databinding.ActivityAllianceBinding;
public class AllianceActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ActivityAllianceBinding binding;
    FloatingActionButton fab;
    FloatingActionButton fab2;
    static String balance = "NA";
    static int match = -1;
    static String autoC = "NA";
    static String teleC = "NA";
    public static int dimen;
    static int mins =0;
    static int secs =0;
    static int millis =0;
    static String time="0.00";
    static ArrayList<Integer> grid;
    static ArrayList<Integer> teleGrid;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static NavigationView navigationView;
    private NavigationMenuItemView night;
    Menu menu;
    static View headerView;
    static TextView allianceText;
    static TextView MatchText;
    String imagesDir;
    static String event;
    static String alliance = "Blue";
    static String team1 = "NA";
    static String team2 = "NA";
    static String team3 = "NA";
    static String offense1 = "NA";
    static String offense2 = "NA";
    static String offense3 = "NA";
    static String defense1 = "NA";
    static String defense2 = "NA";
    static String defense3 = "NA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllianceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab2);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "QR";
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        night = drawerLayout.findViewById(R.id.NightMode);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (faqActivityMain.sharedPreferences != null) {
            event = faqActivityMain.sharedPreferences.getString("event","");
        }
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
        fab2.setOnClickListener(this);
        if (!checkPermission()) {
            requestPermission();
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.before:
                    replaceFragment(new BeforeMatchFragmentAlliance(),"before");
                    getSupportActionBar().setTitle("Before Match ALLIANCE");
                    break;
                case R.id.auto:
                    replaceFragment(new AutoAlliance(),"auto");
                    getSupportActionBar().setTitle("Auto ALLIANCE");
                    break;
                case R.id.tele:
                    replaceFragment(new TeleOpAlliance(),"tele");
                    getSupportActionBar().setTitle("Tele-Operative ALLIANCE");
                    break;
                case R.id.thoughts:
                    replaceFragment(new ThoughtsAlliance(),"thoughts");
                    getSupportActionBar().setTitle("Thoughts ALLIANCE");
                    break;
                case R.id.end:
                    replaceFragment(new EndGameAlliance(),"end");
                    getSupportActionBar().setTitle("Review ALLIANCE");
                    break;
            }
            return true;
        });
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_layout_alliance);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        allianceText = headerView.findViewById(R.id.allianceNum);
        MatchText = headerView.findViewById(R.id.matchNum);
        MatchText.setText("Match: NA");
        allianceText.setText("Alliance:\n Blue");
        boolean locationGo = getIntent().getBooleanExtra("local",false);
        if (locationGo){
            replaceFragment(new EndGameAlliance(),"end");
            getSupportActionBar().setTitle("Review ALLIANCE");
            binding.bottomNavigationView.setSelectedItemId(R.id.end);
        }else {
            replaceFragment(new BeforeMatchFragmentAlliance(),"before");
            getSupportActionBar().setTitle("Before Match ALLIANCE");
            binding.bottomNavigationView.setSelectedItemId(R.id.before);
        }
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        night = drawerLayout.findViewById(R.id.NightMode);
        if (night != null) {
            if (SplashActivity.isDarkModeOn) {
                night.setTitle("Disable Dark Mode");
            } else {
                night.setTitle("Enable Dark Mode");
            }
        }
        return true;
    }
    @Override
    @SuppressLint("RestrictedApi")
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.newTeam:
                clearData();
                Intent intent = new Intent(AllianceActivity.this,AllianceActivity.class);
                startActivity(intent);
                break;
            case R.id.mainMenu:
                Intent intent3 = new Intent(AllianceActivity.this,SplashActivity.class);
                startActivity(intent3);
                break;
            case R.id.FileViewer:
                Intent intent1 = new Intent(AllianceActivity.this,FileListActivity.class);
                intent1.putExtra("path",imagesDir);
                intent1.putExtra("sendBack",true);
                startActivity(intent1);
                break;
            case R.id.share:
                rateApp();
                break;
            case R.id.faq:
                Intent intent2 = new Intent(AllianceActivity.this,faqActivityMain.class);
                startActivity(intent2);
                break;
            case R.id.NightMode:
                night = drawerLayout.findViewById(R.id.NightMode);
                if (night != null){
                    if (SplashActivity.isDarkModeOn) {
                        night.setTitle("Disable Dark Mode");
                        SplashActivity.editor.putBoolean(
                                "isDarkModeOn", false);
                        SplashActivity.editor.apply();
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate
                                                .MODE_NIGHT_NO);
                        night.setTitle("Disable Dark Mode");

                    }
                    else {
                        night.setTitle("Enable Dark Mode");
                        SplashActivity.editor.putBoolean(
                                "isDarkModeOn", true);
                        SplashActivity.editor.apply();
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        AppCompatDelegate
                                                .MODE_NIGHT_YES);
                        night.setTitle("Enable Dark Mode");
                    }
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:

                BeforeMatchFragmentAlliance beforeFrag = (BeforeMatchFragmentAlliance)getSupportFragmentManager().findFragmentByTag("before");
                EndGameAlliance endFrag = (EndGameAlliance) getSupportFragmentManager().findFragmentByTag("end");
                AutoAlliance autoFrag = (AutoAlliance) getSupportFragmentManager().findFragmentByTag("auto");
                TeleOpAlliance teleFrag = (TeleOpAlliance) getSupportFragmentManager().findFragmentByTag("tele");
                ThoughtsAlliance thoughtsFrag = (ThoughtsAlliance) getSupportFragmentManager().findFragmentByTag("thoughts");
                if (beforeFrag != null && beforeFrag.isVisible()) {
                    Intent intent = new Intent(AllianceActivity.this,SplashActivity.class);
                    startActivity(intent);
                }else if (autoFrag != null && autoFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.before);
                  replaceFragment(new BeforeMatchFragmentAlliance(), "before");
                }else if (teleFrag != null && teleFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.auto);
                    replaceFragment(new AutoAlliance(), "auto");
                }else if (thoughtsFrag != null && thoughtsFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.tele);
                    replaceFragment(new TeleOpAlliance(), "tele");
                }else if (endFrag != null && endFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.thoughts);
                    replaceFragment(new ThoughtsAlliance(), "thoughts");
                }
                break;
            case R.id.fab2:

                beforeFrag = (BeforeMatchFragmentAlliance) getSupportFragmentManager().findFragmentByTag("before");
                autoFrag = (AutoAlliance) getSupportFragmentManager().findFragmentByTag("auto");
                teleFrag = (TeleOpAlliance) getSupportFragmentManager().findFragmentByTag("tele");
                thoughtsFrag = (ThoughtsAlliance) getSupportFragmentManager().findFragmentByTag("thoughts");
                endFrag = (EndGameAlliance)getSupportFragmentManager().findFragmentByTag("end");

                if (beforeFrag != null && beforeFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.auto);
                    replaceFragment(new AutoAlliance(), "auto");
                }else if (autoFrag != null && autoFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.tele);
                    replaceFragment(new TeleOpAlliance(), "tele");
                }else if (teleFrag != null && teleFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.thoughts);
                    replaceFragment(new ThoughtsAlliance(), "thoughts");
                }else if (thoughtsFrag != null && thoughtsFrag.isVisible()) {
                    binding.bottomNavigationView.setSelectedItemId(R.id.end);
                    replaceFragment(new EndGameAlliance(), "end");
                    view.setVisibility(View.GONE);
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
        if (title.equals("end")){
            fab2.setVisibility(View.GONE);
        }else{
            fab2.setVisibility(View.VISIBLE);
        }
    }
    public boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(AllianceActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;

        }
    }
    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AllianceActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(AllianceActivity.this,"Storage permission is required, please allow it from settings.",Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(AllianceActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
        }
    }
    public static void setAllianceName(String allianceName) {
        allianceText= headerView.findViewById(R.id.allianceNum);
        allianceText.setText("Alliance:\n" + allianceName);
    }
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }
    public static void setMatchNum(String matchNum) {
        MatchText = headerView.findViewById(R.id.matchNum);
        MatchText.setText("Match:\n" + matchNum);
    }
    public static void setMatch(int match) {
        AllianceActivity.match = match;
    }
    public static void setAutoC(String autoC) {
        AllianceActivity.autoC = autoC;
    }
    public static void setTeleC(String teleC) {
        AllianceActivity.teleC = teleC;
    }
    public static void setGrid( ArrayList<Integer> grid) {
        AllianceActivity.grid = grid;
    }
    public static void setTeleGrid( ArrayList<Integer> teleGrid) {
        AllianceActivity.teleGrid = teleGrid;
    }
    public static void setAlliance(String alliance) {
        AllianceActivity.alliance = alliance;
    }
    public static void setTeam1(String team1) {
        AllianceActivity.team1 = team1;
    }
    public static void setTeam2(String team2) {
        AllianceActivity.team2 = team2;
    }
    public static void setTeam3(String team3) {
        AllianceActivity.team3 = team3;
    }
    public static void setOffense1(String offense1) {
        AllianceActivity.offense1 = offense1;
    }
    public static void setOffense2(String offense2) {
        AllianceActivity.offense2 = offense2;
    }
    public static void setOffense3(String offense3) {
        AllianceActivity.offense3 = offense3;
    }
    public static void setDefense1(String defense1) {
        AllianceActivity.defense1 = defense1;
    }
    public static void setDefense2(String defense2) {
        AllianceActivity.defense2 = defense2;
    }
    public static void setDefense3(String defense3) {
        AllianceActivity.defense3 = defense3;
    }
    public static int getDimen() {
        return dimen;
    }
    public static int getMatch() {
        return match;
    }
    public static String getAutoC() {
        return autoC;
    }
    public static String getTeleC() {
        return teleC;
    }

    public static ArrayList<Integer> getGrid() {
        return grid;
    }
    public static ArrayList<Integer> getTeleGrid() {
        return teleGrid;
    }
    public static String getAlliance(){
        return alliance;
    }
    public static String getTeam1() {
        return team1;
    }
    public static String getTeam2() {
        return team2;
    }
    public static String getTeam3() {
        return team3;
    }
    public static String getOffense1() {
        return offense1;
    }
    public static String getOffense2() {
        return offense2;
    }
    public static String getOffense3() {
        return offense3;
    }
    public static String getDefense1() {
        return defense1;
    }
    public static String getDefense2() {
        return defense2;
    }
    public static String getDefense3() {
        return defense3;
    }
    public static String[] getTeams(){
        return new String[]{team1,team2,team3};
    }
    public static String getAllData(){
        return event + "\n" + String.valueOf(match) + "\n" + String.valueOf(alliance) + "\n" + autoC + "\n" + grid.toString() + "\n" + teleC + "\n" + teleGrid.toString()+ "\n" + team1 + "\n" + team2 + "\n" + team3 + "\n" + offense1+"\n"+offense2 + "\n" + offense3 + "\n" + defense1+"\n"+ defense2 + "\n" + defense3;
    }
    public static void clearData(){
        match = -1;
        alliance = "Blue";
        autoC = "NA";
        grid = new ArrayList<Integer>();
        for (int i = 0; i < 27; i++) {
            grid.add(i,0);
        }
        teleC = "NA";
        teleGrid = new ArrayList<Integer>();
        for (int i = 0; i < 27; i++) {
            teleGrid.add(i,0);
        }
        offense1 = "NA";
        offense2 = "NA";
        offense3 = "NA";
        defense1 = "NA";
        defense2 = "NA";
        defense3 = "NA";
        team1 = "NA";
        team2 = "NA";
        team3 = "NA";
    }
}
