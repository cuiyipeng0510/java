### 第六章 线程池与Future

#### 1. 线程池实现原理

- 容器中有一组线程 **+** 队列，调用方往队列里添加任务，线程不断从队列里取任务执行 （生产 -- 消费者模型）

**需要考虑问题：**

1. 线程数是固定的还是动态的，需要设置多少线程数
   1. 设置核心线程数，最大线程数
   2. N = N(cpu) * U(cpu) * (1 + W/C)
      - cpu 核数 （ **Runtime.getRuntime().availaableProcessors()** ）
      - U(cpu) CPU使用率 (0, 1)
      - waiting / count（等待时间 / 计算时间）
2. 队列大小怎么设置，无界调用放一直调用可能会导致内存耗尽，有界队列，队列满了，新添加的任务需要怎么处理
3. 提交任务时候 新开线程，还是添加队列
4. 当没有任务时候，线程是睡眠一小段时间？还是进入阻塞？如果进入阻塞，如何唤醒？
   - 使用阻塞队列

#### 类继承关系

![](ScheduleThreadPoolExecutor类图.png)

##### ThreadPooleExecutor (参数详解)

1. corePoolSize // 核心线程数
2. maximumPoolSize // 最大线程数
3. keepAliveTime // 没有任务时 线程活跃时间
4. nuit //
5. workQueue // 阻塞队列
6. threadFactory // 指定线程工厂
7. handler // 拒绝策略
   1. jdk默认提供了四种（ThreadPooleExecutor 的内部类 都实现了 RejectedExecutionHandler接口 ）
      - 交给调用方执行 -- CallerRunsPolicy
      - 抛出异常 --- AbortPolicy
      - 悄咪咪的吃掉 ---- DiscardPolicy
      - 丢掉等待时间最长的任务 --- DiscardOldPolicy

