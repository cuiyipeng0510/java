package com.cuiyp.demo.demo;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class Tuoxiaoxiao {


    public static void main(String[] args) throws InterruptedException, ExecutionException {


//        getScheduledTPETest();
        getThreadPoolExecutor();

        phaser.bulkRegister(7);
        for (int i=0;i<5;i++){

            new Thread(new Person("people" + i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }


    private static void getThreadPoolExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println("在线程池里开始执行线程了");
            });
        }
        System.out.println("执行完了");
    }

    private static void getScheduledTPETest() throws ExecutionException, InterruptedException {

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
//        scheduledThreadPoolExecutor.schedule(()-> System.out.println("输出延迟2s立即执行"),2,TimeUnit.SECONDS);
//
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(()-> System.out.println("输出延迟1后立即执行，此后每一秒执行一次"),1,1,TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("输出延迟两秒执行，包含睡眠时间，此后每隔两秒输出");
        }, 1, 3, TimeUnit.SECONDS);

    }


    static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();

    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人离开了" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人到齐了" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃饭了" + registeredParties);
                    System.out.println();
                    return false;

                case 3:
                    System.out.println("婚礼结束，新郎新娘抱抱" + registeredParties);
                    System.out.println();
                    return false;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable{
        private String name;

        Person(String name){
            this.name = name;
        }

        private void hug() throws InterruptedException {
            if (name.equals("新郎")|| name.equals("新娘")){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "抱抱");
                phaser.arriveAndAwaitAdvance();
            }else phaser.arriveAndDeregister();
        }

        private void arrival() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(name + "到达");
            phaser.arriveAndAwaitAdvance();
        }
        private void eat() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(name + "%s 吃饭");
            phaser.arriveAndAwaitAdvance();
        }

        private void leave() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(name + "离开");
            phaser.arriveAndAwaitAdvance();
        }

        @Override
        public void run() {

            try {
                arrival();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                leave();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                hug();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static void LockSupport(){
        Thread t2 = new Thread();
        char[] a = "1234567".toCharArray();
        char[]b = "abcdefg".toCharArray();
        Thread t1 = new Thread(()->{
            for (char i:a){
                System.out.println(i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t1.start();


        t2.start();
    }
}
