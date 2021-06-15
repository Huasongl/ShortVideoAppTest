package com.example.mylibrary;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.IntDef;
import okhttp3.Call;

public abstract class Request<T,R extends Request> {
    private String mUrl;
    protected HashMap<String,String> headers = new HashMap<>();
    protected HashMap<String,Object> params = new HashMap<>();

    public static final int CACHE_ONLY  =1;
    public static final int CACHE_FIRST =2;
    public static final int NET_ONLY    =3;
    public static final int NET_CACHE   =4;
    private String cacheKey;

    @IntDef({CACHE_ONLY,CACHE_FIRST,NET_ONLY,NET_CACHE})
    public @interface CacheStrategy{

    }

    public Request(String url){
        mUrl=url;
    }

    public R addHeader(String key,String value)    {
        headers.put(key,value);
        return (R) this;
    }
    public R addParams(String key,String value){
        Field field = null;
        try {
            field = value.getClass().getField("TYPE");
            Class claz= (Class) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (R) this;
    }

    public R cacheKey(String key){
        this.cacheKey=key;
        return (R) this;
    }
    public void execute(JsonCallback<T> callback){
        getCall();
    }

    private  Call getCall(){
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        addHeaders(builder);
        okhttp3.Request request =generateRequest(builder);
        Call call = ApiService.okHttpClient.newCall(request);
        return call;
    }

    protected abstract okhttp3.Request generateRequest(okhttp3.Request.Builder builder);

    private void addHeaders(okhttp3.Request.Builder builder){
        for (Map.Entry<String,String>entry:headers.entrySet()){
            builder.addHeader(entry.getKey(),entry.getValue());
        }
    }

    public void execute(){

    }
}
