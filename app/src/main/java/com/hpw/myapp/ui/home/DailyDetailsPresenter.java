package com.hpw.myapp.ui.home;

/**
 * Created by hpw on 16/11/3.
 */

public class DailyDetailsPresenter extends DailyContract.DetailsPresenter {
    @Override
    void getDailyDetails(int anInt) {
        mRxManager.add(mModel
                .getDailyDetails(anInt)
                .subscribe(
                        themeChildListBean -> mView.showContent(themeChildListBean),
                        e -> mView.showError("数据加载失败ヽ(≧Д≦)ノ")
                ));
    }

    @Override
    public void onStart() {

    }
}
