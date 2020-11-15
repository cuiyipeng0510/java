# java代码读取文件问题解决

## 问题背景

通过java代码读取文件的方式有很多种，那么我们应该能理解，不同的场景应该使用不同的读取方式，我在前段时间开发项目的时候遇到一个问题：项目中需要读取xml文件和properties文件，代码示例如下：

```java
//读取resources目录下xml文件示例
try {
        Resource resource = new ClassPathResource("/writ/qw.xml");
        File file = resource.getFile();
        StringBuffer result = new StringBuffer();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
        BufferedReader br = new BufferedReader(reader);
        String s ;
     while((s=br.readLine())!=null){
           result = result.append(s);
     }
        br.close();
     }catch (Exception e){
        log.error("读取文件异常",e);
     }
```

```java
//读取resources目录下properties文件示例
File file = new File("src/test/resources/writ/test.properties");
Properties properties = new Properties();
properties.load(new FileInputStream(file));
String property = properties.getProperty("test");
```

我想这种写法应该是比较容易想到的读取文件的方式，而且本地自测没有问题，那么问题来了，当我们将项目打成jar（SpringBoot项目）部署测试发现文件读取不到，为什么呢？



## java代码读取文件方式分析



针对上述问题通过远程测试环境跟踪代码，发现jar包中的文件读取不到，这是什么情况，通过查询得知：原来项目打成jar包后，整个jar包就是一个文件，只能通过流的方式读取资源，而不能使用File来操作资源，也就是说如果读取的是本地或者服务器上的文件可以使用File来操作，而读取项目中的资源需要通过流来操作。话不多说看代码：

```java
//通过流读取properties文件，注意：DemoTest为本类类名
InputStream is = DemoTest.class.getResourceAsStream("/writ/test.properties");
Properties properties = new Properties();
properties.load(is);
String property = properties.getProperty("test");
```

```java
//通过流读取resource目录下的xml文件
Resource resource = new ClassPathResource("/writ/qw.xml");
InputStream is = resource.getInputStream();
StringBuffer result = new StringBuffer();
InputStreamReader reader = new InputStreamReader(is,"utf-8");
BufferedReader br = new BufferedReader(reader);
String s ;
while((s=br.readLine())!=null){
     result = result.append(s);
}
br.close();
reader.close();
```

通过修改代码成功解决问题。

**小知识：**文件分隔符注意

**windows:** 系统中目录通过 " \ "  分割，但是在linux系统中刚好与windows系统相反而是 “ / ”  所以文件在添加分割符的时候注意兼容性

例如：String  filePath = " D: " + File.separator  +  "games"   

在windows中读取路径为： D:\games

在linux中读取路径为：D:/games

如果String  =  filePath = "D :\games\lol.txt"，在windows系统中可以正常读取lol.txt，但是在linux系统中读取不到，linux系统中存储路径为"D:/games\lol.txt"，相当于在D盘下有一个文件名为："games\lol.txt"的文件，其实就是将 “ \ ”当做字符串与文件名合并成新的文件名，而没有将 “ \ ” 当做分割符。