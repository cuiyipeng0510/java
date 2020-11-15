# java常用类库 #

------

------

## lang包 ##

​	java.lang包是提供利用java编程语言进行程序设计的基础类

### Object

object是所有类的超类

Object类定义了一些有用的方法，由于是根类，这些方法在其他类中都存在，一般是进行重载或者重写覆盖，实现了给子类的具体功能。比如：

equals：返回值类型boolean，比较两个对象是否相同

hashCode：返回值类型int，返回对象的哈希码值

toString：返回值类型String，返回对象的字符串表示形式

------

### 包装类

Boolean，Character，Byte，Short，Integer，Long，Float，Double

知识点：装箱和拆箱，类型转换

自动装箱：自动加基本数据类型转成包装类，如：Integer  i = 1；

自动拆箱：自动将包装类转成基本数据类型，如：Integer  i = 2；int  n = i;

| 基本类型 | 包装类    | 字节   | 位数  | 范围                       |
| -------- | --------- | ------ | ----- | -------------------------- |
| boolean  | Boolean   | N/A    |       |                            |
| byte     | Byte      | 1 字节 | 8 位  | -128 ~ 127                 |
| char     | Character | 2 字节 | 16 位 | Unicode0 ~ Unicode(2^16)-1 |
| short    | Short     | 2 字节 | 16 位 | -2^15 ~ 2^15 - 1           |
| int      | Integer   | 4 字节 | 32 位 | -2^31 ~ 2^31 - 1           |
| long     | Long      | 8 字节 | 64 位 | -2^63 ~ 2^63 - 1           |
| float    | Float     | 4 字节 | 32 位 | IEEE754 ~ IEEE754          |
| double   | Double    | 8 字节 | 64 位 | IEEE754 ~ IEEE754          |

**装箱**过程是通过调用包装器的valueOf方法实现的，而**拆箱**过程是通过调用包装器的 xxxValue方法实现的。（xxx代表对应的基本数据类型）。

比较常见的问题，如下：

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

如果i在[-128,127]集合内，返回指向的是IntegerCache.cache中已经存在的对象的引用，否则创建一个新的Integer对象，其实包装类的ValueOf方法的实现方式不相同，比如：Double每次都会new一个Double对象，Integer只是一个例子，如果想了解其他包装类的实现可以看看源码。

https://www.cnblogs.com/dolphin0520/p/3780005.html

------

### 字符串类型

String ，StringBuilder， StringBuffer

在java.lang中还提供了处理字符串的String类，String类用于处理“不可变”的字符串，它们的值在创建后不能被更改；相对地，还提供了StringBuffer类坏人StringBuilder用于处理“可变”字符串。Stirng类，StringBuffer类和StringBuilder都被声明为final类型，因此不能将其当做父类再被继承使用了。

StringBuilder类和StringBuffer类是字符串缓冲区类，用于创建长度可变的字符串对象，这里的“长度可变”是指通过某些方法的调用可以改变字符串的长度和内容，比如通过在原字符串的基础上追加新的字符串序列，或者在原字符串的某个位置上插入新的字符序列等构成新的字符串对象。常用的方法：append()和insert()，比如：

```java
        StringBuilder sb = new StringBuilder("abc");
        System.out.println("1:"+sb);
        sb.append("d");
        System.out.println("2:"+sb);
        sb.insert(1,"f");
        System.out.println("3:"+sb);
```

结果：

```
1:abc
2:abcd
3:afbcd
```

String，StringBuffer和StringBuilder的区别

1.首先说运行速度，或者说是执行速度，在这方面运行速度快慢为：StringBuilder > StringBuffer > String

原因：String为字符串常量，而StringBuilder和StringBuffer均为字符串变量，即String对象一旦创建之后该对象是不可更改的，但后两者的对象是变量，是可以更改的

Java中对String对象进行的操作实际上是一个不断创建新的对象并且将旧的对象回收的一个过程，所以执行速度很慢。

而StringBuilder和StringBuffer的对象是变量，对变量进行操作就是直接对该对象进行更改，而不进行创建和回收的操作，所以速度要比String快很多

2.线程安全

在线程安全上，StringBuilder是线程不安全的，而StringBuffer是线程安全的

如果一个StringBuffer对象在字符串缓冲区被多个线程使用时，StringBuffer中很多方法可以带有**synchronized**关键字，所以可以保证线程是安全的，但StringBuilder的方法则没有该关键字，所以不能保证线程安全，有可能会出现一些错误的操作。所以如果要进行的操作是多线程的，那么就要使用StringBuffer，但是在单线程的情况下，还是建议使用速度比较快的StringBuilder。

------

### System

System类代表系统，系统级的很多属性和控制方法都放置在该类的内部。由于该类的构造方法是private的，所以无法创建该类的对象，也就是无法实例化该类。其内部的成员变量和成员方法都是static的，所以也可以很方便的进行调用。

成员变量：in、out、err，分别代表标准输入流(键盘输入)，标准输出流(显示器)和标准错误输出流(显示器)。

常用方法：

| 返回值            | 方法                                                         | 功能                                                     |
| ----------------- | ------------------------------------------------------------ | -------------------------------------------------------- |
| static  void      | arraycopy(Object src,  int srcPos, Object dest, int destPos,  int length) | 将指定源数组中的数组从指定位置复制到目标数组的指定位置。 |
| static void       | currentTimeMillis()                                          | 返回当前时间（以毫秒为单位）。                           |
| static Properties | getProperties()                                              | 获取指定键指示的系统属性。                               |
| static void       | gc()                                                         | 运行垃圾回收器。                                         |

------

### Math

Math类中包含执行基本数学运算的方法(加减乘除，平方~~~)，比如：绝对值 -abs()、较大值 -max()、较小值 -min{}、四舍五入 -round()

------

### Throwable

1. Throwable
    Throwable是 Java 语言中所有错误或异常的超类。
    Throwable包含两个子类: Error 和 Exception。它们通常用于指示发生了异常情况。
    Throwable包含了其线程创建时线程执行堆栈的快照，它提供了printStackTrace()等接口用于获取堆栈跟踪数据等信息。

2. Exception
    Exception及其子类是 Throwable 的一种形式，它指出了合理的应用程序想要捕获的条件。

3. RuntimeException 
    RuntimeException是那些可能在 Java 虚拟机正常运行期间抛出的异常的超类。
    编译器不会检查RuntimeException异常。例如，除数为零时，抛出ArithmeticException异常。RuntimeException是ArithmeticException的超类。当代码发生除数为零的情况时，倘若既"没有通过throws声明抛出ArithmeticException异常"，也"没有通过try...catch...处理该异常"，也能通过编译。这就是我们所说的"编译器不会检查RuntimeException异常"！
    如果代码会产生RuntimeException异常，则需要通过修改代码进行避免。例如，若会发生除数为零的情况，则需要通过代码避免该情况的发生！

4. Error
    和Exception一样，Error也是Throwable的子类。它用于指示合理的应用程序不应该试图捕获的严重问题，大多数这样的错误都是异常条件。
    和RuntimeException一样，编译器也不会检查Error

------

#### 异常分类

Java将可抛出(Throwable)的结构分为三种类型：被检查的异常(Checked Exception)，运行时异常(RuntimeException)和错误(Error)。

1.  运行时异常
   定义: RuntimeException及其子类都被称为运行时异常。
   特点: Java编译器不会检查它。也就是说，当程序中可能出现这类异常时，倘若既"没有通过throws声明抛出它"，也"没有用try-catch语句捕获它"，还是会编译通过。例如，除数为零时产生的ArithmeticException异常，数组越界时产生的IndexOutOfBoundsException异常，fail-fail机制产生的ConcurrentModificationException异常等，都属于运行时异常。
   虽然Java编译器不会检查运行时异常，但是我们也可以通过throws进行声明抛出，也可以通过try-catch对它进行捕获处理。
   如果产生运行时异常，则需要通过修改代码来进行避免。例如，若会发生除数为零的情况，则需要通过代码避免该情况的发生！

2.  被检查的异常
   定义: Exception类本身，以及Exception的子类中除了"运行时异常"之外的其它子类都属于被检查异常。
   特点: Java编译器会检查它。此类异常，要么通过throws进行声明抛出，要么通过try-catch进行捕获处理，否则不能通过编译。例如，CloneNotSupportedException就属于被检查异常。当通过clone()接口去克隆一个对象，而该对象对应的类没有实现Cloneable接口，就会抛出CloneNotSupportedException异常。
   被检查异常通常都是可以恢复的。

3.  错误
   定义: Error类及其子类。
   特点: 和运行时异常一样，编译器也不会对错误进行检查。
   当资源不足、约束失败、或是其它程序无法继续运行的条件发生时，就产生错误。程序本身无法修复这些错误的。例如，VirtualMachineError就属于错误。

我们最常见的异常就是运行时异常（非检查异常）

| 异常                           | 描述                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| ArithmeticException            | 当出现异常的运算条件时，抛出此异常。例如，一个整数"除以零"时，抛出此类的一个实例。 |
| ArrayIndexOutOfBoundsException | 用非法索引访问数组时抛出的异常。如果索引为负或大于等于数组大小，则该索引为非法索引。 |
| ArrayStoreException            | 试图将错误类型的对象存储到一个对象数组时抛出的异常。         |
| ClassCastException             | 当试图将对象强制转换为不是实例的子类时，抛出该异常           |
| IllegalArgumentException       | 抛出的异常表明向方法传递了一个不合法或不正确的参数。         |
| IndexOutOfBoundsException      | 指示某排序索引（例如对数组、字符串或向量的排序）超出范围时抛出。 |
| NullPointerException           | 当应用程序试图在需要对象的地方使用 null 时，抛出该异常       |
| NumberFormatException          | 当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。 |

我们常见的检查异常

| 异常                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| ClassNotFoundException | 应用程序试图加载类时，找不到相应的类，抛出该异常。           |
| NoSuchFieldException   | 请求的变量不存在                                             |
| NoSuchMethodException  | 请求的方法不存在                                             |
| IOException            | 表示发生某种类型的I / O异常。 此类是由失败或中断的I / O操作产生的一般异常类。 |
| SQLException           | 提供有关数据库访问错误或其他错误的信息的异常。               |
| FileNotFoundException  | 指示尝试打开由指定路径名表示的文件失败。                     |
| EOFException           | 表示在输入过程中意外地到达文件结束或流结束。                 |

------

### 注释类型

#### Override

override表示方法声明旨在覆盖父类的方法

小知识：

重载和重写

| 对比项   | 重载             | 重写                       |
| -------- | ---------------- | -------------------------- |
| 关键字   | overload         | override                   |
| 概念     | 一个方法的多样性 | 子类重新实现父类已有的方法 |
| 环境     | 同一个类中       | 父类，子类的继承关系中     |
| 方法名   | 必须相同         | 必须相同                   |
| 参数列表 | 必须不同         | 必须相同                   |
| 返回值   | 没有要求         | 必须相同                   |
| 访问权限 | 没有要求         | 不能比父类更加严格         |
| 异常     | 没有要求         | 不能比父类抛出更大的异常   |

#### SuppressWarning

SuppressWarning表示抑制编译器警告

------

------

## io包 ##

​	通过数据流，序列化和文件系统提供系统输入和输出。 除非另有说明，否则在此包中的任何类或接口中将null参数传递给构造函数或方法将导致抛出NullPointerException 。 

------

### 文件系统

#### File & FileFilter & FilenameFilter

File类提供了很多对文件和目录操作的方法

**小知识：**

相对路径：./表示当前路径../表示上一级路径，相对路径就需要有参照
绝对路径：绝对路径名是完整的路径名，不需要任何其他信息就可以定位自身表示的文件

**常用的属性：separator**

​	我们在拼接文件路径的时候总会需要拼接路径分隔符，在Windows下的路径分隔符和Linux下的路径分隔符是不一样的，当直接使用绝对路径时，跨平台会暴出“No such file or diretory”的异常。

例如：Windows系统下创建文件    File  file  =  new File("E:\java\test.txt")   或者  File file =  new File("E://java//test.txt")

​	  Linux系统下创建文件  File file = new File("/java/test.txt")

如果将Windows环境下开发的代码部署到Linux系统中就会读取不到文件，那么就需要设置分隔符根据不同系统改变斜杠的方向，所以跨平台开发可以使用如下方式创建文件

File  file  =  new  File("E:" + File.separator + "java" + File.separator + "test.txt")

常用的构造函数：File(String pathname)

​	我们通常指定一个抽象路径的字符串来创建File实例

常用的方法：

​	创建文件：createNewFile()  返回boolean类型表示是否创建成功

​	创建目录：mkdir()  和 mkdirs()

​		区别：mkdirs()可以如果父目录不存在也可以创建

​	删除文件或目录： delete()  注意只能删除空目录

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

​			区别：FileFilter和FilenameFilter接口中方法accept参数不同，FilenameFilter的方法accept(File dir , String  name)其中dir就是指定文件或目录，name就是指定文件或目录的名称，比如：可以判断文件是否是txt结尾的等场景，而FileFilter的方法accept(File pathname)其中pathname就是指定抽象路径名，所以**接口FileFilter是抽象路径名的过滤器，接口FilenameFilter是文件名的过滤器**

------

### io流

![](D:\工具\StuFile\pdf\io.png)

上图就是我们常用的数据流，根据字符流和字节流分类的，其实流还有其他分类标准，比如：输入输出流

#### 介绍

四大抽象超类：

**InputStream** & **OutputStream** 和 **Reader** & **Write**分别是字节输入流&字节输出流和字符输入流&字符输出流的抽象基类，

文件流：

常用的读取文件使用到文件流 **FileInputStream** & **FileOutputStream** 和 **FileReader** & **FileWrite**

缓存流：

**BufferedInputStream** & **BufferedOutputStream** 和 **BufferedReader** & **BufferedWrite**缓存流读写效率较高，自带缓存区

转换流：

**InputStreamReader** 和 **OutputStreamWrite**可以将字符流和字节流进行转换，并且可以设置编码格式，其中**InputStreamReader**是从字节流到字符流的桥：它读取字节，并使用指定的charset将其解码为字符 。 它使用的字符集可以由名称指定，也可以被明确指定，或者可以接受平台的默认字符集。 

**OutputStreamWriter**是字符的桥梁流以字节流：向其写入的字符编码成使用指定的字节charset 。 它使用的字符集可以由名称指定，也可以被明确指定，或者可以接受平台的默认字符集。 

------

#### 字符流和字节流的主要区别

 1.字节流读取的时候，读到一个字节就返回一个字节；字符流使用了字节流读到一个或多个字节（中文GBK编码对应的字节数是2个，在UTF-8码表中是3个字节）时。先去查指定的编码表，将查到的字符返回。

 2.字节流可以处理所有类型数据，如：图片，MP3，AVI视频文件，而字符流只能处理字符数据。只要是处理纯文本数据，就要优先考虑使用字符流，除此之外都用字节流。

3.字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容。flush方法只有Writer类或其子类拥有。

#### 字节流读写方法

**读：**

​	read()   从输入流中读取数据一个字节，返回0到255之间的数值，如果没有字节可读，那就返回值为-1。如下：

```java
public static void main(String[] args) {
        File file = new File("C:" + File.separator + "test" + File.separator + "text.txt");
        try (FileInputStream fis = new FileInputStream(file)){
            int read ;
            while ((read = fis.read()) != -1){
                System.out.print((char)read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

​	read(byte[] b)  从输入流中读取数据存储到b中，可以给b设置长度，一般1024的倍数，读取数据每次达到b的长度就输出一次，如下：

```java
public static void main(String[] args) {
        File file = new File("C:" + File.separator + "test" + File.separator + "text.txt");
        try (FileInputStream fis = new FileInputStream(file)){
            byte[] b = new byte[1024];
            int read ;
            while ((read = fis.read(b)) != -1){
                String s = new String(b, 0, read);
                System.out.print(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

​	read(byte[ ] b, int off, int len) 本方法与上面第二个方法类似，一般不使用此方法，参数off表示存储数据时b的起始位置，参数len表示存储的长度。一般off设置为0，len设置为数组b的长度。

注意：如果长度len大于数组存储数据的长度就会报下标越界异常

```java
public static void main(String[] args) {
        File file = new File("C:" + File.separator + "test" + File.separator + "text.txt");
        try (FileInputStream fis = new FileInputStream(file)){
            byte[] b = new byte[1024];
            int read ;
            while ((read = fis.read(b,1,1023)) != -1){
                String s = new String(b, 0, read);
                System.out.print(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

**写：**

​	write(byte[ ]  b)  将b.length字节从指定的字节数组写入此输出流

```java
 public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text2.txt");
        try (FileInputStream fis = new FileInputStream(file1);
             FileOutputStream fos = new FileOutputStream(file2)){
            byte[] b = new byte[1024];
            while ((fis.read(b,0,1024)) != -1){
                //输出流将读到的数据写入到text2文件中
                fos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

​	write(byte[ ] b, int off, int len)从指定的字节数组写入len字节，从偏移off开始输出到此输出流。 write(b, off, len)的一般合同是数组b中的一些字节按顺序写入输出流; 元素b[off]是写入的第一个字节， b[off+len-1]是此操作写入的最后一个字节。一般off设置为0，len设置为1024的倍数。写入数据速度更快。

```java
public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text2.txt");
        try (FileInputStream fis = new FileInputStream(file1);
             FileOutputStream fos = new FileOutputStream(file2)){
            byte[] b = new byte[1024];
            while ((fis.read(b,0,1024)) != -1){
                fos.write(b,0,1024);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

------

#### 字符流读写方法

​	其实字符流的读写方法与字节流的读写方法类似

**读：**

​	read()读一个字符；

​	read(char[ ] char)将字符读入数组

​	read(char[ ] char, int off, int len)将字符读入数组的一部分

**写：**

​	write(char[ ]  char )写入一个字符数组

​	write(char[ ] char, int  off,  int  len) 写入字符数组的一部分

​	write(String str)写一个字符串

​	write(String str, int off, int len)写一个字符串的一部分

**特别注意：**使用字符流写入数据的时候必须使用flush刷新流，因为字符流本质是带有缓冲区的字节流，会将数据写入到缓冲区，通过刷新才能将数据写入到目标位置，或者通过关闭流强制将数据写入到目标位置。

```java
//示例一
//注意示例使用字符流读取数据，没有flush，也没有关闭流，所以text3.txt是空的
public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text3.txt");
        try{
            FileReader fr = new FileReader(file1);
            FileWriter fw = new FileWriter(file2);
            char[] chars = new char[1024];
            while ((fr.read(chars)) != -1){
                fw.write(chars);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```

```java
//示例二
//本示例通过flush将数据刷新输出,但是没有关闭流
public static void main(String[] args) {
        File file1 = new File("C:" + File.separator + "test" + File.separator + "text1.txt");
        File file2 = new File("C:" + File.separator + "test" + File.separator + "text3.txt");
        try{
            FileReader fr = new FileReader(file1);
            FileWriter fw = new FileWriter(file2);
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

```java
//示例三
//本方法运用了java1.8可以自动关闭流，同事通过flush方法友好的将数据输出到目标位置
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

------

### io异常

io异常是我们在程序中常见的一种异常，在io包下对这些异常进行了介绍，下面学习几种比较常见的异常。

#### IOException异常

IOException的父类就是Exception，直接子类有很多，其实我们在工作中一般会直接对IOException进行操作，可能抛出，或者捕获。

#### FileNotFoundException异常

文件找不到异常，一般当指定的文件路径下没有找到要读取的文件就会报此异常

#### InterruptedIOException异常

表示I / O操作已中断。 抛出一个InterruptedIOException表示输入或输出传输已被终止，因为执行它的线程被中断。	

------

------

## math包 ##

提供执行任意精度整数运算（ BigInteger ）和任意精度十进制运算（ BigDecimal ）的类。

我们经常用到的数据类型int，short，long，float，double的精度虽然能满足我们日常需要，但是如果遇到高精度计算就会导致数据不准确，所以会使用math包下的任意精度的类操作数据

------

#### BigInteger

BigInteger有三个静态的常数，分别为ONE, TEM, ZERO

```JAVA
 public static void main(String[] args) {
        System.out.println(BigInteger.ONE);//1
        System.out.println(BigInteger.TEN);//10
        System.out.println(BigInteger.ZERO);//0
    }
```

BigInteger构造函数中有一个将十进制字符串转成成BigInteger,注意：BigInteger没有无参构造函数

```java
BigInteger  bigInteger = new BigInteger("100")//100
```

BigInteger还可以通过valueOf方法将普通数值转成大数值

```java
BigInteger valueOf = BigInteger.valueOf(1000);
System.out.println(valueOf);
```

注意：BigInteger不能通过基本数据类型的加减乘除(+,-,*,/)方式处理，而是通过方法来处理

BigInteger提供了各种数学计算的方式，比如绝对值，异或运算

```java
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("100");
        BigInteger valueOf = BigInteger.valueOf(1000);
        //加法
        BigInteger add = valueOf.add(bigInteger);
        //减法
        BigInteger subtract = valueOf.subtract(bigInteger);
        //除法
        BigInteger divide = valueOf.divide(bigInteger);
        //乘法
        BigInteger multiply = valueOf.multiply(bigInteger);
        //转成double  当然floatValue，intValue，longValue
        double v = valueOf.doubleValue();
        //比较是否相等
        boolean equals = valueOf.equals(bigInteger);
        //求负数
        BigInteger negate = valueOf.negate();
        //转换成字符串
        String s = valueOf.toString();
        BigInteger xor = valueOf.xor(bigInteger);
        System.out.println(xor);//908
        System.out.println(s);//1000
        System.out.println(negate);//-1000
        System.out.println(equals);//false
        System.out.println(v);//1000.0
        System.out.println(multiply);//100000
        System.out.println(divide);//10
        System.out.println(subtract);//900
        System.out.println(add);//1100
    }
```

------

#### BigDecimal

​	float和double类型的主要设计目标是为了科学计算和工程计算。他们执行二进制浮点运算，这是为了在广域数值范围上提供较为精确的快速近似计算而精心设计的。然而，它们没有提供完全精确的结果，所以不应该被用于要求精确结果的场合。但是，商业计算往往要求结果精确，这时候BigDecimal就派上大用场啦。

```java
    public static void main(String[] args) {
        System.out.println(0.2 + 0.1);
        System.out.println(0.3 - 0.1);
        System.out.println(0.2 * 0.1);
        System.out.println(0.3 / 0.1);
    }
```

运行结果：

```
0.30000000000000004
0.19999999999999998
0.020000000000000004
2.9999999999999996
```

​	你认为你看错了，但结果却是是这样的。问题在哪里呢？原因在于我们的计算机是二进制的。浮点数没有办法是用二进制进行精确表示。我们的CPU表示浮点数由两个部分组成：**指数和尾数**，这样的表示方法一般都会失去一定的精确度，有些浮点数运算也会产生一定的误差。如：2.4的二进制表示并非就是精确的2.4。反而最为接近的二进制表示是 2.3999999999999999。浮点数的值实际上是由一个特定的数学公式计算得到的。

**BigDecimal构造方法**

​       1.public BigDecimal(double val)    将double表示形式转换为BigDecimal *不建议使用

　　2.public BigDecimal(int val)　　将int表示形式转换成BigDecimal

　　3.public BigDecimal(String val)　　将String表示形式转换成BigDecimal

```java
    public static void main(String[] args)
    {
        BigDecimal bigDecimal = new BigDecimal(2);
        BigDecimal bDouble = new BigDecimal(2.3);
        BigDecimal bString = new BigDecimal("2.3");
        System.out.println("bigDecimal=" + bigDecimal);
        System.out.println("bDouble=" + bDouble);
        System.out.println("bString=" + bString);
    }
```

运行结果：

```
bigDecimal=2
bDouble=2.29999999999999982236431605997495353221893310546875
bString=2.3
```

为什么会出现这种情况呢？

​	JDK的描述：1、参数类型为double的构造方法的结果有一定的不可预知性。有人可能认为在Java中写入newBigDecimal(0.1)所创建的BigDecimal正好等于 0.1（非标度值 1，其标度为 1），但是它实际上等于0.1000000000000000055511151231257827021181583404541015625。这是因为0.1无法准确地表示为 double（或者说对于该情况，不能表示为任何有限长度的二进制小数）。这样，传入到构造方法的值不会正好等于 0.1（虽然表面上等于该值）。

2、另一方面，String 构造方法是完全可预知的：写入 newBigDecimal("0.1") 将创建一个 BigDecimal，它正好等于预期的 0.1。因此，比较而言，通常建议优先使用String构造方法。

**当double必须用作BigDecimal的源时，请使用Double.toString(double)转成String，然后使用String构造方法，或使用BigDecimal的静态方法valueOf，如下**

```java
    public static void main(String[] args)
    {
        BigDecimal bDouble1 = BigDecimal.valueOf(2.3);
        BigDecimal bDouble2 = new BigDecimal(Double.toString(2.3));

        System.out.println("bDouble1=" + bDouble1);
        System.out.println("bDouble2=" + bDouble2);

    }
```

运算结果：

```
bDouble1=2.3
bDouble2=2.3
```

**BigDecimal运算**

对于常用的加，减，乘，除，BigDecimal类提供了相应的成员方法。

```java
public BigDecimal add(BigDecimal value);                        //加法

public BigDecimal subtract(BigDecimal value);                   //减法 

public BigDecimal multiply(BigDecimal value);                   //乘法

public BigDecimal divide(BigDecimal value);                     //除法
```

例如：

```java
public static void main(String[] args)
    {
        BigDecimal a = new BigDecimal("4.5");
        BigDecimal b = new BigDecimal("1.5");

        System.out.println("a + b =" + a.add(b));
        System.out.println("a - b =" + a.subtract(b));
        System.out.println("a * b =" + a.multiply(b));
        System.out.println("a / b =" + a.divide(b));
    }
```

结果：

```
a + b =6.0
a - b =3.0
a * b =6.75
a / b =3
```

这里有一点需要注意的是除法运算divide.

BigDecimal除法可能出现不能整除的情况，比如 4.5/1.3，这时会报错java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.

其实divide方法有可以传三个参数

public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode) 
第一参数表示除数， 第二个参数表示小数点后保留位数，
第三个参数表示舍入模式，只有在作除法运算或四舍五入时才用到舍入模式，有下面这几种

```java
ROUND_CEILING    //向正无穷方向舍入

ROUND_DOWN    //向零方向舍入

ROUND_FLOOR    //向负无穷方向舍入

ROUND_HALF_DOWN    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5

ROUND_HALF_EVEN    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP，如果是偶数，使用ROUND_HALF_DOWN

ROUND_HALF_UP    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6

ROUND_UNNECESSARY    //计算结果是精确的，不需要舍入模式

ROUND_UP    //向远离0的方向舍入
```

按照各自的需要，可传入合适的第三个参数。四舍五入采用 ROUND_HALF_UP

需要对BigDecimal进行截断和四舍五入可用setScale方法，例：

```java
public static void main(String[] args)
    {
        BigDecimal a = new BigDecimal("4.5635");

        a = a.setScale(3, RoundingMode.HALF_UP);    //保留3位小数，且四舍五入
        System.out.println(a);//4.564
    }
```

减乘除其实最终都返回的是一个新的BigDecimal对象，因为BigInteger与BigDecimal都是不可变的（immutable）的，在进行每一步运算时，都会产生一个新的对象。

```java
    public static void main(String[] args)
    {
        BigDecimal a = new BigDecimal("4.5");
        BigDecimal b = new BigDecimal("1.5");
        BigDecimal add = a.add(b);

        System.out.println(add);//6.0
        System.out.println(a);  //输出4.5. 加减乘除方法会返回一个新的BigDecimal对象，原来的a不变


    }
```

注意：

(1)商业计算使用BigDecimal。

(2)尽量使用参数类型为String的构造函数。

(3) BigDecimal都是不可变的（immutable）的，在进行每一步运算时，都会产生一个新的对象，所以在做加减乘除运算时千万要保存操作后的值。

 (4)我们往往容易忽略JDK底层的一些实现细节，导致出现错误，需要多加注意。

------

------

## util包

java.util包含集合框架，旧集合类，事件模型，日期和时间设施，国际化和其他实用程序类（字符串tokenizer，随机数生成器和位数组）。 

------

### 集合

![](D:\工具\StuFile\pdf\集合结构图.png)

集合：是java提供的一种容器，可以用来存储多个数据

集合和数组都是容器，那么他们的区别是什么？

- 数组长度是固定的。集合的长度是可变的。

  int[ ]  arr = new int[10] 

  Student[ ]  arr = new Student[3]

  List<String>  list = new ArrayList<>();

- 数组中存储的是同一类型的元素，可以存储基本数据类型值，集合存储的都是对象。而且对象的类型可以不一致。在开发中一般当对象多的时候，使用集合存储。

  注意：集合不能存储基本数据类型，比如：存储的是1,2等数字，其实存储的是Integer对象

------

#### Collection<E>

Collection接口是集合层次结构中的根节点，Collection接口继承自超级接口Iterable<T>,是Collection层次结构中的根接口。Collection表示一组对象，这些对象也被称为Collection的元素。一些Collection允许有重复的元素（例如List），但是另一些则不允许有重复的元素，即可为无序的（如Set）。JDK不提供此接口的任何直接实现---它会提供更为具体的子接口(如Set和List),。此接口用来传递Collection,并在需要时最大普遍性的地方操作这些Collection。

注意：因为Collection继承了Iterable<T>接口，所以实现Collection的类可以通过迭代器遍历，如：list的子类，set的子类，但是Map就不可以，使用迭代器遍历可以一边遍历一边通过迭代器对象删除元素。

Collection是所有单列集合的父接口，因此Collection中定义单列集合List和Set通用的一些方法，这些方法可用于操作所有单列集合

Collection提供了一些通用的方法：

| 返回值      | 方法名                            | 描述                                   |
| ----------- | --------------------------------- | -------------------------------------- |
| boolean     | add(E e)                          | 集合添加元素                           |
| boolean     | addAll(Collection<? extends E> c) | 将指定集合的全部元素添加到新的集合中   |
| void        | clear()                           | 删除集合中全部元素                     |
| boolean     | contains(Object o)                | 集合中是否包含某个元素                 |
| boolean     | containsAll(Collection<?> c)      | 判断集合中是否包含指定集合中的全部元素 |
| boolean     | equals(Object o)                  | 比较两个对象是否相等                   |
| boolean     | isEmpty()                         | 判断集合是否为空                       |
| Iterator<E> | iterator()                        | 返回此集合中元素的迭代器               |
| boolean     | remove(Object o)                  | 从集合中移除指定元素                   |
| boolean     | removeAll(Collection<?> c)        | 从集合中移除指定集合中的所有元素       |
| int         | size()                            | 返回集合中元素的数量                   |
| Object[ ]   | toArray()                         | 将集合中的元素，存储到数组中           |

------

#### Iterator<T>

Collection继承了Iterable<T>接口，那么就像上面提到了只要是实现了Collection接口的集合都可以使用迭代器，迭代器就是循环遍历集合中元素。查看Iterable接口jdk中只有一句：实现此接口允许对象成为“for-each loop”语句的目标。其实for-eache循环底层还是迭代器。

如何获取迭代器？

​	我们发现Collection接口中提供了一个方法Iterator()返回值就是一个Iterator<E>，它的泛型与集合的泛型一致。

迭代器Iterator<E>常用方法：

| 返回值  | 方法名    | 描述           |
| ------- | --------- | -------------- |
| boolean | hasNext() | 是否有更多元素 |
| E       | next()    | 获取下一个元素 |
| void    | remove()  | 移除元素       |

注意：集合在for循环中边遍历边删除的情况必须使用迭代器循环实现，如果使用for循环边遍历边删除元素那么就会报ConcurrentModificationException异常

增强for循环底层仍然是迭代器

------

#### 集合的比较

##### 子接口比较

| List<E>                        | Set<E>                   | Map<k,v>                                         |
| ------------------------------ | ------------------------ | ------------------------------------------------ |
| Collection的子接口             | Collection的子接口       |                                                  |
| 1.有序，有下标<br>2.元素可重复 | 1.无序<br>2.元素不可重复 | 1.键值对一一对应<br>2.key不可重复，value可以重复 |

List和Set都继承了Collection接口，都是是单列集合，Map是双列集合。

Map集合中有一些操作键值对的方法：

put(key, value)  向map中添加元素

get(key)  通过key获取value的值

keySet()  获取所有key的Set集合

values()  获取Collection<V>即value的集合

entrySet()  返回值是键值对的set集合Set<Map.Entry<k,v>>

```
java.util.Map.Entry<K,V>接口,就是键值对，我们常用于遍历map集合时可以通过entrySet获取到键值对的集合Map.Entry<k,v>,再通过接口提供的两个方法分别获取键值对的key和value，即getKey() 和 getValue()
```



------

##### List实现类比较

| ArrayList                                                    | LinkedList                                                   | Vector                                                       |                                                              |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 底层是数组结构：查询快，增删慢<br>因为数组有下标，且是连续的，所以查询速度较快<br>查看ArrayList集合底层源码发现，每次添加 元素都是数组复制的过程，每次添加一个元素都会创建一个比原数组长度大一的新数组，将原有数组的元素复制到新数组中，并添加新元素，所以ArrayList增删慢 | 底层双向链表结构：查询速度较慢，但是增删快；<br>因为LinkedList实现了Deque<E>接口，所以可以模拟队列(先进先出)，因此有offer()入列，poll()出列，也可以模拟堆栈(先进后出)，因此有压栈push(),弹栈pop()，当然因为LinkedList集合是双向列表那么就有一些操作列表首尾元素的方法，比如：addFrist()，addLast()，getFirst()，getLast()等方法 | 1.0版本单列表集合<br>类似ArrayList，但是因为线程安全，效率低，被ArrayList替代 | 1,0版本，继承了Vector栈：特点：后进先出，常用的方法就是pop()出栈也叫弹栈，即删除顶部对象，push()压栈，将对象添加到 |
| 多线程，不安全，效率高                                       | 多线程，不安全，效率高                                       | 单线程，安全，效率低                                         | 单线程，线程安全，效率低                                     |

建议：如果程序中查询比较多，那么使用ArrayList



------

##### Set实现类比较

**哈希值**：是一个十进制的整数，由系统随机给出（就是对象的地址值，是一个逻辑地址，是模拟出来的地址，不是数据实际存储的物理地址）

在Object类有一个方法，可以获取对象哈希值——int  hashCode   返回对象的哈希码值

hashCode方法的源码：public native int hashCode();   其中native代表该方法调用的是本地操作系统的方法

**哈希表：**

![](D:\工具\StuFile\pdf\hashSet.png)

注意：如果同一个哈希值下面挂载的对象超过8个就将此哈希值下的对象转换成红黑树。哈希表=数组+链表或者数组+红黑树

| HashSet                                                      | LinkedHashSet                                               | TreeSet                                                      |
| ------------------------------------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| 底层是哈希表结构：查询速度快<br>特点：元素不重复，且没有顺序，可能存取顺序不同 | 底层是哈希表+链表<br>特点：元素不重复，保存了元素的存储顺序 | 底层是二叉树<br>特点：可以排序<br>实体类实现Comparable接口重写compareTo方法；<br>接口Comparator<T>自定义比较器 |
| 线程不安全，效率高                                           | 线程不安全，效率高                                          | 线程不安全，效率高                                           |
|                                                              |                                                             |                                                              |



**HashSet去重原理**：首先比较元素的hashCode()哈希值是否存在，如果不存在，那么将元素存储，如果存在，再通过equals()方法表元素内容是否一致，如果一致不存储，不一致则存储。HashSet底层使用的HashMap，只取了hashMap的key。

**注意：**jdk提供的数据类型String，Integer等类都重写了hashCode和equals方法，所以当这些数据存入HashSet集合中时都会去重，**但是如果存储自定的类型的数据需要去重的话，就必须重写hashCode和equals方法。**比如：hashSet集合中要存储Student对象（名字和年龄都相同就是同一个人），且需要去重，那么就需要Student类重写hashCode和equals方法。

如下图描述：

![](D:\工具\StuFile\pdf\set不重复原理.png)



**LinkedHashSet**继承了HashSet，只是底层结构不同，LinkedHashSet底层结果为哈希表+链表，链表记录了元素的存储顺序，保证元素有序，LinkedHashSet去重原理与HashSet相同。



**TreeSet的两种排序和去重方法**

**方法一：Comparable<T>**

**TreeSet**会对存储的元素按照自然顺序排序，也就是存入TreeSet的元素必须实现Comparable<T>这个接口，我们常用的数据类型如：String，Integer，Date等都实现了此接口，所以如果我们要存储自定义的实体，那么该实体必须实现Comparable<T>接口。

```
什么是自然顺序？
	java.lang.Comparable<T>  public interface Comparable<T>该接口对实现它的每个类的对象强加一个整体排序。 这个排序被称为类的自然排序 ，类的compareTo方法被称为其自然比较方法 。 因为String实现了接口Comparable<T>，那么String中的compareTo方法按照字典顺序比较字符串，基于字符串中每个字符的Unicode值； 因为Integer实现了Comparable<T>接口，那么Integer中的compareTo方法比较的是Integer对象，按照数值从小到大排序。
	接口Comparable<T>中有一个方法compareTo(T o)，将此对象(即调用compareTo方法的对象)与指定的对象(即o)进行比较以进行排序。返回一个负整数，零或正整数，因为该对象小于，等于或大于指定对象。 我们一般分别返回-1，0, 1。 
```

如果没有实现那么就会报异常：java.lang.ClassCastException: com.xrds.springbootdemo.bean.PersonBean cannot be cast to java.lang.Comparable   例如：

```java
//PersonBean没有实现Comparable接口   
public static void main(String[] args) {
        PersonBean p1 = new PersonBean("张三",20);
        PersonBean p2 = new PersonBean("李四",25);
        PersonBean p3 = new PersonBean("王五",30);
        PersonBean p4 = new PersonBean("王五",30);

        Set<PersonBean> treeSet = new TreeSet<>();
        treeSet.add(p1);
        treeSet.add(p2);
        treeSet.add(p3);
        treeSet.add(p4);
        System.out.println(treeSet.size());
    }
结果：
Exception in thread "main" java.lang.ClassCastException: com.xrds.springbootdemo.bean.PersonBean cannot be cast to java.lang.Comparable
	at java.util.TreeMap.compare(TreeMap.java:1294)
	at java.util.TreeMap.put(TreeMap.java:538)
	at java.util.TreeSet.add(TreeSet.java:255)
	at com.xrds.springbootdemo.business.collection.MyTreeSet.main(MyTreeSet.java:25)
Process finished with exit code 1
```

自定义实体类实现了Comparable<T>接口，那么就需要重写compareTo方法，可以根据实体类的属性进行排序和去重。例如学生实体类通过年龄从小到大排序和去重。

```java
    @Override
    public int compareTo(Student o) {
        if (this.age > o.age){
            return 1;
        }else if (this.age < o.age)){
            return  -1;
        }
        return 0;
    }
```

**方法二：Comparator<T>**

TreeSet不仅可以通过实体类实现Comparable<T>接口实现排序和去重，也可以自定义比较器，即TreeSet有参构造函数TreeSet(Comparator<? super E> comparator)  参数就是Comparator<T>接口的实现类，可以创建一个Comparator<T>的实现类，也可以通过匿名内部类实现排序和去重。例如：

```
java.util.Comparator<T>接口，有比较功能，对一些对象的集合施加了一个整体排序 。可以将比较器传递给排序方法（如Collections.sort或Arrays.sort ），以便对排序顺序进行精确控制。
```

```java
//通过人物的年龄进行从小到大排序和去重
public static void main(String[] args) {
        PersonBean p1 = new PersonBean("张三",20);
        PersonBean p2 = new PersonBean("李四",25);
        PersonBean p3 = new PersonBean("王五",30);
        PersonBean p4 = new PersonBean("王五",30);
//匿名内部类
        Set<PersonBean> treeSet = new TreeSet<>(new Comparator<PersonBean>() {
            @Override
            public int compare(PersonBean o1, PersonBean o2) {
                if (o1.getAge() > o2.getAge()){
                    return 1;
                }else if (o1.getAge()  < o2.getAge()){
                    return -1;
                }
                return 0;
            }
        });
        treeSet.add(p1);
        treeSet.add(p2);
        treeSet.add(p3);
        treeSet.add(p4);
        for (PersonBean p:treeSet){
            System.out.println(p);
        }
    }
```

**注意：**TreeSet存储元素类型必须一致，即使泛型设置为Object，虽然add可以将元素存储，但是运行会报异常：java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String

```java
    public static void main(String[] args) {
        TreeSet<Object> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add("a");
        treeSet.add("c");
        treeSet.add("1");
        treeSet.add("3");
        System.out.println(treeSet.size());
    }
结果：
Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
	at java.lang.String.compareTo(String.java:111)
	at java.util.TreeMap.put(TreeMap.java:568)
	at java.util.TreeSet.add(TreeSet.java:255)
	at com.xrds.springbootdemo.business.collection.MyTreeSet1.main(MyTreeSet1.java:19)
```

**注意：**Map/Set的key为自定义对象时，必须重写hashCode和equals。 关于hashCode和equals的处理，遵循如下规则： 

- 只要重写equals，就必须重写hashCode。  
- 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的对象必须重写这两个方法。  
- 如果自定义对象做为Map的键，那么必须重写hashCode和equals。



------

##### Map实现类比较

Map实现类的比较类似Set实现类比较

| HashMap                  | TreeMap                | LinkedHashMap          | HashTable        |
| ------------------------ | ---------------------- | ---------------------- | ---------------- |
| 底层是哈希表：查询速度快 | 底层是红黑树           | 底层是哈希表+链表      | 被HashMap取代    |
| key无序且不重复          | key排序和去重          | key有序                | key无序且不重复  |
| 多线程，不安全，效率高   | 多线程，不安全，效率高 | 多线程，不安全，效率高 | 线程安全，效率低 |

HashMap的key去重原理与HashSet相同，都是通过hashCode()方法和equalsf()方法；

LinkedHashMap继承了HashMap所以key的去重原理和HashMap相同。

TreeMap的key去重原理与TreeSet相同，都是通过比较器排序和去重。

HashTable被HashMap取代，因为HashTable是线程安全的效率低。

**注意：**上面我们曾经说过，HashSet的底层是通过HashMap实现的，HashSet只是用了HashMap的key集合，所以自定义的实体类如果要作为Map集合的key，那么就需要重写hashCode()方法和equals()方法，对Map集合的key进行去重。当然TreeMap的key排序和去重与TreeSet类似。

#### Collections

我们发现Collections和Collection非常相似，CollectionCollection接口是集合层次结构中的根节点，Collections是包装器，就是集合的一个工具类，发现Collections中的方法都是static静态的。

Collections的属性

| 返回值      | 属性名     | 描述           |
| ----------- | ---------- | -------------- |
| static List | EMPTY_LIST | 空list集合对象 |
| static Map  | EMPTY_MAP  | 空map集合对象  |
| static Set  | EMPTY_SET  | 空set集合对象  |

这三个属性与常用方法中emptyList()、emptyMap()和emptySet()效果一样。

我们在程序中经常为了防止出现空指针异常NullPointerException，会对集合进行判null操作，可能会通过条件语句进行判断，但是程序中条件语句增多的话，会提高代码的复杂度。

```java
//我们发现这段代码我们有两次判空 
public static void main(String[] args) {
        List<String> list = getList();
        if (CollectionUtils.isEmpty(list)){
            System.out.println("list集合是空的");
            return;
        }
        for (String s:list){
            System.out.println(s);
        } 
    }

    private static  List<String> getList(){
        List<String> list = StudentDao.selectStudentList();
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        //TODO对list进行一番操作,然后返回最终的list
        return list;
    }
```

其实我们可以减少判空次数，如下：

```java
public static void main(String[] args) {
        List<String> list = getList();
        for (String s:list){
            System.out.println(s);
        } 
    }

    private static  List<String> getList(){
        List<String> list = StudentDao.selectStudentList();
        if (CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        //TODO对list进行一番操作,然后返回最终的list
        return list;
    }
```

常用方法：

binarySearch(List<? extends Comparable<? super T>> list, T key)     通过二分查找方式从集合中查询key的下标

binarySearch(List<? extends T> list, T key, Comparator<? super T> c)   

​	以上两个方法类似，二分查找必须集合中的元素是升序排序，否则结果有误，方法二在查询前需要对指定列表进行升序排序。

copy(List<? super T> dest, List<? extends T> src)    将一个集合(src)中所有元素复制到目标集合中(dest)

reverse(List<?> list)   将集合中的元素顺序反转排序

sort(List<T> list)   集合中所有元素必须实现Comparable接口，实现升序排序

sort(List<T> list, Comparator<? super T> c)  根据指定的比较器引起的顺序对指定的列表进行排序。

​	我们常用的创建集合的方式，创建的都是线程不安全的集合，比如：new ArrayList<>()，Collections提供了一些方法可以创建线程安全的集合。如下：

synchronizedList(List<T> list)  ，synchronizedMap(Map<K,V> m) ，synchronizedSet(Set<T> s) 

------

### 数组

#### Arrays

java.util.Arrays类，该类是操作数组的一个工具类，该类包含用于操作数组的各种方法（如排序和搜索）。 该类还包含一个静态工厂，可以将数组视为列表。 如果指定的数组引用为空，则该类中的方法都抛出一个NullPointerException。Arrays类似Collections，其方法都是static静态类。

常用的方法：

asList(T... a)  将数组转换成List集合，例如：List<String> list = Arrays.asList("a", "b", "c")  或者  List<String> list = Arrays.asList(new String[ ]{"a", "b", "c"})

sort(byte[ ] a)  和  sort(byte[] a, int fromIndex, int toIndex) 

​	对数组进行排序，基本数据类型按照数字顺序升序排序，自定义数据类型类似TreeSet排序方式，通过接口Comparable和接口Comparator，方法二可以指定排序范围

binarySearch(byte[ ] a, byte[ ] key)  和  binarySearch(char[] a, int fromIndex, int toIndex, char key) 

​	以上两个方法是通过二分法查询数组，数组类型不仅仅只有byte还有char，double，int等基本数据类型，以及Object指定对象数组，其中第二个方法可以指定从数组起始下标到结束下标进行二分法查询，值得注意的是使用二分法查询必须排序。

copyOf(byte[ ] a, int  newLength)  和 copyOfRange(byte[ ] int from, int to)

​	以上两个方法是复制数组，方法一将指定数组，从下标零开始复制，长度为newLength，如果数组长度小于newLength，那么用零填充，方法二将指定数组复制，并且指定复制的范围

当然Arrays也提供了equals()方法比较数组是否相同，toString()方法将指定数组转成字符串等方法

------

### 时间

##### Date

关于时间的首先是日期**Date**

常用的方法：

compareTo(Date anotherDate) 比较两个日期

equals()  比较两个日期是否相同 

getTime()  获取时间的毫秒数

关于时间我们常做的就是日期格式转成，之间通过代码学习

将日期Date转换成字符串

```java
    public static void main(String[] args) throws ParseException {
        //获取当前时间
        Date date = new Date();
        System.out.println("当前时间：" + date);
        //当前时间转成毫秒
        System.out.println("当前时间毫秒："+date.getTime());

        //将当前时间根据指定格式转成字符串形式
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
        String format1 = simpleDateFormat1.format(date);
        System.out.println("当前时间默认字符串格式："+format1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        System.out.println("当前时间指定格式：" + format);

        //将字符串转成Date类型,注意：字符串格式必须与SimpleDateFormat指定日期格式一致
        String date1 = "2019-04-14";
        String date2 = "2019-04-15";
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = simpleDateFormat2.parse(date1);
        Date parse2 = simpleDateFormat2.parse(date2);
        System.out.println(parse1);
        System.out.println(parse2);

        //通过毫秒数比较两个日期
        if (parse1.getTime() > parse2.getTime()){
            System.out.println(date1+"比"+date2+"晚");
        }else {
            System.out.println(date1+"比"+date2+"早");

        }
    }
结果：
当前时间：Sun Apr 14 15:00:35 CST 2019
当前时间毫秒：1555225235266
当前时间默认字符串格式：19-4-14 下午3:00
当前时间指定格式：2019-04-14 15:00:35
Sun Apr 14 00:00:00 CST 2019
Mon Apr 15 00:00:00 CST 2019
2019-04-14比2019-04-15早
```

##### Calendar

Calendar即日历，java.util.Calendar类是一个抽象类，所以不能通过new来创建对象，而且通过getInstance()方法创建一个Calendar对象。通过Calendar操作时间更加灵活，我们可以通过代码学习Calendar。

```java
    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        System.out.println(instance);

        //获取当前时间，相当于Date date = new Date();
        Date time = instance.getTime();
        System.out.println("当前时间："+time);

        //将日期Date转成Calendar
        instance.setTime(new Date());
        System.out.println("当前时间Calendar："+instance);

        System.out.println("=====================================");
        //注意：设置日期是月份需要减1
        instance.set(2019,4-1,14);
        System.out.println("自定义时间年月日："+instance.getTime());

        //通过set(int field, int value)给指定的属性赋值，我们常用的属性
        instance.set(Calendar.YEAR,2019);
        instance.set(Calendar.MONTH,4-1);//Calendar.MONTH实际月份比设置月份大1，即设置的是4，实际输出的是5月份
        instance.set(Calendar.DATE,2);
        instance.set(Calendar.HOUR_OF_DAY,23);//Calendar.HOUR实际时间比设置的时间超出12小时，即如果设置12，实际输出的日期是第二天的0点
        instance.set(Calendar.MINUTE,59);
        instance.set(Calendar.SECOND,59);
        instance.set(Calendar.WEEK_OF_MONTH,4);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(instance.getTime());
        System.out.println("分别自定义年月日时分秒："+format);

        System.out.println("=====================================");
        //通过field获取对应value值
        Calendar instance2 = Calendar.getInstance();
        System.out.println("当前时间年份："+instance2.get(Calendar.YEAR));
        System.out.println("当前时间月份："+instance2.get(Calendar.MONTH)+1);//获取当前月份需要加1，即Calendar.MONTH获得月份加1才是本月月份
        System.out.println("当前时间日期："+instance2.get(Calendar.DATE));
        System.out.println("当前时间小时："+instance2.get(Calendar.HOUR_OF_DAY));
        System.out.println("当前时间分钟："+instance2.get(Calendar.MINUTE));
        System.out.println("当前时间秒："+instance2.get(Calendar.SECOND));
        System.out.println("当前时间周几："+instance2.get(Calendar.DAY_OF_WEEK));//获取周几，注意：周日是1，周一是2 。。。
        System.out.println("当前时间是本月第几周："+instance2.get(Calendar.WEEK_OF_MONTH));//获取本月第几周
        System.out.println("当前时间是本年第几周："+instance2.get(Calendar.WEEK_OF_YEAR));//获取本年第几周


        System.out.println("=====================================");
        //两个日期比较，如果小于0，则调用对象比指定对象早，反之晚，等于0则两个时间相同
        Calendar instance1 = Calendar.getInstance();
        instance1.set(2019,4-1,18);
        int i = instance.compareTo(instance1);
        if (i > 0){
            System.out.println(instance.getTime()+"比"+instance1.getTime()+"晚");
        }else if(i < 0){
            System.out.println(instance.getTime()+"比"+instance1.getTime()+"早");
        }else {
            System.out.println(instance.getTime()+"和"+instance1.getTime()+"相等");

        }

        System.out.println("=====================================");
        //add方法是根据日历的规则，将指定的时间量添加或减去给定的日历字段。
        Calendar instance3 = Calendar.getInstance();
        instance3.add(Calendar.MONTH,2);//当前时间两个月之后的时间
        instance3.add(Calendar.DATE,1);//与上一行代码合起来就是当前时间两个月另一天之后的时间
        System.out.println(instance3.getTime());
    }
结果：
java.util.GregorianCalendar[time=1555230036582,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=19,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=14,DAY_OF_YEAR=104,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=2,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=20,SECOND=36,MILLISECOND=582,ZONE_OFFSET=28800000,DST_OFFSET=0]
当前时间：Sun Apr 14 16:20:36 CST 2019
当前时间Calendar：java.util.GregorianCalendar[time=1555230036614,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=19,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=3,WEEK_OF_YEAR=16,WEEK_OF_MONTH=3,DAY_OF_MONTH=14,DAY_OF_YEAR=104,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=2,AM_PM=1,HOUR=4,HOUR_OF_DAY=16,MINUTE=20,SECOND=36,MILLISECOND=614,ZONE_OFFSET=28800000,DST_OFFSET=0]
=====================================
自定义时间年月日：Sun Apr 14 16:20:36 CST 2019
分别自定义年月日时分秒：2019-04-21 23:59:59
=====================================
当前时间年份：2019
当前时间月份：31
当前时间日期：14
当前时间小时：16
当前时间分钟：20
当前时间秒：36
当前时间周几：1
当前时间是本月第几周：3
当前时间是本年第几周：16
=====================================
Sun Apr 21 23:59:59 CST 2019比Thu Apr 18 16:20:36 CST 2019晚
=====================================
Sat Jun 15 16:20:36 CST 2019
```

当然还有一些方法比如：

获取当前月份的最大日期：getActualMaximum(Calendar.DATE)  即方法instance.getActualMaximum(int field);

获取当前时区：getTimeZone()

在jdk1.8新特性中添加了一些关于时间的类：LocalDate，LocalTime等



------

### UUID

java.util.UUID类一个表示不可变的通用唯一标识符（UUID）的类。 UUID表示128位值。 

我们常用UUID获取32数字，比如：常见的数据库主键

```java
    public static void main(String[] args) {
        String s = UUID.randomUUID().toString().replace("-","");
        System.out.println(s);
    }
结果：0a523fff8cf545d29e9ea1238ee57e19
```



------

------

## concurrent包 ##

java.util.concurrent包中包含了并发编程需要的接口和类

为了学习的流畅性，所以将lang包中的关于线程的常用类在这里介绍。

### 线程类型

接口：Runnable

类：Thread

小知识：进程和线程

进程：是执行中一段程序，即一旦程序被载入到内存中并准备执行，它就是一个进程。进程是表示资源分配的的基本概念，又是调度运行的基本单位，是系统中的并发执行的单位。

线程：单个进程中执行中每个任务就是一个线程。线程是进程中执行运算的最小单位

两种创建线程的方式(其实还有通过使用Callable和Future创建线程、通过使用线程池例如用Executor框架)

方法一、通过实现Runnable接口创建并启动线程

1. 首先需要定义Runnable接口的实现类，重写run()方法，run()方法就是线程的执行体
2. 其次创建Runnable的实现类的实例，并用这个实例作为Thread的target创建Thread对象
3. 通过Thread对象调用start()方法启动线程

```java
public class MyThread implements Runnable {  
    @Override
    public void run() {
        System.out.println("线程执行");
    }
}
```

```java
public class TestMyThread {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();
        System.out.println("线程已经启动");
    }
}
```

方法二、通过继承Thread类来创建并启动线程

1. 定义Thread的子类，重写run()方法，执行线程体
2. 创建Thread子类的实例
3. 通过实例启动线程

```java
public class MyThread extends Thread {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        System.out.println("线程已经启动");
    }

    @Override
    public void run(){
        System.out.println("线程开始执行");
    }
}
```

**线程的状态：Thread.State**

线程状态。 线程可以处于以下状态之一： 

1. NEW （new）
   尚未启动的线程处于此状态。 
2. RUNNABLE （runnable）
   在Java虚拟机中执行的线程处于此状态。 
3. BLOCKED （blocked）
   被阻塞等待监视器锁定的线程处于此状态。 
4. WAITING （waiting）
   正在等待另一个线程执行特定动作的线程处于此状态。 
5. TIMED_WAITING （timed_waiting）
   正在等待另一个线程执行动作达到指定等待时间的线程处于此状态。 
6. TERMINATED (terminated)
   已退出的线程处于此状态。

如下：

```java
public class MyThread extends Thread {

    public static void main(String[] args){
        MyThread myThread = new MyThread();
        System.out.println("线程状态0："+myThread.getState());
        myThread.start();
        System.out.println("线程已经启动");
        System.out.println("线程状态1："+myThread.getState());
    }

    @Override
    public void run(){
        System.out.println("线程开始执行");
    }
}
```

```
线程状态0：NEW
线程已经启动
线程状态1：RUNNABLE
线程开始执行
```

------

#### 线程生命周期

线程的生命周期就是线程的5中状态：

​	新建(New)、就绪(Runnable)、运行(Running)、阻塞(Blocked)、死亡(Dead)

新建状态：当程序使用new 创建一个线程，那么该线程就处于新建状态，仅由JVM为其分配内存，并初始化其成员变量；

就绪状态：当线程对象调用了start()方法，该线程就进入了就绪状态，随时可以被cpu调用。

运行状态：如果被cpu调用那么就进入了运行状态，若执行顺利就会到达死亡状态，当然也可能被阻塞

阻塞状态：如果线程在执行过程中sleep(),wait(),IO阻塞，那么就进入到了阻塞状态，若是睡眠，当睡眠时间到了结束睡眠，若是等待，可以通过notify()结束等待，或者知道IO阻塞解除，结束阻塞状态后线程回到就绪状态等待调用

死亡状态：当线程被使用完后就死亡

![](D:\工具\StuFile\pdf\线程生命周期.png)

**注意：**线程从阻塞状态只能进入就绪状态，无法直接进入运行状态。而就绪和运行状态之间的转换通常不受程序控制，而是由系统线程调度所决定。当处于就绪状态的线程获得处理器资源时，该线程进入运行状态；当处于运行状态的线程失去处理器资源时，该线程进入就绪状态。但有一个方法例外，调用yield()方法可以让运行状态的线程转入就绪状态。关于yield()方法后面有更详细的介纽。

**线程常用方法**

| 返回值         | 方法                          | 功能                                                         |
| -------------- | ----------------------------- | ------------------------------------------------------------ |
| static  Thread | currentThread()               | 返回对当前正在执行的线程对象的引用。                         |
| long           | getId()                       | 返回此线程的标识符。                                         |
| String         | getName()                     | 返回此线程的名称。                                           |
| int            | getPriority()                 | 返回此线程的优先级。                                         |
| Thread.State   | getState()                    | 返回此线程的状态。                                           |
| ThreadGroup    | getThreadGroup()              | 返回此线程所属的线程组。                                     |
| void           | interrupt()                   | 中断这个线程。                                               |
| static boolean | interrupted()                 | 测试当前线程是否中断。                                       |
| boolean        | isAlive()                     | 测试这个线程是否活着                                         |
| void           | join()                        | 等待这个线程死亡。                                           |
| void           | join(long millis)             | 等待这个线程死亡最多 `millis`毫秒。                          |
| void           | run()                         | 如果这个线程使用单独的`Runnable`运行对象构造，则调用该`Runnable`对象   的`run`方法;  否则，此方法不执行任何操作并返回。 |
| void           | setName()                     | 将此线程的名称更改为等于参数 `name`                          |
| void           | setPriority(int new Priority) | 更改此线程的优先级(1-10)                                     |
| static void    | sleep(long millis)            | 使当前正在执行的线程以指定的毫秒数暂停（暂时停止执行），具体取决于系统定时器和调度程序的精度和准确性。 |
| void           | start()                       | 导致此线程开始执行; Java虚拟机调用此线程的`run`方法。        |
| 属性           |                               |                                                              |
| static int     | `MAX_PRIORITY`                | 线程可以拥有的最大优先级。                                   |
| static int     | `MIN_PRIORITY`                | 线程可以拥有的最小优先级。                                   |
| static int     | `NORM_PRIORITY`               | 分配给线程的默认优先级。                                     |

------

### 线程池

我们一般使用线程的时候就会创建一个线程，至于创建线程的方式在学习llang包的时候就介绍了，创建线程的方式可以通过实现Runnable，或者继承Thread，那么如果我们遇到并发时可能需要多个线程同时执行，若每次都创建一个新的线程，用完之后再销毁，这样会大大降低系统效率，因为创建线程和销毁线程需要时间。

我们可以通过线程池来解决，线程池就是一个存放线程的容器，在线程池中创建多个线程，有任务执行就到线程池中获取线程，用完之后归还线程到线程池中，后面的任务就可以使用空闲的线程执行。

![](D:\工具\StuFile\pdf\线程池原理图.png)

合理利用线程池的好处：

1.  降低资源消耗。减少创建和销毁线程的次数，每个工作线程都可以重复利用，可执行多个任务。
2. 提高响应速度。当任务到达时，任务可以不需要等到线程创建就可以立即执行。
3. 提高线程的可管理性。可以根据系统承受能力，调整线程池中工作线程的数目，防止因为消耗过多的内存，而把服务器累趴(每个线程需要大约1MB内存，线程开的越多，消耗内存越多)

------

#### 创建线程池

##### Executor  &    ExecutorService

​	**Executor框架**便是Java 5中引入的，其内部使用了线程池机制，它在java.util.cocurrent 包下，通过该框架来控制线程的启动、执行和关闭，可以简化并发编程的操作。因此，在Java 5之后，通过Executor来启动线程比使用Thread的start方法更好，除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：有助于避免this逃逸问题——如果我们在构造器中启动一个线程，因为另一个任务可能会在构造器结束之前开始执行，此时可能会访问到初始化了一半的对象用Executor在构造器中。Eexecutor作为灵活且强大的异步执行框架，其支持多种不同类型的任务执行策略，提供了一种标准的方法将任务的提交过程和执行过程解耦开发，基于生产者-消费者模式，其提交任务的线程相当于生产者，执行任务的线程相当于消费者，并用Runnable来表示任务，Executor的实现还提供了对生命周期的支持，以及统计信息收集，应用程序管理机制和性能监视等机制。

Executor框架包括：线程池，Executor，Executors，ExecutorService，CompletionService，Future，Callable等。

​	**Executor**：一个接口，其定义了一个接收Runnable对象的方法executor，其方法签名为executor(Runnable command),该方法接收一个Runable实例，它用来执行一个任务，任务即一个实现了Runnable接口的类，一般来说，Runnable任务开辟在新线程中的使用方法为：new Thread(new RunnableTask())).start()，但在Executor中，可以使用Executor而不用显示地创建线程：executor.execute(new RunnableTask()); // 异步执行

​	**ExecutorService**：ExecutorService继承了Executor接口，是一个比Executor使用更广泛的子类接口，其提供了生命周期管理的方法，返回 Future 对象，以及可跟踪一个或多个异步任务执行状况返回Future的方法；可以调用ExecutorService的shutdown（）方法来平滑地关闭 ExecutorService，调用该方法后，将导致ExecutorService停止接受任何新的任务且等待已经提交的任务执行完成(已经提交的任务会分两类：一类是已经在执行的，另一类是还没有开始执行的)，当所有已经提交的任务执行完毕后将会关闭ExecutorService。因此我们一般用该接口来实现和管理多线程。

通过 ExecutorService.submit() 方法返回的 Future 对象，可以调用isDone（）方法查询Future是否已经完成。当任务完成时，它具有一个结果，你可以调用get()方法来获取该结果。你也可以不用isDone（）进行检查就直接调用get()获取结果，在这种情况下，get()将阻塞，直至结果准备就绪，还可以取消任务的执行。Future 提供了 cancel() 方法用来取消执行 pending 中的任务。

ExecutorService的生命周期包括三种状态：运行、关闭、终止。创建后便进入运行状态，当调用了shutdown（）方法时，便进入关闭状态，此时意味着ExecutorService不再接受新的任务，但它还在执行已经提交了的任务，当素有已经提交了的任务执行完后，便到达终止状态。如果不调用shutdown（）方法，ExecutorService会一直处在运行状态，不断接收新的任务，执行新的任务，服务器端一般不需要关闭它，保持一直运行即可。

在Java 5之后，任务分两类：一类是实现了Runnable接口的类，一类是实现了Callable接口的类。两者都可以被ExecutorService执行，但是Runnable任务没有返回值，而Callable任务有返回值。并且Callable的call()方法只能通过ExecutorService的submit(Callable task) 方法来执行，并且返回一个 Future，是表示任务等待完成的 Future。

java.util.concurrent  interface  Exector接口只有一个方法：

execute(Runnable command)	调用线程

java.util.concurrent  interface  ExecutorService  常用方法：

isShutdown()	判断ExecutorService是否已经关闭，如果关闭返回true

isTerminated()	判断所有任务在关闭后是否完成，如果完成返回true

shutdown()	启动有序关闭，已经提交的任务继续执行直到任务完成，但是不接受新的任务

submit(Callable<T>  task)	返回任务处理结果future,执行的任务是Callable

submit(Runnable task)	返回任务处理结果future，执行的任务是Runnable

------

##### interface    ScheduledExecutorService

ScheduledExecutorService接口继承了ExecutorService接口，该接口提供了一些方法可以使线程延迟执行或者定时执行

schedule方法创建具有各种延迟的任务，并返回可用于取消或检查执行的任务对象。 scheduleAtFixedRate和scheduleWithFixedDelay方法创建并执行定期运行的任务，直到取消。 

常用方法：

scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) 

API描述：创建并执行在给定的初始延迟之后，随后以给定的时间段首先启用的周期性动作; 那就是执行将在initialDelay之后开始，然后initialDelay+period ，然后是initialDelay + 2 * period ，等等。 如果任务的执行遇到异常，则后续的执行被抑制。 否则，任务将仅通过取消或终止执行人终止。 如果任务执行时间比其周期长，则后续执行可能会迟到，但不会同时执行。

​	创建并执行Runnable线程任务，按照设置的时间延迟，周期性执行任务，方法参数分别为:

​	Runnable command   ——   要执行的线程任务

​	long initialDelay   ——   第一次执行延迟时间

​	long period   ——   任务执行间隔时间

​	TimeUnit unit   ——   long initialDelay和long period的时间单位

```java
//创建一个线程池，设置线程数量为1
ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

//开始执行线程，设置第一次延迟时间为0，即不延迟，设置任务执行间隔为1s，时间单位为秒
scheduledExecutorService.scheduleAtFixedRate(new PollingThread(this), 0, 1, TimeUnit.SECONDS);
```

ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)

API描述：创建并执行在给定的初始延迟之后首先启用的定期动作，随后在一个执行的终止和下一个执行的开始之间给定的延迟。 如果任务的执行遇到异常，则后续的执行被抑制。 否则，任务将仅通过取消或终止执行人终止。

​	创建并执行Runnable线程任务，按照设置的时间延迟，方法参数分别为：

​	Runnable command   ——   要执行的线程任务

​	long initialDelay   ——   第一次执行延迟时间

​	 long delay   ——   前一个任务执行终止与下一个任务执行开始之间的延迟时间

​	TimeUnit unit   ——   long initialDelay和long delay 的时间单位

schedule(Callable<V> callable, long delay, TimeUnit unit)  和  schedule(Runnable command, long delay, TimeUnit unit) 

​	以上两个方法类似就是创建并执行任务，设置每次任务延迟时间，方法参数分别为：

​	Runnable command   ——   要执行的线程任务

​	 long delay   ——   从现在开始延迟执行的时间 

​	TimeUnit unit   ——   long initialDelay和long delay 的时间单位、

------

##### interface    ThreadFactory

根据需要创建新线程的对象。 使用线程工厂可以删除new Thread的硬连线 ，使应用程序能够使用特殊的线程子类，优先级等。

它有一个方法：new Thread(Runnable r)  返回Thread构建一个新的Thread

------

##### class  Executors

​	java.util.concurrent.Executors 主要提供线程池相关操作。

defaultThreadFactory()	返回ThreadFactory  用于创建新线程的默认线程工程

**java通过Executors提供四种线程池**

1. newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

   newCachedThreadPool()	返回ExecutorService，创建一个线程池，如果没有空闲线程执行任务，就会创建新线程

   newCachedThreadPool(ThreadFactory threadFactory) 	使用该方法可以通过ThreadFactory创建线程

   

2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

   newFixedThreadPool(int nThreads) 	返回ExecutorService，创建一个线程池，该线程池中线程树龄固定。 在任何时候，最多nThreads线程将处于主动处理任务。 如果所有线程处于活动状态时，提交的其他任务将在等待队列中直到有线程可用。 如果任何线程由于在关闭之前的执行期间发生故障而终止，则如果需要执行后续任务，需要等待新线程，本线程会被新任务占用。 池中的线程将一直存在，直到它明确地使用shutdown 关闭线程。

   newFixedThreadPool(int nThreads, ThreadFactory threadFactory) 	设置线程最大数量，线程的创建可以只用ThreadFactory。

   

3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。

   newScheduledThreadPool(int corePoolSize) 	返回ScheduleExecutorService，创建一个线程池，可以设置保留在线程池中线程的数量，即使它们处于空闲状态。

   newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) 	通过ThreadFactory创建线程

   

4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行

   newSingleThreadExecutor() 	返回ExecutorService，创建一个单线程线程池

   newSingleThreadScheduledExecutor(ThreadFactory threadFactory) 	通过ThreadFactory创建线程

------

##### class  CountDownLatch

java.util.concurrent  CountDownLatch是一种通用的同步工具，类似一个线程计数器，例如：我们有多个线程同时执行任务，但是有一个线程在执行任务过程中也许某个过程，需要等其他线程都完成之后才能继续执行，那么就需要有一个类似监听其他线程是否完成的工具，当其他线程完成后通知那个等待的线程继续执行任务。这种场景可能就会用到CountDownLatch。如下：

```java
//创建一个CountDownLatch设置开始线程数量
CountDownLatch countDownLatch = new CountDownLatch(hosts.size());
        try {
            for (String host : hosts) {
                newCachedThreadPool.execute(new SendThread(this, host, countDownLatch));
            }
            //主线程等待，当线程数量为0时，主线程开始继续执行
            countDownLatch.await();
            sendTemporaryCache.deleteRulesList();
        } catch (InterruptedException e) {
            logger.error("执行更新规则线程异常", e);
        }
==================================================================================================
//这一部分是每个线程都要执行的部分
try {
            URL getUrl = new URL("http://" + host + RULEAPI_GET_NEW_RULES);
            String result = HttpClientUtils.post(getUrl.toString(), 
                                                 new HashMap<String, Object>(16) {
                {
                    put("rule", JSON.toJSONString(sendTemporaryCache.getRulesList()));
                }
            }, null, ContentType.APPLICATION_JSON);
            if (FAILD.equals(result)) {
                while (number < MAXNUMBER) {
                    number++;
                    String rs = sendRules(host, countDownLatch);
                    if (StringUtils.equals(SUCCESS, rs)) {
                        number = 0;
                        return SUCCESS;
                    }
                }
                logger.error("服务器{}更新规则失败",host);
                return FAILD;
            }
        } catch (Exception e) {
            logger.error("发送新规则异常");
        } finally {
    //每次线程执行完都要调用countDown()方法，减少1个线程
            countDownLatch.countDown();
        }
        return SUCCESS;
    }
```

常用的方法：

await() 	线程等待，直到线程锁存器计数为零才继续执行。

await(long timeout, TimeUnit unit) 	类似上面的方法，线程等待，直到线程锁存器计数为零或者达到等待时间才继续执行

countDown() 	减少锁存器的计数，如果计数达到零，释放所有等待的线程

getCount() 	返回当前计数。

------

##### interface Future

API描述：A Future计算的结果。 提供方法来检查计算是否完成，等待其完成，并检索计算结果。 结果只能在计算完成后使用方法get进行检索，如有必要，阻塞，直到准备就绪。 取消由cancel方法执行。 提供其他方法来确定任务是否正常完成或被取消。 计算完成后，不能取消计算。 如果您想使用Future ，以便不可撤销，但不提供可用的结果，则可以声明Future<?>表格的类型，并返回null作为基础任务的结果。 

常用方法：

boolean cancel(boolean mayInterruptIfRunning)	取消执行的任务，若mayInterruptIfRunning为true，则执行的任务应该被中断，反之，允许完成。

get() 	等待计算完成，然后检索其结果。如果任务没有完成那就等待，直到获取到结果

 get(long timeout, TimeUnit unit) 	如果需要等待最多在给定的时间计算完成，然后检索其结果（如果可用）。 

isCancelled() 	如果此任务在正常完成之前被取消，则返回 true 。

isDone() 	返回 true如果任务已完成。 

Future模式，实现多个线程同时执行任务，其中有某个线程需要用到其他线程的结果。类似CountDownLatch使用场景

https://www.cnblogs.com/cz123/p/7693064.html   场景介绍不错

https://blog.csdn.net/u014209205/article/details/80598209	结构分析

当然类似场景也可以使用join()方法

```java

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		
		// 等凉菜 -- 必须要等待返回的结果，所以要调用join方法
		Thread t1 = new ColdDishThread();
		t1.start();
		t1.join();
		
		// 等包子 -- 必须要等待返回的结果，所以要调用join方法
		Thread t2 = new BumThread();
		t2.start();
		t2.join();
		
		long end = System.currentTimeMillis();
        System.out.println("准备完毕时间："+(end-start));
    }
		
```

还有可以通过Fork/Join

![](D:\工具\StuFile\pdf\forkjoin.png)

------

#### 自定义线程池

**注意：**最近阿里发布的 Java开发手册中强制线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险

https://www.cnblogs.com/dolphin0520/p/3932921.html   java并发编程：线程池的使用

如果并发的线程数量很多，并且每个线程都是执行一个时间很短的任务就结束了，这样频繁创建线程就会大大降低系统的效率，因为频繁创建线程和销毁线程需要时间。

　　那么有没有一种办法使得线程可以复用，就是执行完一个任务，并不被销毁，而是可以继续执行其他的任务？

　　在Java中可以通过线程池来达到这样的效果。今天我们就来详细讲解一下Java的线程池，首先我们从最核心的ThreadPoolExecutor类中的方法讲起，然后再讲述它的实现原理，接着给出了它的使用示例，最后讨论了一下如何合理配置线程池的大小。

　　以下是本文的目录大纲：

　　一.Java中的ThreadPoolExecutor类

　　二.深入剖析线程池实现原理

------

##### 一.Java中的ThreadPoolExecutor类

​	java.uitl.concurrent.ThreadPoolExecutor类是线程池中最核心的一个类，因此如果要透彻地了解Java中的线程池，必须先了解这个类。下面我们来看一下ThreadPoolExecutor类的具体实现源码。

```java
public class ThreadPoolExecutor extends AbstractExecutorService {
    .....
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
            BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler);
 
    public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
        BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler);
    ...
}
```

从上面的代码可以得知，ThreadPoolExecutor继承了AbstractExecutorService类，并提供了四个构造器，事实上，通过观察每个构造器的源码具体实现，发现前面三个构造器都是调用的第四个构造器进行的初始化工作。

下面解释下一下构造器中各个参数的含义：

- **corePoolSize**：核心池的大小，这个参数跟后面讲述的线程池的实现原理有非常大的关系。在创建了线程池后，默认情况下，线程池中并没有任何线程，而是等待有任务到来才创建线程去执行任务，除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法，从这2个方法的名字就可以看出，是预创建线程的意思，即在没有任务到来之前就创建corePoolSize个线程或者一个线程。默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；

- **maximumPoolSize**：线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；当队列满时，会创建线程执行任务直到线程池中的数量等于maximumPoolSize。

  

- **keepAliveTime**：表示线程没有任务执行时最多保持多久时间会终止。默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0；

  

- **unit**：参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性：

```
TimeUnit.DAYS;               //天
TimeUnit.HOURS;             //小时
TimeUnit.MINUTES;           //分钟
TimeUnit.SECONDS;           //秒
TimeUnit.MILLISECONDS;      //毫秒
TimeUnit.MICROSECONDS;      //微妙
TimeUnit.NANOSECONDS;       //纳秒
```

- **workQueue**：一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：　ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。

```
ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
DelayQueue： 一个使用优先级队列实现的无界阻塞队列。
SynchronousQueue： 一个不存储元素的阻塞队列。
LinkedTransferQueue： 一个由链表结构组成的无界阻塞队列。
LinkedBlockingDeque： 一个由链表结构组成的双向阻塞队列。
```

- **threadFactory**：线程工厂，主要用来创建线程；

- **handler**：表示当拒绝处理任务时的策略，有以下四种取值

```
ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。 
ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。 
ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务 
```

Executor是一个顶层接口，在它里面只声明了一个方法execute(Runnable)，返回值为void，参数为Runnable类型，从字面意思可以理解，就是用来执行传进去的任务的；然后ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、invokeAny以及shutDown等；抽象类AbstractExecutorService实现了ExecutorService接口，基本实现了ExecutorService中声明的所有方法；然后ThreadPoolExecutor继承了类AbstractExecutorService。在ThreadPoolExecutor类中有几个非常重要的方法：

```
execute()
submit()
shutdown()
shutdownNow()
```

 　　execute()方法实际上是Executor中声明的方法，在ThreadPoolExecutor进行了具体的实现，这个方法是ThreadPoolExecutor的核心方法，通过这个方法可以向线程池提交一个任务，交由线程池去执行。

　　submit()方法是在ExecutorService中声明的方法，在AbstractExecutorService就已经有了具体的实现，在ThreadPoolExecutor中并没有对其进行重写，这个方法也是用来向线程池提交任务的，但是它和execute()方法不同，它能够返回任务执行的结果，去看submit()方法的实现，会发现它实际上还是调用的execute()方法，只不过它利用了Future来获取任务执行结果（Future相关内容将在下一篇讲述）。

　　shutdown()和shutdownNow()是用来关闭线程池的。

------

##### 二.深入剖析线程池实现原理

　　1.线程池状态

　　2.任务的执行

　　3.线程池中的线程初始化

　　4.任务缓存队列及排队策略

　　5.任务拒绝策略

　　6.线程池的关闭

　　7.线程池容量的动态调整

------

1.线程池状态

　　在ThreadPoolExecutor中定义了一个volatile变量，另外定义了几个static final变量表示线程池的各个状态：

```java
volatile int runState;
static final int RUNNING    = 0;
static final int SHUTDOWN   = 1;
static final int STOP       = 2;
static final int TERMINATED = 3;
```

​	runState表示当前线程池的状态，它是一个volatile变量用来保证线程之间的可见性；

　　下面的几个static final变量表示runState可能的几个取值。

　　当创建线程池后，初始时，线程池处于RUNNING状态；

　　如果调用了shutdown()方法，则线程池处于SHUTDOWN状态，此时线程池不能够接受新的任务，它会等待所有任务执行完毕；

　　如果调用了shutdownNow()方法，则线程池处于STOP状态，此时线程池不能接受新的任务，并且会去尝试终止正在执行的任务；

　　当线程池处于SHUTDOWN或STOP状态，并且所有工作线程已经销毁，任务缓存队列已经清空或执行结束后，线程池被设置为TERMINATED状态。

------

2.任务的执行

　　在了解将任务提交给线程池到任务执行完毕整个过程之前，我们先来看一下ThreadPoolExecutor类中其他的一些比较重要成员变量：

```java
private final BlockingQueue<Runnable> workQueue;              //任务缓存队列，用来存放等待执行的任务
private final ReentrantLock mainLock = new ReentrantLock();   //线程池的主要状态锁，对线程池状态（比如线程池大小
                                                              //、runState等）的改变都要使用这个锁
private final HashSet<Worker> workers = new HashSet<Worker>();  //用来存放工作集
 
private volatile long  keepAliveTime;    //线程存货时间   
private volatile boolean allowCoreThreadTimeOut;   //是否允许为核心线程设置存活时间
private volatile int   corePoolSize;     //核心池的大小（即线程池中的线程数目大于这个参数时，提交的任务会被放进任务缓存队列）
private volatile int   maximumPoolSize;   //线程池最大能容忍的线程数
 
private volatile int   poolSize;       //线程池中当前的线程数
 
private volatile RejectedExecutionHandler handler; //任务拒绝策略
 
private volatile ThreadFactory threadFactory;   //线程工厂，用来创建线程
 
private int largestPoolSize;   //用来记录线程池中曾经出现过的最大线程数
 
private long completedTaskCount;   //用来记录已经执行完毕的任务个数
```

 　　每个变量的作用都已经标明出来了，这里要重点解释一下corePoolSize、maximumPoolSize、largestPoolSize三个变量。

　　corePoolSize在很多地方被翻译成核心池大小，其实我的理解这个就是线程池的大小。举个简单的例子：

　　假如有一个工厂，工厂里面有10个工人，每个工人同时只能做一件任务。

　　因此只要当10个工人中有工人是空闲的，来了任务就分配给空闲的工人做；

　　当10个工人都有任务在做时，如果还来了任务，就把任务进行排队等待；

　　如果说新任务数目增长的速度远远大于工人做任务的速度，那么此时工厂主管可能会想补救措施，比如重新招4个临时工人进来；

　　然后就将任务也分配给这4个临时工人做；

　　如果说着14个工人做任务的速度还是不够，此时工厂主管可能就要考虑不再接收新的任务或者抛弃前面的一些任务了。

　　当这14个工人当中有人空闲时，而新任务增长的速度又比较缓慢，工厂主管可能就考虑辞掉4个临时工了，只保持原来的10个工人，毕竟请额外的工人是要花钱的。

 

　　这个例子中的corePoolSize就是10，而maximumPoolSize就是14（10+4）。

　　也就是说corePoolSize就是线程池大小，maximumPoolSize在我看来是线程池的一种补救措施，即任务量突然过大时的一种补救措施。

　　不过为了方便理解，在本文后面还是将corePoolSize翻译成核心池大小。

　　largestPoolSize只是一个用来起记录作用的变量，用来记录线程池中曾经有过的最大线程数目，跟线程池的容量没有任何关系。

------

3.线程池中的线程初始化

　　默认情况下，创建线程池之后，线程池中是没有线程的，需要提交任务之后才会创建线程。

　　在实际中如果需要线程池创建之后立即创建线程，可以通过以下两个方法办到：

prestartCoreThread()：初始化一个核心线程；
prestartAllCoreThreads()：初始化所有核心线程

```java
public boolean prestartCoreThread() {
    return addIfUnderCorePoolSize(null); //注意传进去的参数是null
}
 
public int prestartAllCoreThreads() {
    int n = 0;
    while (addIfUnderCorePoolSize(null))//注意传进去的参数是null
        ++n;
    return n;
}
```

------

4.任务缓存队列及排队策略

　　在前面我们多次提到了任务缓存队列，即workQueue，它用来存放等待执行的任务。

　　workQueue的类型为BlockingQueue<Runnable>，通常可以取下面三种类型：

　　1）ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；

　　2）LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；

　　3）synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。

------

5.任务拒绝策略

　　当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：

```
ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
```

------

6.线程池的关闭

　　ThreadPoolExecutor提供了两个方法，用于线程池的关闭，分别是shutdown()和shutdownNow()，其中：

shutdown()：不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
shutdownNow()：立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务

------

7.线程池容量的动态调整

　　ThreadPoolExecutor提供了动态调整线程池容量大小的方法：setCorePoolSize()和setMaximumPoolSize()，

setCorePoolSize：设置核心池大小
setMaximumPoolSize：设置线程池最大能创建的线程数目大小
　　当上述参数从小变大时，ThreadPoolExecutor进行线程赋值，还可能立即创建新的线程来执行任务。























