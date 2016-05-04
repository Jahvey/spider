package com.xxo.spider.download;

import com.xxo.spider.domain.Page;
import com.xxo.spider.utils.PageUtils;

/**
 * Created by xiaoxiaomo on 2016/5/3.
 */
public class DownloadJDPageImpl implements Downloadable {
    public Page download( String url) {
        Page page = new Page();
        page.setUrl( url );
        page.setContent(PageUtils.getContentByURL( url ));
        return page ;
    }
}
