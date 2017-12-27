package com.hpw.myapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.hpw.mvpframe.CoreApp;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;

import cn.bmob.v3.Bmob;

/**
 * Created by hpw on 16/10/28.
 */

public class App extends CoreApp {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    @Override
    public String setBaseUrl() {
        return "http://news-at.zhihu.com/api/4/";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化屏幕宽高
        getScreenSize();
        Bugly.init(getApplicationContext(), "e0bec44741", true);
        Bmob.initialize(this, "b2c214bf677dbc9f2c22bd4645d4061e");
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "59881d5d4ad15615220000a8", "Mvp", MobclickAgent.EScenarioType.E_UM_NORMAL, true));
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
