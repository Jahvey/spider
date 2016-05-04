package com.xxo.spider;

import com.xxo.spider.download.DownloadJDPageImpl;
import com.xxo.spider.process.ProcessJDMobileImpl;
import com.xxo.spider.store.ConsoleStoreImpl;
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
        spider.setEntryPage("http://list.jd.com/list.html?cat=9987%2C653%2C655&go=0");
        spider.setDownload(new DownloadJDPageImpl());
        spider.setProcessable(new ProcessJDMobileImpl());
        spider.setStoreable(new ConsoleStoreImpl());
        spider.start();
    }

}
