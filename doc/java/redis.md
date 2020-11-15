![image-20200808202128188](D:\工具\Typora\document\images\redis概念.png)

单 worker 线程 串行处理

秒杀活动： 实际落地   交易系统   实际到 db落地

redis 本地方法

![image-20200808203606409](D:\工具\Typora\document\images\redis本地方法.png)



**setbit**

![image-20200808212046258](D:\工具\Typora\document\images\字节扩展.png)

![image-20200808212326374](D:\工具\Typora\document\images\二进制扩容查询.png)

**场景：** 

- 根据：任意时间窗口内、登录用户数统计
- 存储：数据细节：日志分析 
- DB：慢、时间戳
- 统计： 

![image-20200808213306370](D:\工具\Typora\document\images\二进制计算。统计数.png)

**redis 二进制 bitmap  （二进制计算）** 

12306 购票系统

redis 锁

redis 做幂等性操作

布隆过滤器算法（数据淘汰算法）  LRU/LFU

**费曼学习法** 技术是死的 给别人讲明白





![image-20200808222350793](D:\工具\Typora\document\images\系统架构-倒三角.png)

![image-20200808224126839](D:\工具\Typora\document\images\传统项目转型.png)



set 集合 做业务拆分

**srandmember  key count**   使用场景  抽奖：  中奖人 个数有限且不能重复 （使用正数）  量大可重复中奖 使用负数

count 为正数  返回的是 无序 去重集合

count 为负数 返回的是 无序 随机个数集合、有可能一个元素出现多次



差集：

使用场景：  推荐系统、 共同好友：交集、推荐好友： 差集

![image-20200809204615383](D:\工具\Typora\document\images\redis差集.png)

排序：

​	**备注：**

score 分值

rank：等级、归类

![image-20200809205251201](D:\工具\Typora\document\images\redis-zset排序.png)



**排名：**

![image-20200809205848429](D:\工具\Typora\document\images\redis-zset排序-倒排.png)



redis  zse 怎么保证排序、怎么做的、性能如何

**redis持久化**  镜像 日志

数据丢失 操作系统内核、单位时间产生的数据量

![image-20200809213317046](D:\工具\Typora\document\images\redis-AOF-日志持久化.png)



redis  AOF 默认关闭、什么场景会开启



集群方式：

![image-20200809214413205](D:\工具\Typora\document\images\redis集群-主从-分片.png)

![image-20200809214515009](D:\工具\Typora\document\images\redis-集群整合使用方式.png)

![image-20200809214736251](D:\工具\Typora\document\images\redis-单点故障.png)

redis 业务拆分

sharding 分片  x、y、z 拆分

x 全量 主备（高可用）

y 业务拆分

z sharding 分片、热点数据服务拆分

![image-20200809215704863](D:\工具\Typora\document\images\redis-AKF-实例拆分-不同数据存不同位置.png)



![image-20200809221402771](D:\工具\Typora\document\images\redis集群-客户端自己实现.png)

![image-20200809220257851](D:\工具\Typora\document\images\代理拆分.png)



redis 自己解决高并发 

![image-20200809221030667](D:\工具\Typora\document\images\redis-集群自己处理方式-哨兵-Map映射.png)

![image-20200809221141618](D:\工具\Typora\document\images\redis-哨兵用的raft--哨兵.png)