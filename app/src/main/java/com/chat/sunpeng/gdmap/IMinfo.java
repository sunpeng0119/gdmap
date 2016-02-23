package com.chat.sunpeng.gdmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amap.api.maps2d.model.LatLng;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunpeng on 16-1-30.
 */
public class IMinfo {
    //是否是在东经上
    private static boolean ISEAST;
    private static boolean ISNORTH;
    //经度
    private double longitude;
    //纬度
    private double latitude;
    private File file;
    private Date date;
    private String datestring;

    public IMinfo(File file){
        this.file=file;
        ISEAST=true;
        ISNORTH=true;
        try {
            Metadata metadata= ImageMetadataReader.readMetadata(file);
            for (Directory directory:metadata.getDirectories()){
                boolean flag=false;
                for (Tag tag:directory.getTags()){
                    if (tag.getDirectoryName().equals("GPS")){
                        if (tag.getDescription().equals("S")){
                            ISNORTH=false;
                        }
                        if (tag.getDescription().equals("W")){
                            ISEAST=false;
                        }
                        if (tag.getDescription().endsWith("\"")){
                            double titude;
                            String[] strings=tag.getDescription().split("°|'|\"");
                            titude=Double.parseDouble(strings[0])+Double.parseDouble(strings[1])/60+
                                    Double.parseDouble(strings[2])/3600;
                            if (!flag){
                                latitude=titude;
                                flag=true;
                            }else {
                                longitude=titude;
                            }
                        }
                        if (tag.getDescription().matches("^[0-9]+:[0-9]+:[0-9]+$")){
                            if (datestring!=null){
                            datestring=tag.getDescription()+datestring;}
                            else {
                                datestring=tag.getDescription();
                            }
                        }
                        if (tag.getDescription().endsWith("UTC")){
                            if (datestring!=null){
                            datestring=datestring+":"+tag.getDescription().replace("UTC","").trim();}
                            else {
                                datestring=":"+tag.getDescription().replace("UTC","").trim();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (datestring!=null){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
            try {
                date=simpleDateFormat.parse(datestring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }
    public static boolean isGPS(File file){
        IMinfo iMinfo=new IMinfo(file);
        if (iMinfo.getLatitude()!=0&&iMinfo.getLongitude()!=0){
            return true;
        }

        return false;
    };


    public static boolean ISEAST() {
        return ISEAST;
    }

    public String getDatestring() {
        return datestring;
    }

    public Date getDate() {
        return date;
    }

    public File getFile() {
        return file;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static boolean ISNORTH() {
        return ISNORTH;
    }

    @Override
    public String toString() {
        return "IMinfo{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", file=" + file +
                ", date=" + date +
                ", datestring='" + datestring + '\'' +
                '}';
    }
    public static Bitmap condenseBitmap( String path){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=16;
        Bitmap srcbitmap= BitmapFactory.decodeFile(path,options);
//        int srcHight=srcbitmap.getHeight();
//        int srcWidth=srcbitmap.getWidth();
//        // 设定图片新的高宽
//        int newHeight;
//        int newWidth ;
//        if (srcWidth>100){
//            newWidth=100;
//        }else newWidth=srcWidth;
//        if (srcHight>100){
//            newHeight=100;
//        }else newHeight=srcHight;
//        float heightScale = ((float) newHeight) / srcHight;
//        float widthScale = ((float) newWidth) / srcWidth;
//        Matrix matrix = new Matrix();
//        matrix.postScale(heightScale, widthScale);
//        matrix.postRotate(0);
//        matrix.postSkew(0f, 0f);
//        Bitmap newBitmap= Bitmap.createScaledBitmap(srcbitmap,newWidth,newHeight,true);
//        srcbitmap.recycle();
        return srcbitmap;
    }
    public LatLng getlatlng(){
        return new LatLng(getLatitude(), getLongitude());
    }
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
