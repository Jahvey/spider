package com.xxo.spider;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.junit.Test;

/**
 *
 * Created by xiaoxiaomo on 2015/5/5.
 */
public class TestCurator {
	
	
	/**
	 * zk节点类型
	 * 
	 * 临时节点：EPHEMERAL
	 * 临时有序节点：EPHEMERAL_SEQUENTIAL
	 * 
	 * 永久节点：PERSISTENT
	 * 永久有序节点：PERSISTENT_SEQUENTIAL
	 * 
	 * @throws Exception
	 */
	@Test
	public void createNode() throws Exception {
		//指定zk集群的地址
		String connectString = "192.168.3.220:2181,192.168.3.221:2181,192.168.3.222:2181";
		//1000 ：代表是重试时间间隔     3：表示是重试次数
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//使用curator创建一个zk链接
		int sessionTimeoutMs = 5000;//这个值必须在4s--40s之间，表示是链接失效的时间
		int connectionTimeoutMs = 1000;//链接超时时间
		CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs , connectionTimeoutMs , retryPolicy);
		//启动链接
		client.start();
		
		InetAddress localHost = InetAddress.getLocalHost();
		String ip = localHost.getHostAddress();
		
		/**
		 * EPHEMERAL 临时节点
		 * EPHEMERAL_SEQUENTIAL 临时有序
		 * PERSISTENT 永久节点
		 * PERSISTENT_SEQUENTIAL 永久有序
		 */
		client.create()
			.creatingParentsIfNeeded()//如果父节点不存在，则创建,这个时候创建的这个父节点是永久节点
			.withMode(CreateMode.EPHEMERAL)//指定节点类型
			.withACL(Ids.OPEN_ACL_UNSAFE)//指定节点的权限信息
			.forPath("/spider/"+ip);//指定节点名称
		
		while (true) {
			Thread.sleep(10000);
		}
	}

}
