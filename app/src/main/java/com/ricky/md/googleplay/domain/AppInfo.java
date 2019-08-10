package com.ricky.md.googleplay.domain;

/**
 * 首页应用信息封装
 * 采用JsonObject解析，不是Gson
 */
public class AppInfo {

    // 使用JsonObject解析方式: 如果遇到{},就是JsonObject;如果遇到[], 就是JsonArray

    public String des;
    public String downloadUrl;
    public String iconUrl;
    public String id;
    public String name;
    public String packageName;
    public long size;
    public float stars;
}
