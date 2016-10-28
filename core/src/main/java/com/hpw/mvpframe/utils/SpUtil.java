package com.hpw.mvpframe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hpw on 16/10/28.
 */

public class SpUtil {
    static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getThemeIndex(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("ThemeIndex", 9);
    }

    public static void setThemeIndex(Context context, int index) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("ThemeIndex", index).apply();
    }

    public static boolean getNightModel(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("pNightMode", false);
    }

    public static void setNightModel(Context context, boolean nightModel) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("pNightMode", nightModel).apply();
    }
}

