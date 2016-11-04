package com.hpw.myapp.ui.home.contract;

import com.hpw.mvpframe.base.CoreBaseModel;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;

/**
 * Created by hpw on 16/10/31.
 */

public interface HomeContract {
    interface Model extends CoreBaseModel {
        String[] getTabs();
    }


    interface View extends CoreBaseView {
        void showTabList(String[] mTabs);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        public abstract void getTabList();
    }
}

