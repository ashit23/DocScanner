package com.scanlibrary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Photos extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {

    MyAdapter myAdapter;
    List<cell> allFilesPath;
    private Bitmap bmp;
    ArrayList<Image> SelectionList;
    public static ArrayList<Uri> arrayList = new ArrayList<>();
    TextView itemcounter;
    ArrayList<Image> imageList;
    int counter=0;
    Boolean isContextualModeEnabled=false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        toolbar=findViewById(R.id.toolBar);
        itemcounter=findViewById(R.id.itemCounter);
        itemcounter.setText("Scanner");
        setSupportActionBar(toolbar);
        SelectionList= new ArrayList<>();
        imageList= new ArrayList<>();
        showImages();

    }
    private void showImages() {
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
        allFilesPath= new ArrayList<>();
        allFilesPath= listAllFiles(path);

        RecyclerView recyclerView= findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<cell> cells= prepareData();
        myAdapter= new MyAdapter(getApplicationContext(),this,cells);
        recyclerView.setAdapter(myAdapter);


    }
    private ArrayList<cell> prepareData(){
        ArrayList<cell> allImages=new ArrayList<>();
        for(cell c:allFilesPath){
            cell cell= new cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }

    private List<cell>listAllFiles(String pathName){
        List<cell> AllFiles= new ArrayList<>();
        File file = new File(pathName);
        File[] files= file.listFiles();
        if(files!=null){
            for(File f: files){
                cell cell= new cell();
                cell.setTitle(f.getName());
                cell.setPath(f.getAbsolutePath());
                AllFiles.add(cell);
            }
        }
        return AllFiles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu,menu);
        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        isContextualModeEnabled=true;
        myAdapter.notifyDataSetChanged();
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.contextual_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void MakeSelection(View view, int adapterPosition) {
        if(((CheckBox)view).isChecked()){
           //  SelectionList.add(imageList.get(adapterPosition));
            counter++;
            updateCounter();
        }else{
            // SelectionList.remove(imageList.get(adapterPosition));
            counter--;
            updateCounter();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Delete){
            myAdapter.removeItem(SelectionList);
            removeContextualActionMode();
           }
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void updateCounter() {
        itemcounter.setText(counter+" Item Selected");
    }

    private void removeContextualActionMode() {
        isContextualModeEnabled=false;
        itemcounter.setText("My App");
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.normal_menu);
        counter=0;
        SelectionList.clear();
        myAdapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View v) {
    }


    // @Override
  /*  public void onClick(int position) {
        Toast.makeText(this,""+position,Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(Photos.this,FullScreen.class);
        intent.putExtra("name",String.valueOf(imageList.get(position)));
        intent.putExtra("pos",position);
        startActivity(intent);*/


}