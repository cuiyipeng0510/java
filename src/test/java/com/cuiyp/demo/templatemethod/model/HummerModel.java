package com.cuiyp.demo.templatemethod.model;

import com.cuiyp.demo.templatemethod.ICarModel;

public class HummerModel implements ICarModel {
    @Override
    public void start() {
        System.out.println("悍马启动");
    }

    @Override
    public void alarm() {
        System.out.println("响喇叭");
    }

    @Override
    public void engineBoom() {
        System.out.println("发动机 轰轰轰....");
    }

    @Override
    public void stop() {
        System.out.println("刹车....");
    }

    @Override
    public void run() {
        this.start();
        this.alarm();
        this.engineBoom();
        this.stop();
    }
}
