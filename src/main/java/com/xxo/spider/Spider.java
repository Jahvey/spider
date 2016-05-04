package com.xxo.spider;

import com.xxo.spider.domain.Page;
import com.xxo.spider.download.Downloadable;
import com.xxo.spider.process.Processable;
import com.xxo.spider.store.Storeable;
import com.xxo.spider.utils.PageUtils;

/**
 * 网络爬虫
 * Created by xiaoxiaomo on 2016/5/3.
 */
public class Spider {

    private String url ;
    private Downloadable downloadable ;
    private Processable processable ;
    private Storeable storeable ;

    public void start() {
        //1. 下载
        Page page = download(url);

        //2. 解析
        Page process = process(page);

        //3. 存储
        store(page);
    }


    public Page download( String url ){
        return downloadable.download(url) ;
    }

    public Page process( Page page ){
        return processable.process(page) ;
    }

    public void store( Page page ){
        storeable.store(page);
    }

    public void setStoreable( Storeable storeable ){
        this.storeable = storeable ;
    }

    public void setDownload( Downloadable downloadable ){
        this.downloadable = downloadable ;
    }

    public void setUrl( String url  ){
        this.url  = url ;
    }

    public void setProcessable(Processable processable) {
        this.processable = processable;
    }
}
