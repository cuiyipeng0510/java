package com.cuiyp.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    private static final ThreadPoolExecutor threadPools;
    static {
        threadPools = new ThreadPoolExecutor(
                2,
                4,
                10l,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
