# maven 基础

## 1.坐标：

​	举例： struts2-core-2.3.24.jar

​	apache(公司名称)+struts2(项目名称)+2.3.24(版本)

### 核心：

1. 依赖管理--jar管理过程

 	2. 项目构建--项目编码完成后 进行编译，测试，打包，部署一系列操作都可以同步命令实现

发布 进入项目制定路径，mvn tomcat:run

## 2，maven安装,配置本地库

1. 配置环境变量 mvn -v
2. 配置本地仓库
3. 仓库分类： 本地仓库，私服， 中央仓库

## 3.maven命令

1. clean：清理  mvn clean  清理target目录

2. compile:编译  mvn compile  java文件编译为class文件

3. test:单元测试  mvn test (先编译，后测试) 

   单元测试类名要求 XxxTest.jar

   将项目根目录src;/test/java目录下的单元测试类都回执行

4. package:打包 mvn package

5. install：安装 mvn install

## 4.maven生命周期

​	三套生命周期 在一套生命内，执行后面的命令，前面的操作会自动执行

CleanLifeCyle: 清理生命周期   clean

defaultLifeCycle:默认生命周期

​	compile, test , package install , deploy

siteLifeCyle: 站点生命周期  site

## 5. 项目中配置maven环境

 	1. 指定maven路径
 	2. 添加setting文件
 	3. 构建索引

