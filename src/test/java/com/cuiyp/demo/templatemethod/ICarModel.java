package com.cuiyp.demo.templatemethod;

/**
 * 模板方法使用场景：
 * 1. 内部处理步骤相同
 * 2. 多个子类有共有的方法，并且逻辑基本相同
 * 3. 重要，复杂的算法，可以把核心算法设计为模板方法，周边相关细节由子类实现
 * 4. 重构时，把相同的代码抽取到父类中 然后通过钩子函数约束其行为
 */
public interface ICarModel {
    void start();
    void alarm();
    void engineBoom();
    void stop();

    void run();
}
