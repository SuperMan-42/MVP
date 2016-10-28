package com.hpw.mvpframe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hpw.mvpframe.base.CoreBaseActivity;

/**
 * Created by hpw on 16/10/28.
 */

public class SpUtil {
    static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isNight() {
        return prefs.getBoolean("isNight", false);
    }

    public static void setNight(Context context, boolean isNight) {
        prefs.edit().putBoolean("isNight", isNight).commit();
        if (context instanceof CoreBaseActivity)
            ((CoreBaseActivity) context).reload();
    }
}

