package com.cuiyp.demo.leetcode.queue.catordog;

public abstract class Pet {
    protected String type;
    public Pet(String type){
        this.type = type;
    }

    abstract String getType();

    private int timeStamp;

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
