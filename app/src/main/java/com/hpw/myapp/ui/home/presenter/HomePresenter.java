package com.hpw.myapp.ui.home.presenter;

import com.hpw.myapp.ui.home.contract.HomeContract;

/**
 * Created by hpw on 16/10/31.
 */

public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void getTabList() {
        mView.showTabList(mModel.getTabs());
    }

    @Override
    public void onStart() {
        getTabList();
    }
}