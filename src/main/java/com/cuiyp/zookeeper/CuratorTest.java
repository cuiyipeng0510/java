package com.cuiyp.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorTest {
    private final static String config = "172.16.34.41:2181,172.16.34.238:2181,172.16.15.190:2181,172.16.15.191:2181";

    public static CuratorFramework getCf(){
        RetryPolicy backoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(config, 600000, 600000, backoffRetry);
        client.start();
        return client;
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework cf = getCf();
        InterProcessMutex lock = new InterProcessMutex(cf, "/lock");
        lock.acquire();
        lock.release();
    }
}
