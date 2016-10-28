package com.hpw.myapp.ui.main;

import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.SpUtil;
import com.hpw.myapp.R;

public class MainActivity extends CoreBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_1).setOnClickListener(click -> {
            SpUtil.setNight(this, !SpUtil.isNight());
            reload();
        });
    }
}
