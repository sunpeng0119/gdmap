package com.chat.sunpeng.gdmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunpeng on 16-1-31.
 */
public class SeleImage  {
    private List<String> paths;

    private static SeleImage seleImage=null;
    public SeleImage(){}

    public static SeleImage getSeleImage(){
     if (seleImage==null){
      seleImage=new SeleImage();
     }
        return seleImage;
    }
    public List<String> getPaths() {
        if (paths==null){
            paths=new ArrayList<String>();
        }
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}

