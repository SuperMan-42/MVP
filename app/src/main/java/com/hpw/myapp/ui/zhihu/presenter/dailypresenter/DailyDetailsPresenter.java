package com.hpw.myapp.ui.zhihu.presenter.dailypresenter;

import com.hpw.myapp.ui.zhihu.contract.ZhihuContract;

/**
 * Created by hpw on 16/11/3.
 */

public class DailyDetailsPresenter extends ZhihuContract.DailyDetailsPresenter {
    @Override
    public void getDailyDetails(int anInt) {
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
