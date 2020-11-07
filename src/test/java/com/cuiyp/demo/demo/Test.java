package com.cuiyp.demo.demo;

import com.cuiyp.demo.factory.*;

public class Test {
    public static void main(String[] args) {
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
