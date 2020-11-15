![image-20200808235539151](D:\工具\Typora\document\images\ioc工作原理.png)

Spring 加载Bean 容器方式

1. 加载配置文件 spring.xml
2. 类上注解+ 构造方法注解
   - 类上注解 @ComponentScan、@Import(类名.class)
   - 构造方法注解 @Bean
3. 



![image-20200809002248787](D:\工具\Typora\document\images\spring加载流程图1.png)



**spring循环依赖：**

源码顺序：refresh() 13个关键方法

![image-20200812205533283](D:\工具\Typora\document\images\spring创建Bean过程.png)



![image-20200812210953765](D:\工具\Typora\document\images\spring三级缓存核心代码.png)



![image-20200812213044132](D:\工具\Typora\document\images\spring三级缓存放置内容.png)



**实例化 与 初始化环节拆分**



![image-20200812215225125](D:\工具\Typora\document\images\三级缓存问题解释.png)