package com.cuiyp.demo.leetcode.queue.catordog;

public class Dog extends Pet {

    public Dog(String type) {
        super(type);
    }

    @Override
    String getType() {
        return this.type;
    }
}
