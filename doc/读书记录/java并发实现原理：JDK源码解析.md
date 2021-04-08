### 第一章 多线程基础

并发编程的三大特性

- 可见性 (visibility)
- 有序性 (ordering)
- 原子性 (atomicity)

#### 1. 线程间通信

- 方式1  wait()**/**notify() 必须与 synchronized 配合使用

  - wait()  --- 线程阻塞进入等待状态

  - notify() --- 唤醒 waitting 状态中的任意一个线程  notifyAll() -- 唤醒所有

    ```java
    // 这两个方法必须 配合 synchronized 关键字使用
    // wait() 方法内部会先释放锁，然后进入阻塞状态
    wait(){
    	// 释放锁
    	//阻塞，等待被其他线程 notify()
    	// 重新拿锁
    }
    ```

    **总结：**
    
    ```java
    Java中的多线程是一种抢占式的机制，而不是分时机制。抢占式的机制是有多个线程处于可运行状态，但是只有一个线程在运行。 
    共同点 ： 
    1. 他们都是在多线程的环境下，都可以在程序的调用处阻塞指定的毫秒数，并返回。 
    2. wait()和sleep()都可以通过interrupt()方法 打断线程的暂停状态 ，从而使线程立刻抛出InterruptedException。 
    如果线程A希望立即结束线程B，则可以对线程B对应的Thread实例调用interrupt方法。如果此刻线程B正在wait/sleep/join，则线程B会立刻抛出InterruptedException，在 catch() {} 中直接return即可安全地结束线程。 
    需要注意的是，InterruptedException是线程自己从内部抛出的，并不是interrupt()方法抛出的。对某一线程调用 interrupt()时，如果该线程正在执行普通的代码，那么该线程根本就不会抛出InterruptedException。但是，一旦该线程进入到 wait()/sleep()/join()后，就会立刻抛出InterruptedException 。 
    不同点 ： 
    1.每个对象都有一个锁来控制同步访问。Synchronized关键字可以和对象的锁交互，来实现线程的同步。 
    sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法。 
    2.wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用 
    3.sleep必须捕获异常，而wait，notify和notifyAll不需要捕获异常 
    4.sleep是线程类（Thread）的方法，导致此线程暂停执行指定时间，给执行机会给其他线程，但是监控状态依然保持，到时后会自动恢复。调用sleep不会释放对象锁。
    5.wait是Object类的方法，对此对象调用wait方法导致本线程放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象发出notify方法（或notifyAll）后本线程才进入对象锁定池准备获得对象锁进入运行状态
    ```
    
    

- Condition机制  --- 必须和 Lock 一起使用

-  [LockSupport 实现方式](https://www.cnblogs.com/gxyandwmm/p/9419129.html)

  - **总结一下，LockSupport 比 Object的wait/notify有两大优势**：
  
    ①LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
    
    ②unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
  
- 多次调用unpark方法和调用一次unpark方法效果一样，因为都是直接将_counter赋值为1，而不是加1。简单说就是：线程A连续调用两次LockSupport.unpark(B)方法唤醒线程B，然后线程B调用两次LockSupport.park()方法， 线程B依旧会被阻塞。因为两次unpark调用效果跟一次调用一样，只能让线程B的第一次调用park方法不被阻塞，第二次调用依旧会阻塞。

  

### 第二章  原子类（Atomic）

**Unsafe 类中 CAS实现原子操作**

#### 1. ABA 问题

1. AtomicStampedReference 这个类中 CAS 操作有四个参数，

   ```java
   // 前两个参数比较修改值
   // 后两个参数比较版本号 解决 ABA问题
   public boolean compareAndSet(V   expectedReference,
                                    V   newReference,
                                    int expectedStamp,
                                    int newStamp) {
           Pair<V> current = pair;
           return
               expectedReference == current.reference &&
               expectedStamp == current.stamp &&
               ((newReference == current.reference &&
                 newStamp == current.stamp) ||
                casPair(current, Pair.of(newReference, newStamp)));
       }
   ```

2. AtomicMarkableReference 

   1. 原理与 AtomicStampedRefference 这个类似 **AtomicMarkableReference** 对象里的版本号是 boolean类型 只能表示两中状态，可以有效缓解ABA问题，但是无法避免

#### Striped64 与 LongAdder

1. 大数字  分而治之 ---  把一个Long类型拆成一个 base变量外加多个 Cell，每个Cell包装一个Long变量，多个线程并发累加时候并发度





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
3. keepAliveTime // maxPoolSize 中空闲线程 销毁所需要的时间
4. TimeUnit // **TimeUnit.MILLISECONDS** 
5. workQueue // 阻塞队列 
   1. ArrayBlockingQueue -- 循环数组 + 一个锁 + 两条件 （典型生产者，消费者模型）
   2. LinkedBlockingQueue -- 单链表 + 两个锁 + 一个条件
   3. PriorityBlockingQueue -- 数组，堆排序（小根堆）
   4. DelayQueue -- 延迟队列 元素必须实现 Delay接口 与Comparable接口
   5. SynchronousQueue --- 特殊的BlockingQueue 有公平/非公平策略 （单链表队列/栈）
6. threadFactory // 指定线程工厂
7. handler // 拒绝策略
   1. jdk默认提供了四种（ThreadPooleExecutor 的内部类 都实现了 RejectedExecutionHandler接口 ）
      - 交给调用方执行 -- CallerRunsPolicy
      - 抛出异常 --- AbortPolicy
      - 悄咪咪的吃掉 ---- DiscardPolicy
      - 丢掉等待时间最长的任务 --- DiscardOldPolicy

 **参数运作流程:** 

1. step1: 判断当前线程数是否大于或等于corePoolSize。如果小于，则新建线程执行；如果大于，则进入step2。

2. step2: 判断当前队列是否已满，未满丢队列，满了进入step3

3. step3: 判断当前线程数是否大于maxPoolSize 如果小于 新建线程执行，如果大于则进入 step4

4. step4: 执行拒绝策略

   ```
   首先判断corePoolSize，其次判断blockingQueue是否已满，接着判断maxPoolSize，最后使用拒绝策略。很显然，基于这种流程，如果队列是无界的，将永远没有机会走到step 3，也即maxPoolSize没有使用，也一定不会走到step 4。
   ```

   

#### 2. 四种并发模型

1. 并行工作者（Parallel ） --- 传入的任务被分配到不同的工作者上
   - 优点，添加更多工作者来提高系统并行度，
   - 缺点：（业务数据，缓存数据，数据库连接池等）
     - 共享状态数据的同步 会比较复杂，对共享数据的操作要保证线程可见，避免资源竞争，死锁等问题
     - 访问共享数据，线程之间的相互竞争，资源 性能损耗，出现一定程度的串行化
     - 任务执行顺序不确定
2. 流水线模型 （Callback） --- 无共享并行模型
   - 类似生产车间流水线上的工人，每个工人只负责一部分任务，当完成自己任务时，会转发给下一个工作者
   - 优点：
     - 无共享状态，无需考虑并发访问共享数据产生的并发性问题，基本类似于单线程操作
     - 合理的作业顺序
   - 缺点：
     - 回调函数嵌套问题
3. 函数式并行模型



#### 3. 阻塞队列（BlockingQueue）

##### 1 入队方法有  

- ##### add(), 返回值类型是 boolean  底层调用的是 offer()   --- 无阻塞

  ```java
      public boolean add(E e) {
          if (offer(e))
              return true;
          else
              throw new IllegalStateException("Queue full");
      }
  ```

  

- #####  offer(), 返回值类型是 boolean    --- 无阻塞

  ```java
      public boolean offer(E e) {
          checkNotNull(e);
          final ReentrantLock lock = this.lock;
          lock.lock();
          try {
              if (count == items.length)
                  return false;
              else {
                  enqueue(e);
                  return true;
              }
          } finally {
              lock.unlock();
          }
      }
  ```

  

- #####  put(), 无返回值，会抛出中断异常   --- 阻塞式

  ```java
      public void put(E e) throws InterruptedException {
          checkNotNull(e);
          final ReentrantLock lock = this.lock;
          lock.lockInterruptibly();
          try {
              while (count == items.length)
                  notFull.await();
              enqueue(e);
          } finally {
              lock.unlock();
          }
      }
  ```

##### 2. 出队方法

- remove（） --- 非阻塞式
- peek（）--- 非阻塞式
- take（）--- 阻塞式

#### 2.  ArrayBlockingQueue

##### - 数组实现的环形队列

```java
//核心数据结构 一把锁 + 两条件
final ReentrantLock lock;
private final Condition notEmpty;
private final Condition notFull;
final Object [] items;
int takeIndex;
int putIndex;
int count;
//构造函数
public ArrayBlockingQueue(int capacity, boolean fair) {
    if (capacity <= 0)
        throw new IllegalArgumentException();
    this.items = new Object[capacity];
    lock = new ReentrantLock(fair);
    notEmpty = lock.newCondition();
    notFull =  lock.newCondition();
}
```

#### 3. LinkedBlockingQueue

- 基于单向链表的阻塞队列。队头和队尾由2个指针分开操作的，所以用了2把锁+2个条件，同时有1个AtomicInteger的原子变量记录count数。

  ```java
  private final AtomicInteger count = new AtomicInteger();
  private final ReentrantLock takeLock = new ReentrantLock();
  private final Condition notEmpty = takeLock.newCondition();
  private final ReentrantLock putLock  = new ReentrantLock();
  private final Condition notFull = putLock.newCondition();
  
  // 构造函数 -- 可指定容量
  public LinkedBlockingQueue() {
      // 不指定 默认int最大值
      this(Integer.MAX_VALUE);
  }
  
  public LinkedBlockingQueue(int capacity) {
      if (capacity <= 0) throw new IllegalArgumentException();
      this.capacity = capacity;
      last = head = new Node<E>(null);
  }
  ```

  1.  为了提高并发度，用2把锁，分别控制队头、队尾的操作。意味着在put（..）和put（..）之间、take（）与take（）之间是互斥的，put（..）和take（）之间并不互斥。但对于count变量，双方都需要操作，所以必须是原子类型

  2. 因为各自拿了一把锁，所以当需要调用对方的condition的signal时，还必须再加上对方的锁，就是signalNotEmpty（）和signalNotFull（）函数。示例如下所示。

     ```java
     /**
      * Signals a waiting take. Called only from put/offer (which do not
      * otherwise ordinarily lock takeLock.)
      */    
     private void signalNotEmpty() {
         final ReentrantLock takeLock = this.takeLock;
         takeLock.lock();
         try {
             notEmpty.signal();
         } finally {
             takeLock.unlock();
         }
     }
     
     /**
      * Signals a waiting put. Called only from take/poll.
      */
     private void signalNotFull() {
         final ReentrantLock putLock = this.putLock;
         putLock.lock();
         try {
             notFull.signal();
         } finally {
             putLock.unlock();
         }
     }
     ```

     

  3. 不仅put会通知take，take 也会通知put。当put 发现非满的时候，也会通知其他put线程；当take发现非空的时候，也会通知其他take线程。

#### 4. PriorityBlockingQueue --- 优先队列

- PriorityQueue（**小根堆排序**） 按照元素优先级从小到大出队列，队列中元素需要比较大小，并实现 Comparable 接口

- 内部实现方式与 ArrayBlockingQueue 类似

- 区别在于：无 notFull 条件，元素个数超出数组长度会自动扩容

  ```java
  // 构造函数
  public PriorityBlockingQueue() {
      // 初始容量默认 11
      this(DEFAULT_INITIAL_CAPACITY, null);
  }
  // 可以指定初始容量
  public PriorityBlockingQueue(int initialCapacity) {
      this(initialCapacity, null);
  }
  // 需要制定比较器
  public PriorityBlockingQueue(int initialCapacity,
                               Comparator<? super E> comparator) {
      if (initialCapacity < 1)
          throw new IllegalArgumentException();
      this.lock = new ReentrantLock();
      this.notEmpty = lock.newCondition();
      this.comparator = comparator;
      this.queue = new Object[initialCapacity];
  }
  ```

#### 5. DelayQueue --- 延迟队列

- 按照延迟时间从小到大出队列

- 添加该队列的元素必须实现 **Delayed** 接口 与 Comparable 接口

  1. 如果getDelay的返回值小于或等于0，则说明该元素到期，需要从队列中拿出来执行。
  2. 该接口首先继承了Comparable 接口，所以要实现该接口，必须实现Comparable 接口。具体来说，就是基于getDelay（）的返回值比较两个元素的大小。

  ```java
  //存放元素泛型指定上界
  public class DelayQueue<E extends Delayed> extends AbstractQueue<E>
  implements BlockingQueue<E> {
      // 使用 一把锁 + 非空条件
      private final transient ReentrantLock lock = new ReentrantLock();
      private final Condition available = lock.newCondition();
      // 核心数据结构 用 优先队列 -- 小根堆实现
      private final PriorityQueue<E> q = new PriorityQueue<E>();
  }
  ```

#### 6. SynchronousQueue -- 特殊的BlockingQueue

- 先调put（..），线程会阻塞；直到另外一个线程调用了take（），两个线程才同时解锁，
- 反之亦然。对于多个线程而言，例如3个线程，调用**3次put（..）**，3个线程都会阻塞；直到另外的线程调用**3次take（）**，**6个线程才同时解锁**，反之亦然。

- 公平--- 单链表实现的队列  TransferQueue
- 非公平 --- 栈结构 TransferStack



































