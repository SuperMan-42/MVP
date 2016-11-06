package com.hpw.myapp.ui.zhihu.presenter.wechatpresenter;

import com.hpw.myapp.ui.zhihu.contract.ZhihuContract;

/**
 * Created by hpw on 16/11/6.
 */
public class WechatPresenter extends ZhihuContract.WechatPresenter {
    @Override
    public void onStart() {
        getWechatData();
    }

    @Override
    public void getWechatData() {
        mRxManager.add(mModel.getWechatData()
                .subscribe(
                        data -> mView.showContent(data),
                        e -> mView.showError("数据加载失败ヽ(≧Д≦)ノ")
                ));
    }
}
