package com.xxo.spider.download;

import com.xxo.spider.domain.Page;

/**
 * Created by xiaoxiaomo on 2015/5/3.
 */
public interface Downloadable {

    /**
     * 通过URL下载一个页面
     * @param url
     * @return
     */
    public Page download( String url ) ;
}
