package com.cuiyp.demo.leetcode.queue.catordog;

public abstract class Pet {
    protected String type;
    public Pet(String type){
        this.type = type;
    }
    abstract String getType();
}
