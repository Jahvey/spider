package com.xxo.spider;

import com.xxo.spider.download.DownloadJDPageImpl;
import com.xxo.spider.process.ProcessJDMobileImpl;
import com.xxo.spider.store.MysqlStoreImpl;
import org.junit.Test;

/**
 *
 * Test
 * Created by xiaoxiaomo on 2016/5/3.
 */
public class TestSpider {


    @Test
    public void spider(){
        Spider spider = new Spider();
        spider.setUrl("http://item.jd.com/2766752.html");
        spider.setDownload(new DownloadJDPageImpl());
        spider.setProcessable(new ProcessJDMobileImpl());
        spider.setStoreable(new MysqlStoreImpl());
        spider.start();
    }

}
