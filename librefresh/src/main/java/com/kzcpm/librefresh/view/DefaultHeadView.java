package com.kzcpm.librefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import com.kzcpm.librefresh.R;
import com.kzcpm.librefresh.view.interf.IRefreshHead;
import com.kzcpm.librefresh.view.utils.DensityUtils;

/**
 * Created by: xudiwei
 * <p>
 * on: 2017/6/2.
 * <p>
 * 描述：默认的style_default样式的刷新头View
 */

public class DefaultHeadView extends FrameLayout implements IRefreshHead {

    private TextView mTvStatus;
    private LayoutParams mLayoutParams =
            new LayoutParams(LayoutParams.MATCH_PARENT, (int) DensityUtils.dipToPx(getContext(), 60));

    public DefaultHeadView(Context context) {
        this(context, null);
    }

    public DefaultHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.default_headview, null);
        mTvStatus = (TextView) view.findViewById(R.id.tv_status);
        mTvStatus.setText("自定义view");
        this.addView(view, mLayoutParams);
    }

    /**
     * 设置刷新头的状态颜色
     * @param textColor
     */
    public void setTextColor(@ColorRes int textColor) {
        mTvStatus.setTextColor(getResources().getColor(textColor));
    }

    /**
     * 设置刷新头的状态文字大小
     * @param textSize
     */
    public void setTextSize(int textSize) {
        mTvStatus.setTextSize(textSize);
    }

    @Override
    public void onStart() {
        mTvStatus.setText("开始下拉");
    }

    @Override
    public void onPullDown(int distance) {
        mTvStatus.setText("下拉刷新");
    }

    @Override
    public void onBound() {
        mTvStatus.setText("释放刷新");
    }

    @Override
    public void onFingerUp(int distance) {
        mTvStatus.setText("刷新中...");
    }

    @Override
    public void onStop() {

    }

    @Override
    public int headViewHeight() {
        return (int) DensityUtils.dipToPx(getContext(), 60);
    }
}
