package com.example.test608.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.test608.model.BottomBar;
import com.example.test608.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AppConfig {
    private static HashMap<String, Destination> sDestConfig;

    public static HashMap<String,Destination> getsDestConfig(){
        if(sDestConfig==null){
            String content = parseFile("destination.json");
            sDestConfig= JSON.parseObject(content,new TypeReference<HashMap<String,Destination>>(){}.getType());
        }
        return sDestConfig;
    }

    public static BottomBar sBottomBar;
    public static BottomBar getsBottomBar(){
        if (sBottomBar==null){
            String content = parseFile("main_tabs_config.json");
            sBottomBar=JSON.parseObject(content,BottomBar.class);
        }
        return sBottomBar;
    }

    public static String parseFile(String fileName){
        AssetManager assetManager=AppGlobals.getsApplication().getAssets();
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder builder=new StringBuilder();
        try{
            stream=assetManager.open(fileName);
            reader=new BufferedReader(new InputStreamReader(stream));
            String line=null;
            while((line=reader.readLine())!=null){
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(stream!=null){
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
