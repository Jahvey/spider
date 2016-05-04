package com.xxo.spider.utils;

/**
 * Created by xiaoxiaomo on 2016/5/4.
 */
public class SleepUtsils {

    /**
     * 毫秒为单位
     * @param time
     */
    public static void sleep( long time ){
        try {
            Thread.sleep( time );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
