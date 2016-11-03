package com.hpw.myapp.ui.home;

import com.hpw.mvpframe.base.CoreBaseModel;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;

import rx.Observable;

/**
 * Created by hpw on 16/11/2.
 */

public interface DailyContract {
    interface Model extends CoreBaseModel {
        Observable<DailyListBean> getDailyData();
    }

    interface View extends CoreBaseView {
        void showContent(DailyListBean info);

        void doInterval(int i);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {

        abstract void getDailyData();

        public abstract void startInterval();
    }
}
