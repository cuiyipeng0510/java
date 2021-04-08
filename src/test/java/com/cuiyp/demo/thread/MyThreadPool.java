package com.cuiyp.demo.thread;

import jdk.internal.org.objectweb.asm.TypeReference;

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
                Executors.defaultThreadFactory(), // 自定义线程工厂
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolExecutor getThreadPools(){
        return threadPools;
    }

    // sleep 不会释放锁
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(10000);
                System.out.println("===");
            }catch (InterruptedException e){
                System.out.println("InterruptedException");
            }
        });
        t.start();
        Thread.sleep(100);
        t.interrupt();
    }
}
