package com.scanlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    static ArrayList<cell> galleryList;
    private Context context;
    private Photos photos;
    onItemClickListener listener;

    public MyAdapter(Context context, Photos photos, ArrayList<cell> galleryList) {
        this.context = context;
        this.photos = photos;
        MyAdapter.galleryList =galleryList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cell,parent,false);

        ViewHolder myViewHolder= new ViewHolder(v,photos);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageFromPath(galleryList.get(i).getPath(),holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context,FullScreen.class);
               intent.putExtra("path",galleryList.get(i).getPath());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
            }
        });
        holder.img.setOnLongClickListener(photos);
        if(!photos.isContextualModeEnabled){
            holder.checkBox.setVisibility(View.GONE);
        }else{
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        TextView textView;
        CheckBox checkBox;
        View view;

        public ViewHolder(@NonNull View itemView, Photos photos) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);
            img= itemView.findViewById(R.id.img);
            view=itemView;
            view.setOnLongClickListener(photos);
           // checkBox.setOnClickListener(photos);
          //  view.setOnClickListener(photos);
        }

        @Override
        public void onClick(View v) {
            photos.MakeSelection(view,getAdapterPosition());
        }
    }
    private void setImageFromPath(String path,ImageView image){
        File imgFile=new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap=ImageHelper.decodeSampledBitmapFromPath(imgFile.getAbsolutePath(),200,200);
            image.setImageBitmap(myBitmap);
        }
    }

    public void removeItem(ArrayList<Image> selectionList) {
        for(int i=0;i<selectionList.size();i++) {
            galleryList.remove(selectionList.get(i));
            notifyDataSetChanged();
        }
    }
}