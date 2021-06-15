package com.example.test608.utils;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppGlobals {
    public static Application sApplication;

    public static Application getsApplication(){
        if(sApplication==null){
            try{
                Method method=Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                sApplication=(Application)method.invoke(null,(Object[])null);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
