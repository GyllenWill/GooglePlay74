package com.ricky.md.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.ricky.md.googleplay.domain.AppInfo;
import com.ricky.md.googleplay.http.protocol.HomeProtocol;
import com.ricky.md.googleplay.ui.adapter.MyBaseAdapter;
import com.ricky.md.googleplay.ui.hodler.BaseHodler;
import com.ricky.md.googleplay.ui.hodler.HomeHodler;
import com.ricky.md.googleplay.ui.view.LoadingPage;
import com.ricky.md.googleplay.ui.view.MyListView;
import com.ricky.md.googleplay.utils.UIUtils;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment{

    private MyListView listView;
    private ArrayList<AppInfo>  list;

    @Override
    public View onCreateSuccessView() {
        listView = new MyListView (UIUtils.getContext());

        listView.setAdapter(new MyAdapter(list));

        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
//        list = new ArrayList<String>();
//        for (int i=0;i<40;i++){
//
//            list.add("这是第"+i+"条目");
//        }
        list=new ArrayList<AppInfo>();
        HomeProtocol homeProtocol=new HomeProtocol();
        //加载第一页数据
        list = homeProtocol.getData(0);
        //校验数据并返回
        return check(list);
    }

    private class MyAdapter extends MyBaseAdapter<AppInfo> {

        public MyAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHodler<AppInfo> getHodler() {
            return new HomeHodler();
        }

        @Override
        public ArrayList onLoadMore() {
            //从服务器中下载数据--此处用手动添加
            ArrayList<AppInfo> list=new ArrayList<AppInfo>();

//            for (int i=0;i<20;i++){
//                list.add("加载更多"+i);
//            }
            //手动睡眠几秒钟，测试
//            SystemClock.sleep(2000);
            HomeProtocol homeProtocol=new HomeProtocol();
            //这里获取list的size时，老师创建了一个方法将list.size()封装再调用，而不是直接使用，我不明白
            ArrayList<AppInfo> data = homeProtocol.getData(list.size());
            return data;
        }
    }

}
