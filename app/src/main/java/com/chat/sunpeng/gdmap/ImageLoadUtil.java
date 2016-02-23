package com.chat.sunpeng.gdmap;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sunpeng on 16-2-1.
 */
public class ImageLoadUtil {
    private HashMap<String,Bitmap> bitmapHashMap;
    private List<String> lastpath;
    private static ImageLoadUtil imageLoadUtil=null;
    int max;
    public ImageLoadUtil(){
        bitmapHashMap=new HashMap<String,Bitmap>();
        lastpath=new LinkedList<String>();
        max=20;
    };
    public static ImageLoadUtil getImageLoad(){
        if (imageLoadUtil==null){
            imageLoadUtil=new ImageLoadUtil();
            return imageLoadUtil;
        }
        return imageLoadUtil;
    }

    public Bitmap loadImage(String path){
        if (bitmapHashMap.get(path)!=null){
            return bitmapHashMap.get(path);
        }else {
            Bitmap bitmap=IMinfo.condenseBitmap(path);
            if (lastpath.size()>=20){
                bitmapHashMap.remove(lastpath.get(0));
                bitmapHashMap.put(path, bitmap);
                lastpath.add(path);
                return bitmap;
            }else {
                bitmapHashMap.put(path,bitmap);
                lastpath.add(path);
                return bitmap;
            }

        }
    }
}
