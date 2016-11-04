package com.hpw.myapp.ui.home.model;

import com.hpw.myapp.ui.home.contract.HomeContract;

/**
 * Created by hpw on 16/10/31.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public String[] getTabs() {
        String[] mTabs = {"日报", "主题", "专栏", "热门"};
        return mTabs;//暂时不从网络取
    }
}