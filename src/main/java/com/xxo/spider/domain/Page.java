package com.xxo.spider.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * Page
 * Created by xiaoxiaomo on 2016/5/3.
 */
public class Page {

    private String url ;
    private String content ;
    private JSONObject prop = new JSONObject();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject getProp() {
        return prop;
    }

    public void setProp(String k , String v) {
        prop.put(k ,v) ;
    }
}
