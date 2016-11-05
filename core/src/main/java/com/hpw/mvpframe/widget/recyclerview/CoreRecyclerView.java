package com.hpw.mvpframe.widget.recyclerview;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hpw.mvpframe.R;
import com.hpw.mvpframe.widget.recyclerview.animation.BaseAnimation;
import com.hpw.mvpframe.widget.recyclerview.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by hpw on 16/11/1.
 */

public class CoreRecyclerView extends LinearLayout implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    BaseQuickAdapter mQuickAdapter;
    addDataListener addDataListener;

    public interface addDataListener<T> {
        List<T> addData();
    }

    private int delayMillis = 1000;
    private static final int TOTAL_COUNTER = 18;
    private static final int PAGE_SIZE = 6;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private View notLoadingView;

    public CoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public CoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CoreRecyclerView initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_recyclerview, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setEnabled(false);
        return this;
    }

    public CoreRecyclerView init(BaseQuickAdapter mQuickAdapter) {
        init(null, mQuickAdapter);
        return this;
    }

    public CoreRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter) {
        mRecyclerView.setLayoutManager(layoutManager != null ? layoutManager : new LinearLayoutManager(getContext()));
        this.mQuickAdapter = mQuickAdapter;
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);
        return this;
    }

    public CoreRecyclerView addOnItemClickListener(OnItemClickListener onItemClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        return this;
    }

    public CoreRecyclerView setAddDataListener(addDataListener addDataListener) {
        this.addDataListener = addDataListener;
        mQuickAdapter.setNewData(addDataListener.addData());
        return this;
    }

    @Override
    public void onRefresh() {
        mQuickAdapter.setNewData(addDataListener.addData());
        mQuickAdapter.openLoadMore(PAGE_SIZE);
        mQuickAdapter.removeAllFooterView();
        mCurrentCounter = PAGE_SIZE;
        mSwipeRefreshLayout.setRefreshing(false);
        isErr = false;
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(() -> {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mQuickAdapter.loadComplete();
                if (notLoadingView == null) {
                    notLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                }
                mQuickAdapter.addFooterView(notLoadingView);
            } else {
                if (isErr) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mQuickAdapter.addData(addDataListener.addData());
                            mCurrentCounter = mQuickAdapter.getData().size();
                        }
                    }, delayMillis);
                } else {
                    isErr = true;
                    mQuickAdapter.showLoadMoreFailedView();

                }
            }
        });
    }

    public CoreRecyclerView addFooterView(View footer) {
        mQuickAdapter.addFooterView(footer);
        return this;
    }

    public CoreRecyclerView addFooterView(View footer, int index) {
        mQuickAdapter.addFooterView(footer, index);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header) {
        mQuickAdapter.addHeaderView(header);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header, int index) {
        mQuickAdapter.addHeaderView(header, index);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header, int index, int orientation) {
        mQuickAdapter.addHeaderView(header, index, orientation);
        return this;
    }

    public CoreRecyclerView hideLoadingMore() {
        mQuickAdapter.hideLoadingMore();
        return this;
    }

    public CoreRecyclerView loadComplete() {
        mQuickAdapter.loadComplete();
        return this;
    }

    public CoreRecyclerView openLoadAnimation() {
        mQuickAdapter.openLoadAnimation();
        return this;
    }

    public CoreRecyclerView openLoadAnimation(BaseAnimation animation) {
        mQuickAdapter.openLoadAnimation(animation);
        return this;
    }

    public CoreRecyclerView openLoadAnimation(@BaseQuickAdapter.AnimationType int animationType) {
        mQuickAdapter.openLoadAnimation(animationType);
        return this;
    }

    public CoreRecyclerView openLoadMore(int pageSize) {
//        this.data = data == null ? new ArrayList<T>() : data;
        mQuickAdapter.openLoadMore(pageSize);
        mQuickAdapter.setOnLoadMoreListener(this);
        return this;
    }

    public CoreRecyclerView remove(int position) {
        mQuickAdapter.remove(position);
        return this;
    }

    public CoreRecyclerView removeAllFooterView() {
        mQuickAdapter.removeAllFooterView();
        return this;
    }

    public CoreRecyclerView removeAllHeaderView() {
        mQuickAdapter.removeAllHeaderView();
        return this;
    }

    public CoreRecyclerView removeFooterView(View footer) {
        mQuickAdapter.removeFooterView(footer);
        return this;
    }

    public CoreRecyclerView removeHeaderView(View header) {
        mQuickAdapter.removeHeaderView(header);
        return this;
    }

    public CoreRecyclerView setDuration(int duration) {
        mQuickAdapter.setDuration(duration);
        return this;
    }

    public CoreRecyclerView setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, isFootAndEmpty, emptyView);
        return this;
    }

    public CoreRecyclerView setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, emptyView);
        return this;
    }

    public CoreRecyclerView setEmptyView(View emptyView) {
        mQuickAdapter.setEmptyView(emptyView);
        return this;
    }

    public CoreRecyclerView setLoadingView(View loadingView) {
        mQuickAdapter.setLoadingView(loadingView);
        return this;
    }

    public CoreRecyclerView setLoadMoreFailedView(View view) {
        mQuickAdapter.setLoadMoreFailedView(view);
        return this;
    }

    public CoreRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        mQuickAdapter.setOnLoadMoreListener(requestLoadMoreListener);
        return this;
    }

    public CoreRecyclerView showLoadMoreFailedView() {
        mQuickAdapter.showLoadMoreFailedView();
        return this;
    }

    public CoreRecyclerView startAnim(Animator anim, int index) {
        mQuickAdapter.startAnim(anim, index);
        return this;
    }

    public CoreRecyclerView openRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return this;
    }
}

