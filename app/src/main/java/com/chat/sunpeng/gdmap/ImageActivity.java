package com.chat.sunpeng.gdmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends Activity implements GridView.OnItemClickListener ,View.OnClickListener{
    GridView gridView;
    List<String> paths;
    List<String> bitmapList;
    String type;
    ImageAdapter imageAdapter;
    Button btn_selected,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent=getIntent();
        type=intent.getStringExtra("TYPE");
        Log.v("TYPE",type);
        gridView=(GridView)findViewById(R.id.iamgetable);
        btn_selected=(Button)findViewById(R.id.btn_selected);
        back=(Button)findViewById(R.id.back);
        paths=new ArrayList<String>();
        btn_selected.setOnClickListener(this);
        back.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        final File file= Environment.getExternalStorageDirectory();
        File camera=new File(file.getPath()+"/DCIM/Camera");
        bitmapList=new ArrayList<String>();
        for (File f:camera.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith("jpg")&&IMinfo.isGPS(pathname)){
                    return true;
                }
                return false;
            }
        })){
            bitmapList.add(f.getAbsolutePath());
        }
        imageAdapter=new ImageAdapter(this,bitmapList);
        gridView.setAdapter(imageAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path=imageAdapter.paths.get(position);
        ImageView selected=(ImageView)view.findViewById(R.id.selected);
        if (type.equals("MORE")){
        if (selected.getVisibility()==View.INVISIBLE){
            selected.setVisibility(View.VISIBLE);
            imageAdapter.selected.set(position, true);
            paths.add(path.toString());
            return;
        }
        if (selected.getVisibility()==View.VISIBLE){
            selected.setVisibility(View.INVISIBLE);
            imageAdapter.selected.set(position, false);
            paths.remove(path.toString());
            return;
        }}else {
            paths.add(path.toString());
            Intent intent=new Intent(ImageActivity.this,MainActivity.class);
            SeleImage.getSeleImage().setPaths(paths);
            startActivity(intent);
            finish();

        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_selected:
                Intent intent=new Intent(ImageActivity.this,MainActivity.class);
                SeleImage.getSeleImage().setPaths(paths);
                startActivity(intent);
                finish();
                break;
        }

    }
}
