package com.ricky.md.googleplay.http.protocol;

import com.ricky.md.googleplay.http.HttpHelper;
import com.ricky.md.googleplay.utils.IOUtils;
import com.ricky.md.googleplay.utils.StringUtils;
import com.ricky.md.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 对网络操作的封装
 * 1.请求服务器
 * 2.缓存数据
 * 3.读取缓存
 * 访问网络的基类  有缓存先加载缓存
 */
public abstract class BaseProtocol<T> {


    /**
     * @param index 表示的是从哪个位置开始返回20条数据, 用于分页
     */
    public T getData(int index){
        //判断是否有缓存，有缓存先加载缓存

        String result = getCache(index);
        //没有缓存/缓存失效，去服务器上加载数据
        if (StringUtils.isEmpty(result)){
            //请求服务器
            result=getDataFromServer(index);
        }
        if (result!=null){
            //解析数据后，并将其返回
            T t = parseData(result);
            return t;
        }
        return null;
    }


    /**
     *  从网络获取数据
     * @param index 表示的是从哪个位置开始返回20条数据, 用于分页
     */
    private String getDataFromServer(int index) {
        // http://www.itheima.com/home?index=0&name=zhangsan&age=18
        HttpHelper.HttpResult httpResult= HttpHelper.get(HttpHelper.URL+getKey()+ "?index=" + index + getParams());
        if (httpResult!=null){
            String result = httpResult.getString();

            if (result!=null){
                //写缓存
                setCache(index,result);
            }
            return result;
        }
        return null;
    }

    // 获取网络链接关键词, 子类必须实现
    public abstract String getKey();//为什么不作为参数数直接传递呢？？？？不明白
    // 获取网络链接参数, 子类必须实现
    public abstract String getParams();

    /**
     *  写缓存  以url为文件名, 以json为文件内容,保存在本地
     * @param index 用来组成存储的文件名
     * @param json
     */
    public  void setCache(int index,String json){
        // 以url为文件名, 以json为文件内容,保存在本地
        // 本应用的缓存文件夹 data/data
        File cacheDir = UIUtils.getContext().getCacheDir();
        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());

        FileWriter writer=null;
        try {
            writer = new FileWriter(cacheFile);

            //为缓存添加有效期。。。。????
            //半个小时的有效期----deadLine为缓存失效的截止日期
            long deadLine=System.currentTimeMillis()+30*60*1000;
            //在缓存文件中的第一行写入，并换行（\n）与json分离
            writer.write(deadLine+"\n");

            // 写入json
            writer.write(json);
            //刷新
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //将write关掉
            if (writer!=null){
                IOUtils.close(writer);
            }
        }
    }

    /**
     *  读缓存
     * @param index
     * @return
     */
    public String getCache(int index) {
        // 本应用的缓存文件夹 data/data
        File cacheDir = UIUtils.getContext().getCacheDir();
        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());

        BufferedReader reader=null;

        try {
            reader=new BufferedReader(new FileReader(cacheFile));
            String deadLine = reader.readLine();
            //获取失效日期
            long deadTime = Long.parseLong(deadLine);
            //在失效日期之前，才可以加载
            if (System.currentTimeMillis()<deadTime){
                //当前时间小于截止时间 说明缓存有效

                //sb用来存放从文件中读取的数据
                StringBuffer sb=new StringBuffer();
                String line;
                while ((line=reader.readLine())!=null){
                    sb.append(line);
                }
                //返回字符串
                sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //将流关掉
            IOUtils.close(reader);
        }
        return null;
    }

    // 解析json数据, 子类必须实现
    public abstract T parseData(String result);

}
