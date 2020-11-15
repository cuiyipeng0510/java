
package com.cuiyp.demo.factory;

public class BlackHuman implements IHuman {
    private String color;
    private String sex;

    public void setColor(String color) {
        this.color = color;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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
