package com.ricky.md.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * 采用饿汉式单例模式，优化性能
 */
public class BitmapHelper {
    private static BitmapUtils bitmapHelper=null;

    /**
     *  饿汉式 单例模式
     * @return
     */
    public static BitmapUtils getBitmapHelper(){
        if (bitmapHelper == null){
            synchronized (BitmapHelper.class){
                if (bitmapHelper==null){
                    bitmapHelper=new BitmapUtils(UIUtils.getContext());
                    return bitmapHelper;
                }
            }
        }
        return bitmapHelper;
    }
}
