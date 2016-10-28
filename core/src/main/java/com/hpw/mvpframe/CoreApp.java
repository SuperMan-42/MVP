package com.hpw.mvpframe;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.hpw.mvpframe.utils.SpUtil;

/**
 * Created by hpw on 16/10/28.
 */

public class CoreApp extends Application {
    private static CoreApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SpUtil.init(this);
    }

    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

}
