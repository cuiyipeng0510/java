### 第一章  多线程基础

#### 1.1  关闭线程

说明： 线程是 ”一段运行中的代码“

1. 线程任务正常执行完毕、会释放资源，如果是循环运行的线程，就需要用到线程间通信、主线程通知其推出
   - wait() --- > watiing 状态  （释放锁，释放资源）
   - sleep() ----> blocking  阻塞 （不释放资源）
   - notify() ----> 随机唤醒watiing状态线程、进入 runnable、或 
   
2. 调用Thread 类中方法手动关闭 
   1. stop()
   2. destory()
      - 不推荐使用、因为强制杀死运行中的线程、其中所使用的资源、链接不能正常关闭
   
3. 守护线程
   1. setDaemon(true) ---》设置守护线程 <垃圾回收线程、就是守护线程>
   2. java中默认开启的都是非守护线程、当所有的非守护线程推出之后、整个jvm就退出了
   
4. interruptedException()、与 interrupt();
   1.  只有定义了《interruptedException 》该异常的方法才会抛出
   2. interrupt() Thread类中 中断线程的方法状态的方法
      1. 中断被调用线程的内部状态
   
5. **volatile** 关键字

   1. 最终一致性：线程A设置boolean值为true、线程B立即去读、读到的还是false、但之后能读到true

      - 实现一把自旋锁、会出现线程A设置为true、线程B读到的的是false 就会产生两个线程拿到同一把锁的问题

        ```java
        long a = 100;
        // jvm 规范没有要求 64位的long或Long 写入是原子性
        // 在32位操作系统中 给long对象 a 赋值 一个64位的变量可能被拆分为 两个32位的写操作来执行、这样读取的线程可能会读到一半
        ```

   2. 强一致性：

   3. 内存可见性：指的是 **“写完之后立即对其他线程可见”**，它的反面不是 **“不可见”** 而是 **“稍后才能可见”** 

      - 给变量添加  **volatile** 关键字即可解决该问题

   4. **重排序：DCL 问题 （Double Checking Locking）**

      - 对象初始化涉及三个步骤

        1. 分配内存空间
        2. 初始化对象属性
        3. 将对象内存地址指向对象引用

      - **构造函数溢出问题原理**： 解决方案、为 instance 对象加  **volatile** 关键字

        ```java
        //涉及问题：单例模式是线程安全的吗？怎么保证线程安全
        public class Sington{
        	private static Sington instance;
        	public static Sington getInstance(){
        		if(instance == null){
        			synchronized(Stington.class){ // 为了性能延迟使用锁 synchronized 
        				if(instance == null){
        					new Instance();  // 非线程安全、因为对象初始化涉及三个步骤、第二，第三步是无序的
                            // 如果线程A 正在先把对象内存地址指向引用、后初始化对象属性、线程B可能获取到一个未完全初始化的对象
        				}
        			}
        		}
        		return instance;
        	}
        }
        ```

   5. volatile关键字作用：

      1. 64位写入原子性
      2. 内存可见性
      3. 防止重排序

6. **JMM与happen-before**

   1. 存在内存可见性的原因
   
7. 线程池

   1. ExecutorService   
      - submit（Callable<T> callable）// 提交任务到线程池，异步处理（由线程池决定处理任务时间） 
   2. Future
      - future.get()  // 阻塞





 