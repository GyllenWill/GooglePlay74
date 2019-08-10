package com.ricky.md.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * 项目一初始化，就创建几个常用的值
 * 自定义application, 进行全局初始化
 */
public class GooglePlayApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainTreadId;

    //在一开始进行创建常用值
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        handler=new Handler();
        //获取子线程的ID
        mainTreadId=android.os.Process.myTid();
    }

    public static Context getContext(){
        return context;
    }

    public static Handler getHandler(){
        return handler;
    }
    public static int getMainThreadId(){
        return mainTreadId;
    }

}
