## 内容概要

#### 主要分为两篇：

- 第1篇 MyBatis 3源码篇（第1~11章），主要介绍MyBatis框架各个特性的源码实现；
- 第2篇章 MyBatis Spring源码篇（第12~13章），主要介绍MyBatis框架与Spring框架整合的原理及MyBatis Spring模块的实现细节。下面是本书的内容大纲。

```
1. 第1章　搭建MyBatis源码环境主要介绍如何搭建MyBatis源码调试环境，包括MyBatis框架源码获取途径、如何导入集成开发工具以及如何运行MyBatis源码中的测试用例。
2. 第2章　JDBC规范详解MyBatis框架是对JDBC轻量级的封装，熟练掌握JDBC规范有助于理解MyBatis框架实现原理。本章将详细介绍JDBC规范相关细节，已经全面掌握JDBC规范的读者可以跳过该章。
3. 第3章　MyBatis常用工具类介绍MyBatis框架中常用的工具类，避免读者因对这些工具类的使用不熟悉而导致对框架主流程理解的干扰，这些工具类包括MetaObject、ObjectFactory、ProxyFactory等。
4. 第4章　MyBatis核心组件介绍介绍MyBatis的核心组件，包括Configuration、SqlSession、Executor、MappedStatement等，包括这些组件的作用及MyBatis执行SQL语句的核心流程。
5. 第5章　SqlSession的创建过程主要介绍SqlSession组件的创建过程，包括MyBatis框架对XPath方式解析XML封装的工具类、MyBatis主配置文件解析生成Configuration对象的过程。
6. 第6章　SqlSession执行Mapper过程本章介绍Mapper接口注册的过程、SQL配置转换为MappedStatement对象并注册到Configuration对象的过程。除此之外，本章还将介绍SqlSession对象执行Mapper的过程。
7. 第7章　MyBatis缓存本章首先介绍MyBatis一级缓存和二级缓存的使用细节，接着介绍一级缓存和二级缓存的实现原理，最后介绍MyBatis如何整合Redis作为二级缓存。
8. 第8章　MyBatis日志实现基于Java语言的日志框架比较多，比较常用的有Logback、Log4j等，本章介绍Java的日志框架发展史，并介绍这些日志框架之间的关系，最后介绍MyBatis自动查找日志框架的实现原理。
9. 第9章　动态SQL实现原理本章主要介绍MyBatis动态SQL的使用、动态SQL配置转换为SqlSource对象的过程以及动态SQL的解析原理，最后从源码的角度分析动态SQL配置中#{}和${}参数占位符的区别。
10. 第10章　MyBatis插件原理及应用本章介绍MyBatis插件的实现原理，并以实际的案例介绍如何自定义MyBatis插件。在本章中将会实现两个MyBatis插件，分别为分页查询插件和慢SQL统计插件。
11. 第11章　MyBatis级联映射与懒加载本章介绍MyBatis中一对一、一对多级联映射和懒加载机制的使用细节，并介绍级联映射和懒加载的源码实现。
12. 第12章　MyBatis与Spring整合案例在介绍MyBatis框架与Spring整合原理之前，需要了解MyBatis整合Spring的基本配置，本章以一个用户注册RESTful接口案例作为MyBatis框架与Spring框架整合的最佳实践。
13. 第13章　MyBatis Spring的实现原理首先介绍Spring框架中的一些核心概念和Spring IoC容器的启动过程，接着介绍MyBatis和Spring整合后动态代理产生的Mapper对象是如何与Spring Ioc容器进行关联的，最后介绍MyBatis整合Spring事务管理的实现原理。
```

### 优点：

- 消除大量JDBC冗余代码，包括参数设置，结果集封装

- SQL语句更加灵活，方便查询优化

- 学习成本低，方便新用户快速学习

- 提供主流IOC框架Spring集成支持

- 引入缓存机制，提供与第三方缓存类库集成支持

  ```
  1. https://github.com/mybatis/mybatis-3 
  2. https://github.com/mybatis/spring
  3. https://github.com/mybatis/parent
  ```

  