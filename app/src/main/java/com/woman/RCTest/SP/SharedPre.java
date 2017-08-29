package com.woman.RCTest.SP;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class SharedPre {
    private static Context appcontext;
    private static String fimeName;

    public static void init(@NonNull Context context,@NonNull String file){
    appcontext=context.getApplicationContext();
    fimeName=file;
    }

    public static <T> T get(@NonNull String key, T fallback) throws
            UnsupportedOperationException {
        SharedPreferences sp = getSharedPreferences();
        Object result;
        if (fallback instanceof Boolean){
            result = sp.getBoolean(key, (Boolean)fallback);
        }
        else if (fallback instanceof String){
            result = sp.getString(key, (String) fallback);
        }
        else if (fallback instanceof Integer){
            result = sp.getInt(key, (Integer) fallback);
        }
        else if (fallback instanceof Float){
            result = sp.getFloat(key, (Float) fallback);
        }
        else if (fallback instanceof Long) {
            result = sp.getLong(key, (Long) fallback);
        }
        else{
            throw new UnsupportedOperationException("Type not supported: " + fallback.getClass()
                    .getSimpleName());
        }
        return (T)result;
    }
    public static String getString(@NonNull String key){
        return get(key, "");
    }

    /**
     * Retrieve a long value from the preferences, default is <code>0</code>.
     */
    public static long getLong(@NonNull String key){
        return get(key, 0L);
    }

    /**
     * Retrieve a integer value from the preferences, default is <code>0</code>.
     */
    public static int getInt(@NonNull String key){
        return get(key, 0);
    }

    /**
     * Retrieve a boolean value from the preferences, default is <code>false</code>.
     */
    public static boolean getBoolean(@NonNull String key){
        return get(key, false);
    }

    public static<T>void set(@NonNull String key,@NonNull T value){
        SharedPreferences .Editor editor=getSharedPreferences().edit();
        if (value instanceof Boolean){
            editor.putBoolean(key,(Boolean)value);
        }
        else if (value instanceof String){
            editor.putString(key, (String) value);
        }
        else if (value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }
        else if (value instanceof Float){
            editor.putFloat(key, (Float) value);
        }
        else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        else {
            throw  new UnsupportedOperationException("type not support"+value.getClass().getSimpleName());
        }
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences(){
        checkInitiatedOrThrow();
        return appcontext.getSharedPreferences(fimeName, Context.MODE_PRIVATE);
    }

    public static void remove(String key){
        getSharedPreferences().edit().remove(key).apply();
    }

    private static void checkInitiatedOrThrow() {
        if (appcontext==null|| TextUtils.isEmpty(fimeName))
            throw new IllegalStateException("the sharedpre init incorrect");
    }
}
