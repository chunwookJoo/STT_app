package com.example.speak_5.Save_memo;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static final String DEFAULT_VALUE_STRING = "";
    private static final int DEFAULT_VALUE_INT = 1 ;

    //SharedPreferences 생성자
    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    //String 저장
    public static void setString(Context context, String key, String value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,value);
        editor.apply();
        //editor.commit();
    }

    //Int 저장
    public static void setInt(Context context, String key, int value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key,value);
        editor.apply();
        //editor.commit();
    }

    public static int getInt(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key,DEFAULT_VALUE_INT);
        return value;
    }

    public static String getString(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key,DEFAULT_VALUE_STRING);
        return value;
    }

    public static void Clear(Context context){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        //editor.commit();
    }
}
