package robotics.scouting.current;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lets_go_splash.CreateAnim;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    Context context;
    ImageView imageView;
    List<String[]> TotalLines;
    public GridAdapter(Context context) throws Exception {
        this.context = context;
        TotalLines = readLineByLine();
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_grid,parent,false);
        return new ViewHolder(view);
    }
    public String[] readAll(){
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        if (csvFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(csvFile));
                StringBuilder text = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                String data = text.toString();
                String[] lines = data.split("\n");
                return lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public String regexFinder(String data,int position){
        String dataNew = data.split(";")[position];
        return dataNew;
    }
    List<String[]> finalElements;
    public boolean removeItem(int position) {
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        if (csvFile.exists()) {
            try {
                CSVReader reader2 = new CSVReader(new FileReader(csvFile));
                List<String[]> allElements = reader2.readAll();
                allElements.remove(position+1);
                FileWriter sw = new FileWriter(csvFile);
                CSVWriter writer = new CSVWriter(sw);
                writer.writeAll(allElements);
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    public List<String[]> readLineByLine() throws Exception {
        List<String[]> list = new ArrayList<>();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "file.csv");
        Path filePath = Paths.get(file.getAbsolutePath()); //possible error. check
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }
        }
        return list;
    }
    public void updateAll(GridAdapter.ViewHolder holder, int position) throws Exception {
        TotalLines = readLineByLine();
        String data = TotalLines.get(position+1)[0];
        String lAGrid = regexFinder(data,4).substring(0,6)+"| "+regexFinder(data,4).substring(18,24)+"| "+regexFinder(data,4).substring(36,42);
        String cAGrid = regexFinder(data,4).substring(6,12)+"| "+regexFinder(data,4).substring(24,30)+"| "+regexFinder(data,4).substring(42,48);
        String rAGrid = regexFinder(data,4).substring(12,18)+"| "+regexFinder(data,4).substring(30,36)+"| "+regexFinder(data,4).substring(48,53) + " ";
        String lTGrid = regexFinder(data,6).substring(0,6)+"| "+regexFinder(data,6).substring(18,24)+"| "+regexFinder(data,6).substring(36,42);
        String cTGrid = regexFinder(data,6).substring(6,12)+"| "+regexFinder(data,6).substring(24,30)+"| "+regexFinder(data,6).substring(42,48);
        String rTGrid = regexFinder(data,6).substring(12,18)+"| "+regexFinder(data,6).substring(30,36)+"| "+regexFinder(data,6).substring(48,53) + " ";
        holder.eventTitle.setText(regexFinder(data,0));
        holder.matchText.setText(regexFinder(data,1));
        holder.teamText.setText(regexFinder(data,2));
        holder.autoComment.setText(regexFinder(data,3));
        holder.LAutoGrid.setText(lAGrid);
        holder.CAutoGrid.setText(cAGrid);
        holder.RAutoGrid.setText(rAGrid);
        holder.teleComment.setText(regexFinder(data,5));
        holder.LTeleGrid.setText(lTGrid);
        holder.CTeleGrid.setText(cTGrid);
        holder.RTeleGrid.setText(rTGrid);
        holder.docking.setText(regexFinder(data,7));
        holder.dockingTime.setText(regexFinder(data,8));
        holder.eventHeader.setText("Event");
        holder.matchHeader.setText("Match");
        holder.teamHeader.setText("Team");
        holder.autoHeader.setText("Auto Comment");
        holder.AutoGridHeader.setText("Auto Grid");
        holder.teleHeader.setText("Tele");
        holder.TeleGridHeader.setText("Tele Grid");
        holder.dockHeader.setText("Docking");
        holder.dockingTimeHeader.setText("Docking Time");
    }
    @Override
    public void onBindViewHolder(GridAdapter.ViewHolder holder, int position) {
        try {
            updateAll(holder,position);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if (selectFile.isDirectory()){
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
                 */
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){

                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenu().add("DELETE");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("DELETE")){
                            boolean deleted = removeItem(holder.getAdapterPosition());
                            if(deleted){
                                Toast.makeText(context.getApplicationContext(), "DELETED", Toast.LENGTH_SHORT).show();
                                v.setVisibility(View.GONE);
                            }
                            ((GridActivity)context).recreate();
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
        if (TotalLines != null){
            return TotalLines.toArray().length-1;
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle;
        TextView eventHeader;
        TextView matchText;
        TextView matchHeader;
        TextView teamText;
        TextView teamHeader;
        TextView autoComment;
        TextView autoHeader;
        TextView AutoGridHeader;
        TextView LAutoGrid;
        TextView CAutoGrid;
        TextView RAutoGrid;
        TextView teleHeader;
        TextView teleComment;
        TextView TeleGridHeader;
        TextView LTeleGrid;
        TextView CTeleGrid;
        TextView RTeleGrid;
        TextView dockHeader;
        TextView docking;
        TextView dockingTimeHeader;
        TextView dockingTime;
        public ViewHolder(View itemView){
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_text);
            eventHeader = itemView.findViewById(R.id.event_header);
            matchText = itemView.findViewById(R.id.match_text);
            matchHeader = itemView.findViewById(R.id.match_header);
            teamText = itemView.findViewById(R.id.team_text);
            teamHeader = itemView.findViewById(R.id.team_header);
            autoComment = itemView.findViewById(R.id.autoc_text);
            autoHeader = itemView.findViewById(R.id.autoc_header);
            AutoGridHeader = itemView.findViewById(R.id.Autogrid_header);
            LAutoGrid = itemView.findViewById(R.id.LAgrid_text);
            CAutoGrid = itemView.findViewById(R.id.CAgrid_text);
            RAutoGrid = itemView.findViewById(R.id.RAgrid_text);
            teleHeader = itemView.findViewById(R.id.tele_header);
            teleComment = itemView.findViewById(R.id.teleop_text);
            TeleGridHeader = itemView.findViewById(R.id.teleGrid_header);
            LTeleGrid = itemView.findViewById(R.id.LTgrid_text);
            CTeleGrid = itemView.findViewById(R.id.CTgrid_text);
            RTeleGrid = itemView.findViewById(R.id.RTgrid_text);
            dockHeader = itemView.findViewById(R.id.dock_header);
            docking = itemView.findViewById(R.id.docking_text);
            dockingTimeHeader = itemView.findViewById(R.id.dockTime_header);
            dockingTime = itemView.findViewById(R.id.dockTime_text);
        }
    }
}
