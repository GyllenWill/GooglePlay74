package com.ricky.md.googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.ricky.md.googleplay.global.GooglePlayApplication;


/**
 * 项目的工具类
 */
public class UIUtils {

    public static Context getContext() {
        return GooglePlayApplication.getContext();
    }

    public static Handler getHandler() {
        return GooglePlayApplication.getHandler();
    }

    public static int getMainThreadId() {
        return GooglePlayApplication.getMainThreadId();
    }

    // /////////////////加载资源文件 ///////////////////////////

    /**
     * 获取字符串
     *
     * @param id 资源id
     * @return
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取字符串数组（比如，values/string.xml 可定义string-array）
     *
     * @param id
     * @return 返回一个字符串数组
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 获取一个Drawable 资源图片
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 根据id获取颜色的状态选择器
     *
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    /**
     * 获取尺寸
     *
     * @param id
     * @return 返回具体像素值
     */
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    // /////////////////dip和px转换//////////////////////////

    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    // /////////////////加载布局文件//////////////////////////

    /**
     *  获取布局对象
     * @param id
     * @return
     */
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    // /////////////////判断是否运行在主线程//////////////////////////

    /**
     *  判断当前线程是否为主线程
     * @return true 为主线程  false 为子线程
     */
    public static boolean isRunOnUIThread() {

        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程

        int tid = Process.myTid();
        if (tid==getMainThreadId()){
            return true;
        }else {
            return false;
        }
    }

    /**
     *  在主线程中运行
     * @param r
     */
    public static void runOnUIThread(Runnable r){

        if (isRunOnUIThread()){
            // 已经是主线程, 直接运行
            r.run();
        }else {

            // 如果是子线程, 借助handler让其运行在主线程

            //在子线程中转换为主线程的三个方法之一  1.runOnUIThread(); 2.handler.sendMessage()  3.handler.post(new Runnable{})
            getHandler().post(r);
        }
    }
}
