package com.cuiyp.demo.factory;

public class WhiteHuman implements IHuman{
    @Override
    public void getColor() {
        System.out.println("白鬼");
    }

    @Override
    public void talk() {
        System.out.println("美国渣渣----");
    }
}
