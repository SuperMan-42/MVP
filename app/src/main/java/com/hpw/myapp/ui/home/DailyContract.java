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

        Observable<ZhihuDetailBean> getDailyDetails(int anInt);
    }

    interface View extends CoreBaseView {
        void showContent(DailyListBean info);

        void doInterval(int i);
    }

    interface DailyDetails extends CoreBaseView {
        void showContent(ZhihuDetailBean info);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        abstract void getDailyData();

        abstract void startInterval();
    }

    abstract class DetailsPresenter extends CoreBasePresenter<Model, DailyDetails> {
        abstract void getDailyDetails(int anInt);
    }
}
