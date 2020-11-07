package com.cuiyp.demo.factory;

/**
 * 接口隔离原则（）
 * 定义接口公共属性与方法，行为与属性拆分
 * 对扩展开放，对修改关闭，封装可能引起变化的因子
 */
public interface IHuman {
    void getColor();
    void talk();
}
