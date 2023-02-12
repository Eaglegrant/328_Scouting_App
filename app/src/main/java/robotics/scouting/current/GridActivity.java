package robotics.scouting.current;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;


import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class GridActivity extends AppCompatActivity {
    TextView noFilesText;
    File csvFile;
    RecyclerView recyclerView;
    ArrayList<recycler> recyclerArrayList;
    GridAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        noFilesText = findViewById(R.id.nofiles_textview);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridActivity.this, QRCodeScanner.class);
                startActivity(intent);
            }
        });

        csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        if (!csvFile.exists()) {
            noFilesText.setVisibility(View.VISIBLE);
        }else{

            try {
                getData();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new GridAdapter(this));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuTeam:
                Collections.sort(recyclerArrayList,recycler.teamComparator); //Not doing anything?
                gridAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Sorted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wipeData:
                File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
            if (csvFile.exists()) {
                Boolean succeeded = csvFile.delete();
                if (succeeded) {
                    Toast.makeText(this, "Data Reset", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Data Reset Failed", Toast.LENGTH_SHORT).show();
                }
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() throws Exception {
        List<String[]> TotalLines = readLineByLine();
        recyclerArrayList = new ArrayList<recycler>();
        for (int i=1;i< TotalLines.toArray().length;i++){
            String data = TotalLines.get(i)[0];
                recycler recyclerp = new recycler(regexFinder(data,2));
                if (recyclerArrayList != null){
                    recyclerArrayList.add(recyclerp);
                }
        }
        gridAdapter = new GridAdapter(this);

        recyclerView.setAdapter(gridAdapter);
    }
    public String regexFinder(String data,int position){
        String dataNew = data.split(";")[position];
        return dataNew;
    }
    public List<String[]> readLineByLine() throws Exception {
        List<String[]> list = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Path filePath = Paths.get(file.getAbsolutePath()); //possible error. check
            try (Reader reader = Files.newBufferedReader(filePath)) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        list.add(line);
                    }
                }
            }
        }
        return list;
    }
}