package com.hpw.myapp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.myapp.Constants;
import com.hpw.myapp.R;

/**
 * Created by hpw on 16/11/3.
 */

public class DailyDetailsFragment extends CoreBaseFragment<DailyDetailsPresenter, DailyModel> implements DailyContract.DailyDetails {

    public static DailyDetailsFragment newInstance(int id) {
        DailyDetailsFragment fragment = new DailyDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_DAILY_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_details;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void getBundle(Bundle bundle) {
        mPresenter.getDailyDetails(bundle.getInt(Constants.ARG_DAILY_ID));
    }

    @Override
    public void initData() {

    }

    @Override
    public void showContent(ZhihuDetailBean info) {

    }

    @Override
    public void showError(String msg) {

    }
}
