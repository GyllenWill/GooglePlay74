package com.ricky.md.googleplay.ui.hodler;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.ricky.md.googleplay.R;
import com.ricky.md.googleplay.domain.AppInfo;
import com.ricky.md.googleplay.http.HttpHelper;
import com.ricky.md.googleplay.utils.BitmapHelper;
import com.ricky.md.googleplay.utils.UIUtils;

public class HomeHodler extends BaseHodler<AppInfo> {

    private TextView tvName, tvSize, tvDes;
    private ImageView ivIcon;
    private RatingBar rbStar;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        // 1. 加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        // 2. 初始化控件
        tvName =  view.findViewById(R.id.tv_name);
        tvSize = view.findViewById(R.id.tv_size);
        tvDes = view.findViewById(R.id.tv_des);
        ivIcon = view.findViewById(R.id.iv_icon);
        rbStar =  view.findViewById(R.id.rb_star);

        //bitmapUtils = new BitmapUtils(UIUtils.getContext());
        bitmapUtils= BitmapHelper.getBitmapHelper();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tvDes.setText(data.des);
        rbStar.setRating(data.stars);

        //图片需要再加载
        bitmapUtils.display(ivIcon, HttpHelper.URL + "image?name="
                + data.iconUrl);
    }
}
