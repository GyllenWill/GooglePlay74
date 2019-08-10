package com.ricky.md.googleplay.ui.hodler;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ricky.md.googleplay.R;
import com.ricky.md.googleplay.utils.UIUtils;

public class MoreHodler extends BaseHodler<Integer> {

    // 加载更多的几种状态
    // 1. 可以加载更多
    // 2. 加载更多失败
    // 3. 没有更多数据
    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;

    private LinearLayout llLoadMore;
    private TextView tvLoadError;

    /**
     * @param hasMore 是否加载更多数据 true 是  false 否
     */
    public MoreHodler(boolean hasMore){
        if (hasMore){
            setData(STATE_MORE_MORE);
        }else {
            setData(STATE_MORE_NONE);
        }

    }

    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.list_item_more);

        llLoadMore =  inflate.findViewById(R.id.ll_load_more);
        tvLoadError = inflate.findViewById(R.id.tv_load_error);

        return inflate;
    }

    @Override
    public void refreshView(Integer data) {

        switch (data) {
            case STATE_MORE_MORE:
                // 显示加载更多
                llLoadMore.setVisibility(View.VISIBLE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                // 隐藏加载更多
                llLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                // 显示加载失败的布局
                llLoadMore.setVisibility(View.GONE);
                tvLoadError.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

    }

}
