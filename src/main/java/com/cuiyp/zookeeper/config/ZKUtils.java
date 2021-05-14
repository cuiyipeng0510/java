package com.cuiyp.zookeeper.config;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author: cuiyp sequence
 * @create: 2019-09-20 20:08
 */
public class ZKUtils {

    private static ZooKeeper zk;

    private static String address = "172.16.34.41:2181,172.16.34.238:2181,172.16.15.190:2181,172.16.15.191:2181";

    private static DefaultWatch watch = new DefaultWatch();

    private static CountDownLatch init = new CountDownLatch(1);

    public static ZooKeeper getZK() {

        try {
            zk = new ZooKeeper(address, 1000, watch);
            watch.setCc(init);
            init.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zk;
    }


}
