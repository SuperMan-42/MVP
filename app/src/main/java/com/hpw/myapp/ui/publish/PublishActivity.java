package com.hpw.myapp.ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.StringUtils;
import com.hpw.myapp.R;
import com.hpw.myapp.ui.publish.utils.PublishUtils;
import com.hpw.myapp.widget.emoticonskeyboard.adpater.PageSetAdapter;
import com.hpw.myapp.widget.emoticonskeyboard.interfaces.EmoticonClickListener;
import com.hpw.myapp.widget.emoticonskeyboard.widget.EmoticonsEditText;
import com.hpw.myapp.widget.emoticonskeyboard.widget.FuncLayout;
import com.hpw.myapp.widget.imageselector.model.LocalMedia;
import com.hpw.myapp.widget.imageselector.view.ImagePreviewActivity;
import com.hpw.myapp.widget.imageselector.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hpw on 16/11/22.
 */

public class PublishActivity extends CoreBaseActivity implements FuncLayout.OnFuncKeyBoardListener, AdapterView.OnItemClickListener {

    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.et_content)
    EmoticonsEditText etContent;
    @BindView(R.id.et_limit)
    TextView tvLimit;
    @BindView(R.id.ek_bar)
    DefEmoticonsKeyBoard ekBar;
    private static int inputLength = 300;
    StringBuilder publishString;
    private static final int REQUEST_IMAGE = 2;
    private static ArrayList<String> mSelectPath = new ArrayList<>();
    private static Activity activity;
    @BindView(R.id.result_recycler)
    GridView gridView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        activity = this;
        gridView.setOnItemClickListener(this);
        tvPublish.setEnabled(false);
        initEmoticonsKeyBoardBar();
        initEmoticonsEditText();
    }

    private void initEmoticonsEditText() {
        publishString = new StringBuilder();
        PublishUtils.initEmoticonsEditText(etContent);
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                publishString.setLength(0);
                publishString.append(s);
                if (s == null) return;
                String str = s.toString().trim();
                if (isInputLengthOutOfRange(str) || str.equals("")) {
                    tvPublish.setEnabled(false);
                    tvPublish.setTextColor(getResources().getColor(R.color.font_dark_gery));
                    setTextInputCount(str, true);
                } else {
                    tvPublish.setEnabled(true);
                    tvPublish.setTextColor(getResources().getColor(R.color.white));
                    setTextInputCount(str, false);
                }
            }
        });
    }

    /**
     * 设置当前输入框中文字的颜色
     *
     * @param input
     */
    private void setTextInputCount(String input, boolean isOutOfRange) {
        int count = (int) Math.ceil((double) StringUtils.getChineseCount(input) / 2d);
        tvLimit.setText(" 还可以输入" + (inputLength - count) + "字");
        if (isOutOfRange) {
            if (input.equals("")) {
                tvLimit.setTextColor(getResources().getColor(R.color.font_dark_gery));
            } else {
                tvLimit.setTextColor(getResources().getColor(R.color.md_red_500));
            }
        } else {
            tvLimit.setTextColor(getResources().getColor(R.color.font_dark_gery));
        }
    }

    /**
     * 判断当前输入框中文字是否超过规定长度
     *
     * @param input
     * @return
     */
    private boolean isInputLengthOutOfRange(String input) {
        return Math.ceil((double) StringUtils.getChineseCount(input) / 2d) > inputLength;
    }

    private void initEmoticonsKeyBoardBar() {
        EmoticonClickListener emoticonClickListener = PublishUtils.getCommonEmoticonClickListener(etContent);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        PublishUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        ekBar.setAdapter(pageSetAdapter);
        ekBar.addOnFuncKeyBoardListener(this);
    }

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, PublishActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_publish:
                publish();
                break;
        }
    }

    private void publish() {

    }

    @Override
    public void OnFuncPop(int height) {

    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    public void onPause() {
        super.onPause();
        ekBar.reset();
    }

    public static void pickImage() {
        ImageSelectorActivity.start(activity, 9 - mSelectPath.size(), ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            mSelectPath.addAll((ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT));
        } else if (resultCode == RESULT_OK && requestCode == ImagePreviewActivity.REQUEST_PREVIEW) {
            mSelectPath.clear();
            for (LocalMedia localMedia : (ArrayList<LocalMedia>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT)) {
                mSelectPath.add(localMedia.getPath());
            }
        }
        gridView.setAdapter(new PhotoAdapter(mSelectPath));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<LocalMedia> mSelect = new ArrayList<>();
        for (String string : mSelectPath) {
            mSelect.add(new LocalMedia(string));
        }
        ImagePreviewActivity.startPreview(activity, mSelect, mSelect, mSelect.size(), position);
    }

    /**
     * 图片预览adapter
     */
    private class PhotoAdapter extends BaseAdapter {
        private ArrayList<String> selectPath = new ArrayList<>();

        public PhotoAdapter(ArrayList<String> mSelectPath) {
            selectPath = mSelectPath;
        }

        @Override
        public int getCount() {
            return selectPath.size();
        }

        @Override
        public Object getItem(int position) {
            if (position >= selectPath.size()) {
                return null;
            }
            return selectPath.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = View.inflate(parent.getContext(), R.layout.item_photo_result, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String photoPath = selectPath.get(position);
            if (null != photoPath) {
                if (!photoPath.equals(holder.imageView.getTag())) {
                    Glide.with(convertView.getContext()).load(new File(photoPath)).centerCrop().into(holder.imageView);
                    holder.imageView.setTag(photoPath);
                }
            }
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
        }
    }
}
