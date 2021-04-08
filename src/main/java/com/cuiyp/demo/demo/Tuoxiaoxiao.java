package com.cuiyp.demo.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Tuoxiaoxiao {
    private static ReentrantLock lock = new ReentrantLock();

    private static volatile  boolean flag = true;

    private static Thread t1 = null;

    public static void main(String[] args) throws InterruptedException {
        InterruptTest();

    }

    static void InterruptTest() {
        t1 = new Thread() {
            public void run() {
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(1000);
                    System.out.println("t1 end");
                    if(t1.isInterrupted()){
                        System.out.println("11111111");
                        throw new InterruptedException("jjjj");
                    }
                } catch (InterruptedException e) {

                    System.out.println("t1 中断异常处理");
                } finally {
                    lock.unlock();
                }
                System.out.println("t1 执行结束");
            }
        };
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();

    }

    private static void thread3() throws InterruptedException {
        Thread t3 = new Thread(() -> {
            int i = 0;
            long starttime = System.currentTimeMillis();
           while (flag){
               i++;
           }
            System.out.println("t3 线程结束，i值为：" + i
                    + "用时：" + (System.currentTimeMillis() - starttime));
        });
           t3.start();
           TimeUnit.SECONDS.sleep(3);
           flag = false;
    }

}
