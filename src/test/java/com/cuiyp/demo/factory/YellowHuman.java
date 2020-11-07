package com.cuiyp.demo.factory;

public class YellowHuman implements IHuman{
    @Override
    public void getColor() {
        System.out.println("黄皮肤");
    }

    @Override
    public void talk() {
        System.out.println("华夏---");
    }
}
