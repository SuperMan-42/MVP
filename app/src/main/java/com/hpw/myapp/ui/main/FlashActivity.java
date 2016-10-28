package com.hpw.myapp.ui.main;

import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by hpw on 16/10/28.
 */

public class FlashActivity extends CoreBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void initView() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    startActivity(HomeActivity.class);
                    finish();
                });
    }
}
