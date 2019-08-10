package com.ricky.md.googleplay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

        //设置默认状态选择器为全透明
        this.setSelector(new ColorDrawable());
        //去掉分割线
        this.setDivider(null);
        //有时候滑动listView背景会变成黑色，此方法可以将其变为全透明
        this.setCacheColorHint(Color.TRANSPARENT);
    }
}
