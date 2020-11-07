package com.cuiyp.demo.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HumanFactory<T> extends AbstractHumanFactory<T> {

    @Override
    public <T extends IHuman> T createHuman(Class<T> c) {
        IHuman human = null;
        try {
            human = (T) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) human;
    }
}
