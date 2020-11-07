package com.cuiyp.demo.designmode;

public class SingleMode {

    private volatile static SingleMode singleMode;

    private SingleMode(){}

    /**
     * DCL
     * @return singleMode
     */
    public SingleMode getSingleMode(){
        if(singleMode == null){
            synchronized (SingleMode.class){
                if(singleMode == null){
                    singleMode = new SingleMode();
                }
            }
        }
        return singleMode;
    }
}
