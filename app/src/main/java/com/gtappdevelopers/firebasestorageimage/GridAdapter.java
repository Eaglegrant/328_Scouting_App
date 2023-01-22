package com.gtappdevelopers.firebasestorageimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class GridAdapter extends BaseAdapter{
    private int[] imagesPhoto;
    private String[] namesPhoto;
    private Context context;
    private LayoutInflater layoutInflater;


    public GridAdapter(String[] imageNames,int[] imagesPhoto,Context context){
        this.imagesPhoto=imagesPhoto;
        this.namesPhoto=namesPhoto;
        this.context=context;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesPhoto.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null) {
            view = layoutInflater.inflate(R.layout.items_grid, viewGroup, false);
        }
        TextView tvName = view.findViewById(R.id.tvName);
        ImageView imageView = view.findViewById(R.id.imageView);
        tvName.setText(namesPhoto[i]);
        imageView.setImageResource(imagesPhoto[i]);

        return view;
    }
}
