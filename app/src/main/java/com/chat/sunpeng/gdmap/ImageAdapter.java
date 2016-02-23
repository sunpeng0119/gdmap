package com.chat.sunpeng.gdmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunpeng on 16-1-30.
 */
public class ImageAdapter extends BaseAdapter {
    List<String> paths;
    LayoutInflater inflater;
    List<Boolean> selected;
    ImageLoader imageLoader;

    public ImageAdapter(){

    }
    public ImageAdapter(Context context,List<String> paths){
        super();
        inflater=LayoutInflater.from(context);
        this.paths=paths;
        selected=new ArrayList<Boolean>();
        for (int i=0;i<paths.size();i++){
            selected.add(false);
        }
        imageLoader=ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
//                        .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                        //                    .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //                    .discCacheFileCount(100) //缓存的文件数量
//                        .discCache(new LruDiskCache(new File(cacheDir), new Md5FileNameGenerator(), Integer.MAX_VALUE))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                        .writeDebugLogs() // Remove for release app
                .build();//开始构建
        imageLoader.init(config);
    }
    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v("路径：",getItem(position).toString());
//        Bitmap image=ImageLoadUtil.getImageLoad().loadImage(paths.get(position));
        if (convertView!=null){
            convertView=inflater.inflate(R.layout.iamgetable,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.gps_iamge);
            if (selected.get(position)){
                convertView.findViewById(R.id.selected).setVisibility(View.VISIBLE);
            }else{
                convertView.findViewById(R.id.selected).setVisibility(View.INVISIBLE);
            }
            imageLoader.displayImage("file://"+paths.get(position), imageView);

        }else {
            convertView=inflater.inflate(R.layout.iamgetable,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.gps_iamge);
            imageLoader.displayImage("file://"+paths.get(position), imageView);
            if (selected.get(position)){
                convertView.findViewById(R.id.selected).setVisibility(View.VISIBLE);
            }else{
                convertView.findViewById(R.id.selected).setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

}
