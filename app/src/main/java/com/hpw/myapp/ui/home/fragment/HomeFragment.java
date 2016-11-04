package com.hpw.myapp.ui.home.fragment;

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
import com.hpw.myapp.ui.home.contract.HomeContract;
import com.hpw.myapp.ui.home.model.HomeModel;
import com.hpw.myapp.ui.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.hpw.myapp.Constants.ARG_TITLE;

/**
 * Created by hpw on 16/10/31.
 */
public class HomeFragment extends CoreBaseLazyFragment<HomePresenter, HomeModel> implements HomeContract.View, Toolbar.OnMenuItemClickListener {
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

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
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
            if (i == 0) {
                fragments.add(new DailyFragment());
            } else {
                fragments.add(new QuickFragment());
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
            showToast("HomeFragment");
        }));
        toolbar.inflateMenu(R.menu.activity_main_drawer);
        toolbar.setOnMenuItemClickListener(this);
    }
}
