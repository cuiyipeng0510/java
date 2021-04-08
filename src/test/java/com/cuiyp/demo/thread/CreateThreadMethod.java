package com.cuiyp.demo.thread;

import java.util.concurrent.*;

public class CreateThreadMethod {
    // 1. 继承Thread 重写run()
    static class MyThread extends Thread{
        @Override
        public void run(){
            System.out.println("MyThread");
        }
    }
    // 2. 实现 Runnable 接口 重写 run()
    static class MyThreadRunnable implements Runnable{
        @Override
        public void run(){
            System.out.println("MyThreadRunnable");
        }
    }

    // 3. 实现 Callable 接口 重写 call() 有返回值
    static class MyThreadCallable implements Callable<String> {
        @Override
        public String call(){
            System.out.println("MyThreadCallable");
            return "MyThreadCallable";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyThread mt = new MyThread();
        mt.setName("sss");
        mt.start();
        new Thread(()->{}).setName("sss");
        // Thread中不能直接添加 带返回值的 Callable，迂回添加，
        // 先构建 FutureTask -->可添加Callable实现类
        FutureTask<String> stringFutureTask = new FutureTask<>(new MyThreadCallable());
        Thread futureTask = new Thread(stringFutureTask);
        futureTask.start();
        stringFutureTask.get();

        ThreadPoolExecutor threadPools = MyThreadPool.getThreadPools();
        threadPools.execute(()-> System.out.println("MyThreadPools"));

        Future<String> submit = threadPools.submit(new MyThreadCallable());
        submit.get(); // 阻塞操作
    }
}
