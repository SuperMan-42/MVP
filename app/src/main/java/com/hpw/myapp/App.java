package com.hpw.myapp;

import com.hpw.mvpframe.CoreApp;

/**
 * Created by hpw on 16/10/28.
 */

public class App extends CoreApp {
    @Override
    public String setBaseUrl() {
        return "http://news-at.zhihu.com/api/4/";
    }
}
