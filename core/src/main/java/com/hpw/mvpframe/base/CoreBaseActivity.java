package com.hpw.mvpframe.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hpw.mvpframe.AppManager;
import com.hpw.mvpframe.R;
import com.hpw.mvpframe.utils.DialogUtils;
import com.hpw.mvpframe.utils.LogUtil;
import com.hpw.mvpframe.utils.SpUtil;
import com.hpw.mvpframe.utils.StatusBarUtil;
import com.hpw.mvpframe.utils.TUtil;
import com.hpw.mvpframe.utils.ThemeUtil;
import com.hpw.mvpframe.utils.TitleBuilder;
import com.hpw.mvpframe.utils.ToastUtils;
import com.hpw.mvpframe.widget.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * Created by hpw on 16/10/12.
 */

public abstract class CoreBaseActivity<T extends CoreBasePresenter, E extends CoreBaseModel> extends AppCompatActivity {

    protected String TAG;
    private Dialog progressDialog;

    public T mPresenter;
    public E mModel;
    public Context mContext;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        TAG = getClass().getSimpleName();
        progressDialog = DialogUtils.createProgressDialog(this);

        setTheme(ThemeUtil.themeArr[SpUtil.getThemeIndex(this)][
                SpUtil.getNightModel(this) ? 1 : 0]);
        this.setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        this.initView();
        if (this instanceof CoreBaseView) mPresenter.attachVM(this, mModel);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mPresenter != null) mPresenter.detachVM();
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isOpen()) {
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            swipeBackLayout.addView(view);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    public void setStatusBarColor(boolean on) {
        if (on) {
            StatusBarUtil.setColor(this, ThemeUtil.getTheme(this), 0);
        }
    }

    protected boolean isApplyStatusBarColor() {
        return false;
    }

    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> {
                    finish();
                });
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {
        LogUtil.i(TAG, msg);// TODO: 16/10/12 Log需要自己从新搞一下
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
