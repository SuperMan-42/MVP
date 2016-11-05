package com.hpw.myapp.ui.zhihu.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hpw.mvpframe.base.CoreBaseLazyFragment;
import com.hpw.mvpframe.utils.DisplayUtils;
import com.hpw.mvpframe.widget.recyclerview.BaseQuickAdapter;
import com.hpw.mvpframe.widget.recyclerview.BaseViewHolder;
import com.hpw.mvpframe.widget.recyclerview.CoreRecyclerView;
import com.hpw.myapp.App;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.zhihu.contract.ZhihuContract;
import com.hpw.myapp.ui.zhihu.model.sectionmodel.SectionListBean;
import com.hpw.myapp.ui.zhihu.model.sectionmodel.SectionModel;
import com.hpw.myapp.ui.zhihu.presenter.sectionpresenter.SectionPresenter;

/**
 * Created by hpw on 16/11/5.
 */

public class SectionFragment extends CoreBaseLazyFragment<SectionPresenter, SectionModel> implements ZhihuContract.SectionView {
    CoreRecyclerView coreRecyclerView;

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).initAdapter(new BaseQuickAdapter<SectionListBean.DataBean, BaseViewHolder>(R.layout.item_section) {
            @Override
            protected void convert(BaseViewHolder helper, SectionListBean.DataBean item) {
                //Glide在加载GridView等时,由于ImageView和Bitmap实际大小不符合,第一次时加载可能会变形(我这里出现了放大),必须在加载前再次固定ImageView大小
                ViewGroup.LayoutParams lp = helper.getView(R.id.section_bg).getLayoutParams();
                lp.width = (App.SCREEN_WIDTH - DisplayUtils.dp2px(mContext, 12)) / 2;
                lp.height = DisplayUtils.dp2px(mContext, 120);

                Glide.with(mContext).load(item.getThumbnail()).crossFade().placeholder(R.drawable.ic_img).into((ImageView) helper.getView(R.id.section_bg));
                helper.setText(R.id.section_kind, item.getName());
                helper.setText(R.id.section_des, item.getDescription());
                helper.setOnClickListener(R.id.ll_click, v -> {
                    showToast(item.getName());
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, SectionActivity.class);
//                        intent.putExtra("id", mList.get(holder.getAdapterPosition()).getId());
//                        intent.putExtra("title", mList.get(holder.getAdapterPosition()).getName());
//                        mContext.startActivity(intent);
                });
            }
        });
        return coreRecyclerView;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        mPresenter.getSectionData();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent(SectionListBean info) {
        coreRecyclerView.setAddDataListener(() -> info.getData());
    }
}
