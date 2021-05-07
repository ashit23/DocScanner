package com.scanlibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;


public class FullScreen extends AppCompatActivity {

    private ImageView imageView;
    FloatingActionButton floatingActionButton;
    private Intent intent;
    private String currentImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imageView= findViewById(R.id.full_image);
        floatingActionButton= findViewById(R.id.share);
        intent=getIntent();

        currentImage= intent.getStringExtra("path");
        Glide.with(FullScreen.this).load(currentImage).into(imageView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                Bitmap bitmap= drawable.getBitmap();
                File f = new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".jpg");
                Intent shareInt =null;

                try{
                    FileOutputStream outputStream= new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();

                    shareInt= new Intent(Intent.ACTION_SEND);
                    shareInt.setType("image/*");
                    shareInt.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                    shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(Intent.createChooser(shareInt,"share image"));
            }
        });



    }

}