package com.hpw.myapp.ui.home;

import com.hpw.mvpframe.data.net.RxService;
import com.hpw.mvpframe.utils.helper.RxUtil;
import com.hpw.myapp.ui.api.ZhiHuApi;

import rx.Observable;

/**
 * Created by hpw on 16/11/2.
 */
public class DailyModel implements DailyContract.Model {

    @Override
    public Observable<DailyListBean> getDailyData() {
        return RxService.createApi(ZhiHuApi.class).getDailyList().compose(RxUtil.rxSchedulerHelper());
    }
}
