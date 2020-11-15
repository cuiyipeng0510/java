# JAVA基础培训总结

​	java基础是我们java程序员日常开发的必备技能，我主要对java基础培训过程中某些部分进行一些总结。

## 数据类型

### 基本数据类型

​	java数据类型是我们日常开发每天都会用到的，是整个java开发的基础。数据类型分为：基本数据类型和引用数据类型，基本数据类型有4类8种，除了基本数据类型外其他的都是引用类型，比如：字符串，数组，类，接口等等。

​	每一种基本数据类型都会对应各自的包装类，那么为什么要分基本数据类型和包装类呢？因为在java编程中基本数据类型应用非常普遍，如果每次都去new对象并存储到内存堆中， 消耗资源且效率低，而基本数据类型存储在栈中，栈的存取速度要比堆快，而且数据共享。当在一段代码块定义一个变量时，Java就在栈中 为这个变量分配内存空间，当该变量退出该作用域后，Java会自动释放掉为该变量所分配的内存空间，该内存空间可以立即被另作他用。但是基本数据类型只是纯粹的数据，我们知道java是一个面向对象的编程语言，基本数据类型不具有对象的性质，为了基本数据类型也具有对象的特性，就有了包装类，它相当于将基本数据类型“包装起来”，使它具有了对象的性质，并且为其添加了属性和方法，丰富了基本数据类型的操作。

如下是基本数据类型

| 基本类型 | 包装类    | 字节   | 位数  | 范围                       |
| -------- | --------- | ------ | ----- | -------------------------- |
| boolean  | Boolean   |        |       |                            |
| byte     | Byte      | 1 字节 | 8 位  | -128 ~ 127                 |
| char     | Character | 2 字节 | 16 位 | Unicode0 ~ Unicode(2^16)-1 |
| short    | Short     | 2 字节 | 16 位 | -2^15 ~ 2^15 - 1           |
| int      | Integer   | 4 字节 | 32 位 | -2^31 ~ 2^31 - 1           |
| long     | Long      | 8 字节 | 64 位 | -2^63 ~ 2^63 - 1           |
| float    | Float     | 4 字节 | 32 位 | IEEE754 ~ IEEE754          |
| double   | Double    | 8 字节 | 64 位 | IEEE754 ~ IEEE754          |

基本数据类型可以和包装类进行转换：装箱和拆箱

**自动装箱：**自动加基本数据类型转成包装类，如：Integer  i = 1，其实装箱过程是通过调用包装器的valueOf方法实现的；

**自动拆箱：**自动将包装类转成基本数据类型，如：Integer  i = 2；int  n = i，拆箱过程是通过调用包装器的 xxxValue方法实现的。（xxx代表对应的基本数据类型）。

对于基本数据类型我们常见的问题如下：

```java
public class Main {
    public static void main(String[] args) {
        Integer i1 = 100;
        Integer i2 = 100;
        
        Integer i3 = 200;
        Integer i4 = 200;
        
        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }
}
```

结果：

```
true
false
```

原因：其实查看Integer的ValueOf方法的源码就知道了

```java
public static Integer valueOf(int i) {
        if(i >= -128 && i <= IntegerCache.high){
            return IntegerCache.cache[i + 128];
        }else{
            return new Integer(i);
        }
}
```

如果i在[-128,127]集合内，返回指向的是IntegerCache.cache中已经存在的对象的引用，否则创建一个新的Integer对象，其实包装类的ValueOf方法的实现方式不相同，比如：Double每次都会new一个Double对象。以上Integer只是一个例子，如果想了解其他包装类的实现可以看看源码。

基本数据类型可以与各自的包装类进行转换，有些基本数据类型也可以相互转换

**注意：**

- 自动数据类型转换是数据范围从小到达转换，例如：long  num = 100，若数据范围从大到小自动转换就会报错，数据类型不兼容，如：int num = 100L，但是可以强制类型转换；
- 强制类型转换可能发生精度损失，数据溢出，不推荐使用强制类型转换；
- byte/char/short这三种类型都可以发生数学运算，但是在运算的时候会首先被提升成为int类型，然后在计算。
- boolean类型不能发生数据类型的转换

### 引用数据类型——字符串

​	引用类型中最常用的就是字符串String，在java.lang中还提供了处理字符串的String类，String类用于处理“不可变”的字符串，它们的值在创建后不能被更改；相对地，还提供了StringBuffer类坏人StringBuilder用于处理“可变”字符串。Stirng类，StringBuffer类和StringBuilder都被声明为final类型，因此不能将其当做父类再被继承使用了。

​	首先我们总结学习一下字符串，字符串的特点：内容是不可变的，但可以共享，字符串效果上相当于char[ ]字符数组，底层原理是byte[ ]字节数组。我们常遇到的问题是比较两个字符串（“==”是用来表引用类型是引用地址，“equals()”方法可以比较字符串内容是否一致。），今天我们只是简单总结学习一下字符串String存储方式。例如：

```java
    public static void main(String[] args){
        String str1 = "abc";
        String str2 = "abc";
        
        char[] charArray = {'a','b','c'};
        String str3 = new String(charArray);
        
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str2 == str3);
        
    }

结果：
true
false
false
```

原因我们可以通过画内存图了解：

![](E:\muser\pdf\String存储图.png)

注意：1.对于引用类型来说，‘==’进行的是地址值得比较；2.双引号直接写的字符串在常量池中，new的不在常量池中

其实还有很多关于上面字符串比较的相关例子，都是通过分析判断字符串的引用地址是否一致的问题。

​	那么现在我们在学习一下StringBuilder和Stringbuffer，StringBuilder类和StringBuffer类是字符串缓冲区类，用于创建长度可变的字符串对象，这里的“长度可变”是指通过某些方法的调用可以改变字符串的长度和内容，比如通过在原字符串的基础上追加新的字符串序列，或者在原字符串的某个位置上插入新的字符序列等构成新的字符串对象。常用的方法：append()和insert()，比如：

```java
    StringBuilder sb = new StringBuilder("abc");
    System.out.println("1:"+sb);
    sb.append("d");
    System.out.println("2:"+sb);
    sb.insert(1,"f");
    System.out.println("3:"+sb);
结果：
1:abc
2:abcd
3:afbcd
```
String，StringBuffer和StringBuilder的区别

1.首先说运行速度，或者说是执行速度，在这方面运行速度快慢为：StringBuilder > StringBuffer > String

原因：String为字符串常量，而StringBuilder和StringBuffer均为字符串变量，即String对象一旦创建之后该对象是不可更改的，但后两者的对象是变量，是可以更改的。Java中对String对象进行的操作实际上是一个不断创建新的对象并且将旧的对象回收的一个过程，所以执行速度很慢。而StringBuilder和StringBuffer的对象是变量，对变量进行操作就是直接对该对象进行更改，而不进行创建和回收的操作，所以速度要比String快很多

2.线程安全：在线程安全上，StringBuilder是线程不安全的，而StringBuffer是线程安全的

如果一个StringBuffer对象在字符串缓冲区被多个线程使用时，StringBuffer中很多方法可以带有**synchronized**关键字，所以可以保证线程是安全的，但StringBuilder的方法则没有该关键字，所以不能保证线程安全，有可能会出现一些错误的操作。所以如果要进行的操作是多线程的，那么就要使用StringBuffer，但是在单线程的情况下，还是建议使用速度比较快的StringBuilder。

## 流

​	jdkAPI的java.io包下是关于一系列数据流的介绍，io流是我们读取数据和输出数据的重要实现方式，比如：读取文件内容，输出文件内容等。

### File

​	jdk提供了操作文件的类File，File类提供了很多对文件和目录操作的方法。如：

**常用的构造函数**：File file =  new File(String pathname)

​			    我们通常指定一个抽象路径的字符串来创建File实例

**常用的方法**：

​	创建文件：createNewFile()  返回boolean类型表示是否创建成功

​	创建目录：mkdir()  和 mkdirs()

​		**区别**：mkdirs()可以如果父目录不存在也可以创建

​	删除文件或目录： delete()  

​		 **注意**：只能删除空目录

​	判断文件或目录是否存在：exists()

​	获取文件各种路径的方法：

​		getAbsoluteFile()   获取抽象路径的绝对形式  返回File类型

​		getAbsolutePath()  获取抽象路径的绝对路径字符串  返回String类型

​		getParent()  获取抽象路径的父路径的字符串  返回String类型

​		getPath()  获取抽象路径的字符串  返回String类型

​		。。。。。。

​	获取文件或目录名称：getName()

​	判断是否是文件：isFile()

​	获取文件长度：length()

​	获取文件集合：

​		listFiles()  获取指定路径下的所有文件  返回File[ ]

​		listFiles(FileFilter filter) 和 listFiles(FilenameFilter filter)  可以过滤文件

​		      **区别**：FileFilter和FilenameFilter接口中方法accept参数不同，FilenameFilter的方法accept(File dir , String  name)其中dir就是指定文件或目录，name就是指定文件或目录的名称，比如：可以判断文件是否是txt结尾的等场景，而FileFilter的方法accept(File pathname)其中pathname就是指定抽象路径名，所以接口FileFilter是抽象路径名的过滤器，接口FilenameFilter是文件名的过滤器

**注意：**

（1）File类中有个属性**separator**分隔符，我们在拼接文件路径的时候总会需要拼接路径分隔符，在Windows下的路径分隔符和Linux下的路径分隔符是不一样的，当直接使用绝对路径时，跨平台会暴出“No such file or diretory”的异常。

例如：Windows系统下创建文件    File  file  =  new File("E:\java\test.txt")   或者  File file =  new File("E://java//test.txt")

​	  Linux系统下创建文件  File file = new File("/java/test.txt")

如果将Windows环境下开发的代码部署到Linux系统中就会读取不到文件，那么就需要设置分隔符根据不同系统改变斜杠的方向，所以跨平台开发可以使用如下方式创建文件

File  file  =  new  File("E:" + File.separator + "java" + File.separator + "test.txt")

（2）如果要读取的文件在项目目录下，比如：读取properties文件，注意项目是否跨平台，如果需要将项目部署到Linux系统中，可能项目会打包成jar或者war，那么如果代码中使用File读取文件的话，会读取不到文件内容，因为项目打成jar或者war包之后整个项目就是一个文件，项目下的文件就不能通过File去操作，得通过**getResourceAsStream**来读取文件内容并操作。

### io流

首先我们可以通过下图直观的了解我们常用的io流：

![](E:\muser\pdf\io流类继承图.jpg)

四大抽象超类：

**InputStream** & **OutputStream** 和 **Reader** & **Write**分别是字节输入流&字节输出流和字符输入流&字符输出流的抽象基类，

文件流：

常用的读取文件使用到文件流 **FileInputStream** & **FileOutputStream** 和 **FileReader** & **FileWrite**

缓存流：

**BufferedInputStream** & **BufferedOutputStream** 和 **BufferedReader** & **BufferedWrite**缓存流读写效率较高，自带缓存区

转换流：

**InputStreamReader** 和 **OutputStreamWrite**可以将字符流和字节流进行转换，并且可以设置编码格式，其中**InputStreamReader**是从字节流到字符流的桥：它读取字节，并使用指定的charset将其解码为字符 。 它使用的字符集可以由名称指定，也可以被明确指定，或者可以接受平台的默认字符集。 

**OutputStreamWriter**是字符的桥梁流以字节流：向其写入的字符编码成使用指定的字节charset 。 它使用的字符集可以由名称指定，也可以被明确指定，或者可以接受平台的默认字符集。 

#### 字节流和字符流的主要区别

 1.字节流读取的时候，读到一个字节就返回一个字节；字符流使用了字节流读到一个或多个字节（中文GBK编码对应的字节数是2个，在UTF-8码表中是3个字节）时。先去查指定的编码表，将查到的字符返回。

 2.字节流可以处理所有类型数据，如：图片，MP3，AVI视频文件，而字符流只能处理字符数据。只要是处理纯文本数据，就要优先考虑使用字符流，除此之外都用字节流。

3.字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容。flush方法只有Writer类或其子类拥有。

**字节流与字符流读写方法比较：**

| 字节流                             |                                                              | 字符流                                   |                                                              |
| ---------------------------------- | ------------------------------------------------------------ | ---------------------------------------- | ------------------------------------------------------------ |
| 方法名                             | 描述                                                         | 方法名                                   | 描述                                                         |
| read()                             | 从输入流中读取数据一个字节，返回0到255之间的数值，如果没有字节可读，那就返回值为-1。 | read()                                   | 从输入流中读取数据一个字节，返回0到255之间的数值，如果没有字节可读，那就返回值为-1。 |
| read(byte[] b)                     | 从输入流中读取数据存储到b中，可以给b设置长度，一般1024的倍数，读取数据每次达到b的长度就输出一次。 | read(char[ ] char)                       | 从输入流中读取数据存储到char中，可以给char设置长度，一般1024的倍数，读取数据每次达到char的长度就输出一次。 |
| read(byte[ ] b, int off, int len)  | 本方法与上面第二个方法类似，参数off表示存储数据时b的起始位置，参数len表示存储的长度。一般off设置为0，len设置为数组b的长度。注意：如果长度len大于数组存储数据的长度就会报下标越界异常 | read(char[ ] char, int off, int len)     | 本方法与上面第二个方法类似，参数off表示存储数据时b的起始位置，参数len表示存储的长度。一般off设置为0，len设置为数组char的长度。注意：如果长度len大于数组存储数据的长度就会报下标越界异常 |
| write(byte[ ]  b)                  | 将b.length字节从指定的字节数组写入此输出流                   | write(char[ ]  char )                    | 将char.length字节从指定的字节数组写入此输出流                |
| write(byte[ ] b, int off, int len) | 从指定的字节数组写入len字节，从偏移off开始输出到此输出流。 write(b, off, len)的一般合同是数组b中的一些字节按顺序写入输出流; 元素b[off]是写入的第一个字节， b[off+len-1]是此操作写入的最后一个字节。一般off设置为0，len设置为1024的倍数。写入数据速度更快。 | write(char[ ] char, int  off,  int  len) | 从指定的字节数组写入len字节，从偏移off开始输出到此输出流。 write(char, off, len)的一般合同是数组char中的一些字节按顺序写入输出流; 元素char[off]是写入的第一个字节， b[off+len-1]是此操作写入的最后一个字节。一般off设置为0，len设置为1024的倍数。写入数据速度更快。 |
|                                    |                                                              | write(String str)                        | 写一个字符串                                                 |
|                                    |                                                              | write(String str, int off, int len)      |                                                              |

**特别注意：**使用字符流写入数据的时候必须使用**flush**刷新流，因为字符流本质是带有缓冲区的字节流，会将数据写入到缓冲区，通过刷新才能将数据写入到目标位置，或者通过关闭流强制将数据写入到目标位置。例如：

```java
//本方法运用了java1.7可以自动关闭流，通过flush方法友好的将数据输出到目标位置
public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text3.txt");
        try(FileReader fr = new FileReader(file1);
            FileWriter fw = new FileWriter(file2)){
            char[] chars = new char[1024];
            while ((fr.read(chars)) != -1){
                fw.write(chars);
                fw.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```

缓存流添加了读写高效的方法：

缓存流的**BufferedReader**相比其他输入流添加了一个新的读取方法：**readLine()** 可以对文本按行进行读取，一行被视为由换行符（'\ n'），回车符（'\ r'）中的任何一个或随后的换行符终止。

缓存流的**BufferedWriter**相比其他输出流添加了一个新的行分隔符方法：**newLine()**因为**BufferedReader**可以按照行进行读取，但是不会读取换行符，那么如果需要输出是按照原文内容进行换行，就需要通过newLine()方法将每一行数据后加一个换行符，而且本换行符会根据系统属性添加不同的换行符标识。例如：

```java
public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text3.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file1));
             BufferedWriter bw = new BufferedWriter(new FileWriter(file2))
        ) {
            String rs;
            while ((rs = br.readLine()) != null){
                bw.write(rs);
                bw.flush();
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

**注意：**我们在读取文件是有时候会出现乱码问题，因为文件存储的字符编码和读取的编码方式不一样，一般通过FileReader读取文件的时候回根据IDE默认编码方式读取文本内容，那么我们就可能需要转换流，转换流可以转换读取方式，也可以转换编码格式，比如：通过字节流FileInputStream将文本内容读取到，通过转换流InputStreamReader将字节流转换成字符流，再查询指定编码表将数据解码。

### 属性集

java.util.Properties类，Properties类表示一组持久的属性。 Properties可以保存到流中或从流中加载。 属性列表中的每个键及其对应的值都是一个字符串。 

properties继承了Hashtable<k,v> ,properties是唯一与io流相结合的集合，可以使用properties集合中的方法store，将集合中的数据持久化到硬盘中存储，也可以使用load方法，把硬盘中保存的文件(键值对)读取到集合中使用，我们使用Properties类最常见的场景就是读取配置文件，且配置文件内容类似“key = value” 或者 “key value”其中key和value通过“=”或者空格隔开。

Properties常用的方法：

构造函数：

​	Properties()   用来创建一个空属性列表

方法：

​	getProperties(String key)  类似Map集合中get(String key)方法，通过key值获取对应的value

​	setProperties(String key,String value)  类似Map集合中put(String key, String value)方法，但注意的是key和value都是字符串

​	stringPropertyNames() 返回的是Set<String>即获取到的是集合中所有key的集合，相当于Map中的keySet()方法

​	load(InputStream inStream) & load(Reader reader)  通过io流读取到数据并存储到集合中

​	store(OutputStream out, String comments)  & store(Writer writer, String comments)  将数据持久化到硬盘中

​		注意：参数为OutputStream out 字节输出流如果写中文会将中文转换成Unicode编码格式

​			   Writer writer 字符输出流可以写中文

​			   String  comment 注释，用来解释说明保存的文件是做什么用的，若使用中文会将其转换成Unicode十六进制输出

例如：

```java
 public static void main(String[] args) {
        Properties properties = new Properties();
        File file = new File("C:" + File.separator + "test" + File.separator + "test2.txt");
        try(InputStream is = MyProperties.class.getResourceAsStream("/config/test.properties");
            FileWriter fw = new FileWriter(file)
             ) {
            properties.load(is);
            properties.setProperty("山东省","济南市");
            Set<String> keys = properties.stringPropertyNames();
            for (String k : keys) {
                String value = properties.getProperty(k);
                System.out.println(k + "=" + value);

            }
            properties.store(fw,"地区名称");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
结果：
北京市=朝阳区
天津市=南开区
上海市=虹桥区
山东省=济南市
山西省=太原市
河北省=石家庄市

test2.txt
#\u5730\u533A\u540D\u79F0
#Mon Apr 08 21:51:27 CST 2019
北京市=朝阳区
天津市=南开区
上海市=虹桥区
山东省=济南市
山西省=太原市
河北省=石家庄市
```