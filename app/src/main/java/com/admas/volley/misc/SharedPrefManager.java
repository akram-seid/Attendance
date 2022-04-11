package com.admas.volley.misc;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String KEY_EMAIL = "useremail";

    private static final String KEY_FRAGMENT = "fragment";

    private static final String KEY_ID = "userid";

    private static final String KEY_USERNAME = "username";

    private static final String SHARED_PREF_NAME = "sharedpref1";

    private static final String SHARED_PREF_NAME1 = "viewpager";

    private static Context ctx;

    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance== null){
            mInstance= new SharedPrefManager(context);
        }
        return mInstance;
    }

    public static void loggedin(String user) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("viewpager", 0).edit();
        editor.putString("username", user);
        editor.apply();
    }

    public static String whoLoggedIn() {
        return ctx.getSharedPreferences("viewpager", 0).getString("username", null);
    }

    public void Logout() {
        SharedPreferences sharedPreferences1 = ctx.getSharedPreferences("sharedpref1", 0);
        SharedPreferences sharedPreferences2 = ctx.getSharedPreferences("viewpager", 0);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor1.clear();
        editor2.putString("username", null);
        editor1.apply();
        editor2.apply();
    }

    public boolean UserLogin(int paramInt) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences("sharedpref1", 0).edit();
        editor.putInt("userid", paramInt);
        editor.apply();
        return true;
    }

    public int getUserId() {
        return ctx.getSharedPreferences("sharedpref1", 0).getInt("userid", 0);
    }

    public String getUseremail() {
        return ctx.getSharedPreferences("sharedpref1", 0).getString("useremail", null);
    }

    public String getUsername() {
        return ctx.getSharedPreferences("sharedpref1", 0).getString("username", null);
    }

    public boolean isUserLoggedIn() {
        return (ctx.getSharedPreferences("sharedpref1", 0).getString("username", null) != null);
    }
}
