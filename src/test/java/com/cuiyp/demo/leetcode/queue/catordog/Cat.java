package com.cuiyp.demo.leetcode.queue.catordog;

public class Cat extends Pet {

    public Cat(String type) {
        super(type);
    }

    @Override
    String getType() {
        return this.type;
    }
}
