package com.chat.sunpeng.gdmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity implements AMap.OnMarkerClickListener,AMap.OnMarkerDragListener{
    private MapView mapView;
    private AMap aMap;
    Button selecImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        init();
        aMap.setOnMarkerClickListener(this);
//        final File file= Environment.getExternalStorageDirectory();
//        File camera=new File(file.getPath()+"/DCIM/Camera");
//        for (int i=0;i<camera.listFiles().length&&i<20;i++){
//
//            ImageLoadUtil.getImageLoad().loadImage(camera.listFiles()[i].getAbsolutePath());
//        }
        List<String> paths=SeleImage.getSeleImage().getPaths();
//        Log.v("sdfjaiewnasjdf",paths.toString());
//        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(new LatLng(30.679879, 104.064855)).title("成都市")
//                .snippet("成都市:30.679879, 104.064855").draggable(true));
//        BitmapDescriptor bitmapDescriptor=BitmapDescriptorFactory.fromResource(R.drawable.qwerqew);
//        MarkerOptions markerOptions=new MarkerOptions();
//        markerOptions.anchor(0.5f,0.5f).position(new LatLng(31.240428,121.486136)).title("上海").draggable(true);
//        markerOptions.icon(bitmapDescriptor);
//        Marker marker=aMap.addMarker(markerOptions);
//
//        marker.isInfoWindowShown();
        List<IMinfo> list= new ArrayList<IMinfo>();
        for (String path:paths){
            list.add(new IMinfo(new File(path)));
        }
        Collections.sort(list, new Comparator<IMinfo>() {
            @Override
            public int compare(IMinfo lhs, IMinfo rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
        for (IMinfo iMinfo:list){
            Bitmap bitmap =IMinfo.condenseBitmap(iMinfo.getFile().getAbsolutePath());
            MarkerOptions markerOptions=new MarkerOptions().anchor(0.5f,1f).position(new LatLng(iMinfo.getLatitude(),
                    iMinfo.getLongitude())).title(iMinfo.getDate().toString()).draggable(true);
            Marker marker=aMap.addMarker(markerOptions);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));

        }

        for (int i=0;i<list.size()-1;i++){

            aMap.addPolyline((new PolylineOptions()).add(
                    new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude()),
                    new LatLng(list.get(i+1).getLatitude(), list.get(i+1).getLongitude())).color(
                    Color.RED));
        }
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(list.get(0).getlatlng(),17rl);
        aMap.animateCamera(cameraUpdate);
//        list.add(new IMinfo(new File("/storage/emulated/0/DCIM/Camera/IMG_20160122_152718.jpg")));
//        list.add(new IMinfo(new File("/storage/emulated/0/DCIM/Camera/IMG_20151003_170322.jpg")));
//        MarkerOptions markerOptions=new MarkerOptions().anchor(0.5f,0.5f).
//                position(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude())).title("上海").draggable(true);
//        MarkerOptions markerOptions1=new MarkerOptions().anchor(0.5f,0.5f).
//                position(new LatLng(list.get(1).getLatitude(), list.get(1).getLongitude())).title("上海").draggable(true);
//        aMap.addMarker(markerOptions);
//        aMap.addMarker(markerOptions1);
        selecImage=(Button)findViewById(R.id.selet_iamge);
        selecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent1);
            }
        });


    }




    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();

        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.isInfoWindowShown();
        Toast.makeText(MainActivity.this, "sfsdfdsfsadf", Toast.LENGTH_SHORT).show();
        Log.v("asdfasdf","asdfasf");
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
