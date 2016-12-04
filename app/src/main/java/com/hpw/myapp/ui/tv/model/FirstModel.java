package com.hpw.myapp.ui.tv.model;

import com.hpw.mvpframe.data.net.RxService;
import com.hpw.mvpframe.utils.helper.RxUtil;
import com.hpw.myapp.api.TvApi;
import com.hpw.myapp.ui.tv.contract.TvContract;

import rx.Observable;

/**
 * Created by hpw on 16/12/4.
 */

public class FirstModel implements TvContract.FirstModel {
    @Override
    public Observable<FirstBean> getFirstData(String url) {
        return null;
    }

    @Override
    public Observable<Object> getBannerData() {
        return RxService.createApi(TvApi.class).getBannerList().compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<OtherBean> getOtherData(String url) {
        return RxService.createApi(TvApi.class).getOtherList(url).compose(RxUtil.rxSchedulerHelper());
    }
}
