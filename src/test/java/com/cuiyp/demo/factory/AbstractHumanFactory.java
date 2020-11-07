package com.cuiyp.demo.factory;

public abstract class AbstractHumanFactory<T> {
    public abstract <T extends IHuman> T createHuman(Class<T> c);
}
