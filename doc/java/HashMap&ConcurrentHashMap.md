## HashMap&ConcurrentHashMap

时间复杂度：

空间复杂度：



hashCode（）计算方式

数组 、链表、 java7 红黑树

红黑树特性

为啥用数组：速度 读--写

数组 -> O（1）

知道索引 HashMap<?,?>

索引计算：

Object.hashCode() = 固定长度的hash值

**Object.hashCode() % Object[].length()  -> [0,  length - 1]**

hash 冲突  

1.  在散列法 （再次进行hash值计算）

2. 链表法  插入 **（链表头部最快）** --------- 头结点直接赋值到 数组对应位置、链表直接移动

   1. 右移 异或

   

   & 与操作  （二进制、 都为 1 才为1）

   ![image-20200810005910392](images\与操作.png)

   ![image-20200810002044917](images\hashMap-插入同一位置数据处理方式.png)

   ![image-20200810002322151](images\hashMap链表移动.png)

数组 + 链表

![image-20200809005257702](images\红黑树.png)

链表：



效率： 位运算 是 取模运算的 十倍

![image-20200809191427931](images\hashMap工作原理jdk1.7.png)

![image-20200809224740858](images\hashmap链表转红黑树.png)





hashMap 线程问题 循环链表  扩容死循环问题