package com.cuiyp.demo.designmode;

/**
 * 单例模式优缺点：
 *  优点：
 *      1. 内存中只有一个实例，减少内存开销（特别是对像需要频繁创建，销毁）
 *      2. 避免对资源多重占用：比如对一个文件进行写操作，由于只有一个实例在内存中，避免对同一个文件同时写
 *  缺点：
 *      1. 没有接口，扩展不易
 *      2. 对测试不利，在并行开发环境中，如果单例模式没有完成，是不能进行测试的，没有接口也不能使用mock的方式虚拟一个对象
 *      3. 与单一职责有冲突，单例模式把 “要单例” 和业务逻辑融合在一个类中
 *  使用场景：（系统中要求有且只有一个对象）
 *      1. 要求生成唯一序列号
 *      2. 创建一个对象需要消耗资源过多，如：需要访问 IO 和 数据库资源
 *      3. 需要定义大量的静态常量和静态方法（工具类）可以采用单例（也可以直接声明为 static）
 */
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
