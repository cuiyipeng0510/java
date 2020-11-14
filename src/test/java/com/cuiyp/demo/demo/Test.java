package com.cuiyp.demo.demo;

import com.cuiyp.demo.factory.*;

public class Test {
    public void getCount(int k) {
        m();
        new Test();
        n();
        new Test(1);
    }

    private void m() {
    }
    public final void n(){}

    public Test(){}
    public Test(int i){}

    public static void main(String[] args) {
        new Test().getCount(1);
        AbstractHumanFactory humanFactory = new HumanFactory();
        System.out.println("-----------------");
        IHuman yellow = humanFactory.createHuman(YellowHuman.class);
        yellow.talk();
        yellow.getColor();
        System.out.println("-----------------");
        IHuman black = humanFactory.createHuman(BlackHuman.class);
        black.talk();
        black.getColor();

        System.out.println("-----------------");
        IHuman white = humanFactory.createHuman(WhiteHuman.class);
        white.talk();
        white.getColor();
    }
}
