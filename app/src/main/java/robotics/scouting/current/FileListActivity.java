package robotics.scouting.current;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.lets_go_splash.CreateAnim;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;

public class FileListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        TextView noFilesText = findViewById(R.id.nofiles_textview);
        String path = getIntent().getStringExtra("path");
        File root = new File(path);
        ImageView imageView = findViewById(R.id.idIVImage);
        File[] filesAndFolders = root.listFiles();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getVisibility() == View.VISIBLE){
                    imageView.startAnimation(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.fade_in));
                    imageView.setVisibility(View.GONE);
                }else{
                    Intent intent = new Intent(FileListActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        if (filesAndFolders == null || filesAndFolders.length == 0) {
            noFilesText.setVisibility(View.VISIBLE);
            return;
        }
        noFilesText.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapters(this, filesAndFolders));

    }
}