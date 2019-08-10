package com.ricky.md.googleplay.ui.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.ricky.md.googleplay.domain.AppInfo;
import com.ricky.md.googleplay.http.protocol.AppProtocol;
import com.ricky.md.googleplay.ui.hodler.AppHodler;
import com.ricky.md.googleplay.ui.adapter.MyBaseAdapter;
import com.ricky.md.googleplay.ui.hodler.BaseHodler;
import com.ricky.md.googleplay.ui.view.LoadingPage;
import com.ricky.md.googleplay.utils.UIUtils;

import java.util.ArrayList;

public class AppFragment extends BaseFragment{

    private ListView listView;
    private ArrayList<AppInfo> list;
    private AppProtocol appProtocol;

    @Override
    public View onCreateSuccessView() {

        listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new MyAdapter(list));

        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {

        appProtocol = new AppProtocol();
        list = appProtocol.getData(0);

        return check(list);
    }

    private class MyAdapter extends MyBaseAdapter{

        public MyAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHodler<AppInfo> getHodler() {
            return new AppHodler();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            //从服务器中下载数据--此处用手动添加
            ArrayList<AppInfo> list=new ArrayList<AppInfo>();
            list=appProtocol.getData(list.size());
            //手动睡眠几秒钟，测试
            return list;
        }
    }




}
