package com.xxo.spider;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.junit.Test;

import java.net.InetAddress;

/**
 * Created by xiaoxiaomo on 2016/5/5.
 */
public class TestZookepper {

    @Test
    public void test1() throws Exception {

        String connectStr = "192.168.1.171:2181,192.168.1.172:2181,192.168.1.173:2181" ;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3) ;

        int sessionTimeoutMs = 3000 ;
        int connectionTimeoutMs = 4000 ;
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectStr, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
        client.start(); //开启这个链接


        InetAddress host = InetAddress.getLocalHost();
        String ip = host.getHostAddress();

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/spider/"+ip) ;

        while (true){
            Thread.sleep(50000);
        }
    }
}
