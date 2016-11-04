package com.hpw.myapp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hpw.mvpframe.base.CoreBaseFragment;
import com.hpw.mvpframe.utils.HtmlUtil;
import com.hpw.mvpframe.utils.NetUtils;
import com.hpw.mvpframe.utils.SnackbarUtil;
import com.hpw.mvpframe.utils.SpUtil;
import com.hpw.myapp.Constants;
import com.hpw.myapp.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by hpw on 16/11/3.
 */

public class DailyDetailsFragment extends CoreBaseFragment<DailyDetailsPresenter, DailyModel> implements DailyContract.DailyDetails {

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.fab)
    FloatingActionButton fab;

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
        setToolBar(toolbar, "");
        WebSettings settings = wvDetailContent.getSettings();
        if (SpUtil.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (SpUtil.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (NetUtils.isConnected(mContext)) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
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
        Glide.with(mContext).load(info.getImage()).crossFade().into(detailBarImage);
        collapsingToolbar.setTitle(info.getTitle());
        detailBarCopyright.setText(info.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(info.getBody(), info.getCss(), info.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(mActivity.getWindow().getDecorView(), msg);
    }
}
