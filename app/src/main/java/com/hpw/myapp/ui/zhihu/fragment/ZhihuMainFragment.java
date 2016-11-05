package com.hpw.myapp.ui.zhihu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.hpw.mvpframe.base.CoreBaseLazyFragment;
import com.hpw.mvpframe.utils.helper.FragmentAdapter;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.zhihu.contract.ZhihuContract;
import com.hpw.myapp.ui.zhihu.fragment.child.DailyFragment;
import com.hpw.myapp.ui.zhihu.fragment.child.QuickFragment;
import com.hpw.myapp.ui.zhihu.fragment.child.SectionFragment;
import com.hpw.myapp.ui.zhihu.model.ZhihuMainModel;
import com.hpw.myapp.ui.zhihu.presenter.ZhihuMainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.hpw.myapp.Constants.ARG_TITLE;

/**
 * Created by hpw on 16/10/31.
 */
public class ZhihuMainFragment extends CoreBaseLazyFragment<ZhihuMainPresenter, ZhihuMainModel> implements ZhihuContract.ZhihuMainView, Toolbar.OnMenuItemClickListener {
    protected OnFragmentOpenDrawerListener mOpenDraweListener;
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }

    public static ZhihuMainFragment newInstance(String title) {
        ZhihuMainFragment fragment = new ZhihuMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.app_bar_main;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initData() {

    }

    @Override
    public void showTabList(String[] mTabs) {
        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        for (int i = 0; i < mTabs.length; i++) {
            tabs.addTab(tabs.newTab().setText(mTabs[i]));
            switch (i) {
                case 0:
                    fragments.add(new DailyFragment());
                    break;
                case 1:
                    fragments.add(new SectionFragment());
                    break;
                default:
                    fragments.add(new QuickFragment());
                    break;
            }
        }
        viewpager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        tabs.setupWithViewPager(viewpager);
        for (int i = 0; i < mTabs.length; i++) {
            tabs.getTabAt(i).setText(mTabs[i]);
        }
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("首页");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });
        fab.setOnClickListener(v -> Snackbar.make(v, "Snackbar comes out", Snackbar.LENGTH_LONG).setAction("action", vi -> {
            showToast("ZhihuMainFragment");
        }));
        toolbar.inflateMenu(R.menu.activity_main_drawer);
        toolbar.setOnMenuItemClickListener(this);
    }
}
