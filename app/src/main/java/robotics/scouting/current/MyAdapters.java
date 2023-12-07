package robotics.scouting.current;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lets_go_splash.CreateAnim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyAdapters extends RecyclerView.Adapter<MyAdapters.ViewHolder> {
    Context context;
    File[] filesAndFolders;
    ImageView imageView;
    boolean success;
    private String rename = "";
    public MyAdapters(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyAdapters.ViewHolder holder, int position) {
        File selectFile = filesAndFolders[position];
        holder.textView.setText(selectFile.getName().replaceAll(".jpg"," QR Code"));
        if(selectFile.isDirectory()) {
            holder.imageView.setImageResource((R.drawable.ic_baseline_folder_24));
        }else{
            holder.imageView.setImageResource((R.drawable.ic_baseline_insert_drive_file_24));
        }
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (selectFile.isDirectory()){
            Intent intent = new Intent(context, FileListActivity.class);
            String path = selectFile.getAbsolutePath();
            intent.putExtra("path",path);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            try {
                File file = new File(selectFile.getPath());
                imageView = ((FileListActivity)context).findViewById(R.id.idIVImage);
                imageView.setImageDrawable(Drawable.createFromPath(file.toString()));
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(CreateAnim.INSTANCE.createAnimation(context, R.anim.no_animaiton));
            }catch (Exception e){
                Toast.makeText((context.getApplicationContext()),"Cannot open the file.",Toast.LENGTH_SHORT).show();
            }

        }
    }
});
holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
    @Override
    public boolean onLongClick(View v){

        PopupMenu popupMenu = new PopupMenu(context,v);
        popupMenu.getMenu().add("DELETE");
        popupMenu.getMenu().add("RENAME");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getTitle().equals("DELETE")){
                    boolean deleted = selectFile.delete();
                    if(deleted){
                        Toast.makeText(context.getApplicationContext(), "DELETED", Toast.LENGTH_SHORT).show();
                        v.setVisibility(View.GONE);
                    }
                    ((FileListActivity)context).recreate();

                }
                if(item.getTitle().equals("RENAME")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    final EditText edittext = new EditText(context);
                    alert.setMessage("What are you renaming this to?");
                    alert.setTitle("Renaming");

                    alert.setView(edittext);

                    alert.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Path source = Paths.get("path/here");
                                try {
                                    File newFile = new File(selectFile.getParent(), edittext.getText().toString() + ".png");
                                    Files.move(selectFile.toPath(), newFile.toPath());
                                    Toast.makeText(context.getApplicationContext(), "RENAMED", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    File newFile = new File(selectFile.getParent(), edittext.getText().toString() + ".png");
                                    selectFile.renameTo(newFile);
                                    Toast.makeText(context.getApplicationContext(), "RENAMED2", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                File newFile = new File(selectFile.getParent(), edittext.getText().toString() + ".png");
                                selectFile.renameTo(newFile);
                                Toast.makeText(context.getApplicationContext(), "RENAMED3", Toast.LENGTH_SHORT).show();
                            }

                            ((FileListActivity)context).recreate();
                        }
                    });

                    alert.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });

                    alert.show();
                }
                return true;
            }
        });
        popupMenu.show();
        return true;
    }
});

    }
    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.file_name_text_view);
            imageView = itemView.findViewById(R.id.icon_view);

        }
    }
}
