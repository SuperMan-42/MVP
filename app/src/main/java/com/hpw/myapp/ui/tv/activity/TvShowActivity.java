package com.hpw.myapp.ui.tv.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hpw.mvpframe.utils.LogUtil;
import com.hpw.mvpframe.utils.StatusBarUtil;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.tv.model.OtherBean;
import com.hpw.myapp.ui.tv.model.TvShowBean;
import com.hpw.myapp.ui.tv.model.TvShowModel;
import com.hpw.myapp.ui.tv.presenter.TvShowPresenter;
import com.hpw.myapp.widget.media.HorMediaControllView;
import com.hpw.myapp.widget.media.LivePlayerHolder;
import com.hpw.myapp.widget.media.OnHorControllListener;
import com.hpw.myapp.widget.media.OnVerticalControllListener;
import com.hpw.myapp.widget.media.VerticalMediaControllView;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by hpw on 16/12/3.
 */

public class TvShowActivity extends BaseTvShowActivity<TvShowPresenter, TvShowModel> implements VerticalMediaControllView.OnFullScreenListener, View.OnTouchListener {
    private SurfaceView mSurfaceView;
    private VerticalMediaControllView verticalControll;
    private View mLoadingView;

    private LivePlayerHolder playerHolder;
    private HorMediaControllView horizontalControll;
    private OtherBean.DataBean mPlayBean;
    private boolean isVertical = true;

    private int mCodec;
    private String mPlayerPath;
    private ImageView bgImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tv_show;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this);
        initPlayer();
        initVerControll();
        initHorContrll();
        initData();
    }

    private void initPlayer() {
        mLoadingView = findViewById(R.id.LoadingView);
        bgImage = (ImageView) findViewById(R.id.bgImg);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceView.setOnTouchListener(this);
        mPlayBean = (OtherBean.DataBean) getIntent().getSerializableExtra("playBean");
        mCodec = getIntent().getIntExtra("mediaCodec", 0);
    }

    private void initVerControll() {
        verticalControll = (VerticalMediaControllView) findViewById(R.id.verticalControll);
        verticalControll.setOnVerticalControllListener(new OnVerticalControllListener(this, playerHolder));
        verticalControll.setOnFullScreenListener(this);
    }

    private void initHorContrll() {
        horizontalControll = (HorMediaControllView) findViewById(R.id.horizontalControll);
        horizontalControll.setOnHorControllListener(new OnHorControllListener(this, playerHolder));
    }

    private void initData() {
        if (mPlayBean != null) {
            mPresenter.onTvShow(mPlayBean.uid);
        }
        Glide.with(this).load(mPlayBean.thumb).fitCenter().into(bgImage);
        verticalControll.setData(mPlayBean.getView(), mPlayBean.getAvatar(), mPlayBean.getNick(), mPlayBean.getTitle(), mPlayBean.getFollow());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerHolder != null)
            playerHolder.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerHolder != null)
            playerHolder.onPause();
    }

    @Override
    protected void onDestroy() {
        if (playerHolder != null) {
            playerHolder.release();
            playerHolder = null;
        }
        verticalControll.onDestroy();
        horizontalControll.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void toPrepare() {
        if (playerHolder != null)
            playerHolder.prepare();
    }

    @Override
    public void showContent(TvShowBean info) {
        JSONObject jsonObject = new JSONObject((Map) info.getLive().getWs().getHls());
        JSONObject object = jsonObject.optJSONObject("0");
        if (object != null) {
            mPlayerPath = object.optString("src");
        } else {
            mPlayerPath = jsonObject.optJSONObject("4").optString("src");
        }
        playerHolder = new LivePlayerHolder(this, mSurfaceView, mCodec, mPlayerPath);
        playerHolder.startPlayer();
    }

    @Override
    public void onConnecting() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReConnecting() {
        showToastTips("正在重连...");
    }

    @Override
    public void onConnectSucces() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onConnectFailed() {
        showToastTips("连接失败");
    }

    @Override
    public void onPlayComleted() {
        showToastTips("主播离开了");
    }

    @Override
    public void onPlayerStart() {
        bgImage.animate().alpha(0).setDuration(1000).start();
    }

    @Override
    public void onPlayePause() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogUtil.i("TOUCH  " + isVertical);
        verticalControll.onTouchEvent(isVertical, event);
        horizontalControll.onTouchEvent(isVertical, event);
        return false;
    }

    @Override
    public void onVerticalClickFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Display display =
                getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onBackPressedSupport() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            return;
        }
        if (playerHolder != null)
            playerHolder.release();
        super.onBackPressedSupport();
    }
}
