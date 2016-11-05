package com.hpw.myapp.ui.zhihu.contract;

import com.hpw.mvpframe.base.CoreBaseModel;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;
import com.hpw.myapp.ui.zhihu.model.dailymodel.DailyListBean;
import com.hpw.myapp.ui.zhihu.model.dailymodel.ZhihuDetailBean;
import com.hpw.myapp.ui.zhihu.model.sectionmodel.SectionListBean;

import rx.Observable;

/**
 * Created by hpw on 16/10/31.
 */

public interface ZhihuContract {
    //主页接口
    interface ZhihuMainModel extends CoreBaseModel {
        String[] getTabs();
    }


    interface ZhihuMainView extends CoreBaseView {
        void showTabList(String[] mTabs);
    }

    abstract class ZhihuMainPresenter extends CoreBasePresenter<ZhihuMainModel, ZhihuMainView> {
        public abstract void getTabList();
    }

    //daily所有接口(model写在了一起,view presenter分开写)
    interface DailyModel extends CoreBaseModel {
        Observable<DailyListBean> getDailyData();

        Observable<ZhihuDetailBean> getZhihuDetails(int anInt);
    }

    interface DailyView extends CoreBaseView {
        void showContent(DailyListBean info);

        void doInterval(int i);
    }

    abstract class DailyPresenter extends CoreBasePresenter<DailyModel, DailyView> {
        public abstract void getDailyData();

        public abstract void startInterval();
    }

    interface ZhihuDetailsView extends CoreBaseView {
        void showContent(ZhihuDetailBean info);
    }

    abstract class ZhihuDetailsPresenter extends CoreBasePresenter<DailyModel, ZhihuDetailsView> {
        public abstract void getZhihuDetails(int anInt);
    }

    //section所有接口
    interface SectionModel extends CoreBaseModel {

        Observable<SectionListBean> getSectionData();
    }

    interface SectionView extends CoreBaseView {
        void showContent(SectionListBean info);

    }

    abstract class SectionPresenter extends CoreBasePresenter<SectionModel, SectionView> {

        public abstract void getSectionData();
    }

}

