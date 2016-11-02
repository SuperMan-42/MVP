package com.hpw.myapp.ui.home;

/**
 * Created by hpw on 16/11/2.
 */
public class DailyPresenter extends DailyContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    public void getDailyData() {
        mRxManager.add(mModel
                .getDailyData()
                .subscribe(
                        dailyListBean -> mView.showContent(dailyListBean),
                        e -> mView.showError("数据加载失败ヽ(≧Д≦)ノ")
                ));
    }
}
