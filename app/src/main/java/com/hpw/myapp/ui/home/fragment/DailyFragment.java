package com.hpw.myapp.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hpw.mvpframe.base.CoreBaseLazyFragment;
import com.hpw.mvpframe.widget.recyclerview.BaseQuickAdapter;
import com.hpw.mvpframe.widget.recyclerview.BaseViewHolder;
import com.hpw.mvpframe.widget.recyclerview.CoreRecyclerView;
import com.hpw.mvpframe.widget.recyclerview.listener.OnItemClickListener;
import com.hpw.mvpframe.widget.recyclerview.recyclerviewpager.LoopRecyclerViewPager;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.home.model.DailyListBean;
import com.hpw.myapp.ui.home.model.DailyModel;
import com.hpw.myapp.ui.home.presenter.DailyPresenter;
import com.hpw.myapp.ui.home.activity.DailyDetailsActivity;
import com.hpw.myapp.ui.home.contract.DailyContract;

/**
 * Created by hpw on 16/10/31.
 */
public class DailyFragment extends CoreBaseLazyFragment<DailyPresenter, DailyModel> implements DailyContract.View {
    CoreRecyclerView coreRecyclerView;
    LoopRecyclerViewPager vpTop;

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).initAdapter(new BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder>(R.layout.item_daily) {
            @Override
            protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                Glide.with(mContext).load(item.getImages().get(0)).crossFade().placeholder(R.mipmap.def_head).into((ImageView) helper.getView(R.id.iv_daily_item_image));
            }
        }).addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
//                showToast("点击了" + position);
//                ((SupportFragment) getParentFragment()).start(DailyDetailsFragment.newInstance(((DailyListBean.StoriesBean) adapter.getData().get(position)).getId()));
                DailyDetailsActivity.start(mActivity, ((DailyListBean.StoriesBean) adapter.getData().get(position)).getId());
            }
        });
        return coreRecyclerView;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.daily_header, (ViewGroup) coreRecyclerView.getParent(), false);
        vpTop = (LoopRecyclerViewPager) view1.findViewById(R.id.vp_top);
        vpTop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        coreRecyclerView.addHeaderView(view1);
    }

    @Override
    public void initData() {
        mPresenter.getDailyData();
        mPresenter.startInterval();
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showContent(DailyListBean info) {
        vpTop.setAdapter(new BaseQuickAdapter<DailyListBean.TopStoriesBean, BaseViewHolder>(R.layout.item_daily_header, info.getTop_stories()) {
            @Override
            protected void convert(BaseViewHolder helper, DailyListBean.TopStoriesBean item) {
                helper.setText(R.id.tv_top_title, item.getTitle());
                Glide.with(mContext).load(item.getImage()).crossFade().placeholder(R.drawable.ic_img).into((ImageView) helper.getView(R.id.iv_top_image));
                helper.setOnClickListener(R.id.iv_top_image, v -> {
                    DailyDetailsActivity.start(mActivity, item.getId());
//                    ((SupportFragment) getParentFragment()).start(DailyDetailsFragment.newInstance(item.getId()));
                });
            }
        });
        coreRecyclerView.setAddDataListener(() -> info.getStories());
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void doInterval(int i) {
        vpTop.smoothScrollToPosition(i);
    }
}
