
package com.cuiyp.demo.factory;

public class BlackHuman implements IHuman {
    private String color;

    @Override
    public void getColor() {
        System.out.println("黑色");
    }

    @Override
    public void talk(){
        System.out.println("黑人说话---------");
    }
    public BlackHuman(){

    }
}
