package com.ricky.md.googleplay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ricky.md.googleplay.ui.view.LoadingPage;
import com.ricky.md.googleplay.utils.UIUtils;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        loadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {

                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };

     //   loadData(loadingPage);

        return loadingPage;
    }

    //是否加载成功，让子类实现
    public abstract View onCreateSuccessView();
    public abstract LoadingPage.ResultState onLoad();

    //开始加载数据 -- 调用 布局类LoadingPage 中加载数据的方法
    // （但是该方法，其实是为了获取viewpager中每个fragment从服务器中获取数据的状态，然后进行view更新（刷新））
    public void loadData(){

        if (loadingPage!=null){
            System.out.println("------------进入了BaseFragment-->loadData--》if判断");
            loadingPage.loadData();
        }
    }

    // 对网络返回数据的合法性进行校验
    public LoadingPage.ResultState check(Object obj) {
        if (obj != null) {
            if (obj instanceof ArrayList) {// 判断是否是集合
                ArrayList list = (ArrayList) obj;

                if (list.isEmpty()) {
                    return LoadingPage.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }

}
