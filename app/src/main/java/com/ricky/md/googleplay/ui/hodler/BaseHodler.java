package com.ricky.md.googleplay.ui.hodler;

import android.view.View;

public abstract class BaseHodler<T> {

    private View mRootView;

    private T data;

    //1. 加载布局文件
    //2. 初始化控件 findViewById
    //3. 打一个标记tag
    public BaseHodler(){
        //当new这个对象时, 就会加载布局, 初始化控件,设置tag
        mRootView=initView();
        mRootView.setTag(this);
    }

    //1. 加载布局文件
    //2. 初始化控件 findViewById
    public abstract View initView();

    //4. 根据数据来刷新界面
    public abstract void refreshView(T data);

    // 返回item的布局对象
    public View getRootView() {
        return mRootView;
    }

    public void setData(T data){
        this.data=data;
        refreshView(data);
    }
    // 获取当前item的数据
    public T getData() {
        return data;
    }
}
