## 第一部分   基础知识

### 第一章   关心 **java8** 的原由

#### 总纲

- 思想汇总
  1. stream API
  2. 向方法传递代码
  3. 接口中默认方法
- 阅读问题
  1. java 演变过程，简易多核并行能力
  2. 新的编程语汇，代码传递给方法
  3. Streams介绍，数据并行处理的新方式
  4. 如何利用 java8 中默认方法，提高接口编程效率
  5. java 与其他语言 在jvm中的 函数式编程思想

### 1.1 整体介绍

1. 思想总则：面向对象编程，一切都是对象
2. **问题：**大数据处理，支持多核并发编程（**为什么？从哪些地方体现**）

#### 1.1.1 编程概念

##### 1、 Stream--流处理

> 流： 指一系列数据项，程序可以从输入流中读取数据，然后以同样的方式将数据项写入流中。一个程序的输出流可能是另一个程序的书输入流

流水线工作：并行处理；

![Streams-流并行处理](images\Streams-流并行处理.png)

##### 2、 行为参数化-------代码传递给方法

```java
inventory.sort(comparing(Apple::getWeight));
```

##### 3、并行与共享的可变数据

1. 同时对不同输入安全执行（不能访问共享可变数据）
2. 纯函数，无副作用函数、无状态函数（关注7、13 章）
3. 两个进程同时修改共享变量解决方案（关注1.3小节内容 ）

#### 1.2 java中的函数

- 一等值（一等公民）-------- 基本数据类型，引用数据类型
- 二等值（二等公民）-------- 方法、类

##### 1.2.1 lambda 表达式 （方法提升为一等值，作为参数直接传递）

1. 方法的引用  （ **: :** ） 将方法引用**File::isHidden**传递给**listFiles**方法  

   ```
   File[] hiddenFiles = new File("").listFiles(File :：isHidden);
   ```

2. lambda，匿名函数----------***编写把函数作为一等值来传递的程序***

##### 1.2.2 代码传递

1. 谓词：predicate 比较筛选指定数据 -------- （***一个返回boolean值的函数***）
   - 代码传递了方法 Apple **: :** isGreenApple
   - 使用 Predicate<T>  p 接收
2. 方法传递到 Lambda表达式
   - filterApples(applesList, apple -> apple.getColor.equals("green") )

#### 1.3 流

  **Stream API** 重点关注 第四章~第七章

思想：

- 集合---collection API 外部迭代
- Stream API  内部迭代
  - stream  顺序处理
  - parallelStream 并行处理

#### 1.4 默认方法

​	提供接口方法扩展方式，不会破坏原有代码结构 使用 **default** 关键字表示（实现类没有实现方法时提供方法内容，也可以重写）

#### 1.5 函数式编程思想

1. 将方法与lambda作为一等值
2. 没有可共享状态时，函数与方法可以有效，安全地并行执行

### 第二章   行为参数化传递代码

#### **总则：**处理频繁变更的需求的一种软件开发模式

#### 2.1 ---  行为参数化，代码演变

*策略模式简用*

![简单策略模式](images\简单策略模式.png)

### 第三章  **Lambda表达式**

#### **3**.1  Lambda 简介

> **定义** ： 简洁，可传递的匿名函数，没有名称，有参数列表，函数主体，返回值。可能还有抛出的异常列表

- 匿名：方法没有明确名称，写的少想的多
- 函数：不属于特定的类，但是与方法一样
- 传递：可以传递给方法或存储在变量中
- 简洁：无需像匿名类一样写很多模板代码

```java
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

```java
1、() -> {}
2、() -> "wahaha"
3、() -> {return "wahaha";}
4、(Integer i) -> return "wahaha"+i
5、(String a) -> {"wahaha";}
```

> 上诉 4 5 不是lambda表达式 隐式 返回值***不需要带有花括号***，显式返回值需要有 ***returen*** 关键字 且带有 **；**与***花括号***

#### 3.2 使用lambda表达式场景or使用方式

1. 函数式接口：***只定义一个抽象方法的接口*** ,即使接口中有很多默认方法，只要接口只定义了一个抽象方法，它仍然是一个函数式接口

   ```java
   Interface Predicate<T>{
   	boolean test(T t);
   }
   Interface Runnable{
       void run();
   }
   Runnable r = () -> Syetem.out.println("hello word");
   public static void process(Runnable r){
       r.run()
   }
   process(r);
   ```

   - **问题：** Lambda表达式是如何做类型检查的?

2. 函数式接口注解

   - **@FunctionalInterface :**  接口上添加该注解，表述该接口中只能有一个抽象方法，是一个函数式接口 类似于 @Override 标准表示方法被重写了

#### 3.3 Lambda实践，环绕执行模式

1. try-catch-resource 隐式关闭流原理

   > 链接对象必须实现了 ***AutoCloseable*** 或 ***Closeable*** 接口

#### 3.4 函数式接口介绍

##### 1. Predicate<T>（谓词）

​	**使用场景 ：** 需要表示涉及类型 **T** 的布尔表达式时可以使用

> 定义一个接收String对象的Lambda表达式

```java
@FunctionlInterface
interface Predicate<T>{
    boolean test(T t);
}
public static <T> List<T> filter(List<T> list, Predicate<T> p){
    List<T> result = new ArrayList<>();
    for (T l: list) {
        if(p.test(l)){
            result.add(l);
        }
    }
    return result;
}
filter(list, s -> s != "");
//s -> s != "" 这个Lambda表达式是 Predicate接口中 test方法的实现
```

##### 2. Consumer<T>  消费者 

​	**使用场景：** 需要访问的类型是 **T** 并对其执行某些操作 就可以使用该接口

```java
@FunctionlInterface
interface Consumer<T>{
	void accept(T t);
}
public static <T> void forEach(List<T> list, Consumer<T> c){
	for(T l: list){
		c.accept(l)
	}
}
forEach(list, t -> System.out.println(t));
```

##### 3. Function<T, R>  功能

**使用场景：** 接收一个泛型 **T** 对象，返回一个泛型 **R** 对象，将输入对像应映射到输出对象中

```java
@FunctionlInterface
interface Function<T, R>{
	R apply(T, R);
}
public static <T, R> List<R> map(List<T> list, Function<T, R> f){
	List<R> result = new ArrayList<>();
	for(T l: list){
		result.add(f.apply(l))
	}
	return result;
}
List<Integer> accept = map(list, s -> s.length());
```

**备注：** 泛型只能绑定到引用类型，（基本类型无法使用泛型）

- **问题：** 泛型内部实现导致，16章会有解释（需要补充）

| 函数式接口        | 函数描述      | 原始类型特化                                                 |
| ----------------- | ------------- | ------------------------------------------------------------ |
| Predicate<T>      | T - > boolean | IntPredicate,LongPredicate, DoublePredicate                  |
| Consumer<T>       | T -> void     | IntConsumer,LongConsumer, DoubleConsumer                     |
| Function<T,R>     | T -> R        | IntFunction<R>,IntToDoubleFunction, IntToLongFunction, LongFunction<R>, <br> LongToDoubleFunction, LongToIntFunction, DoubleFunction<R>, ToIntFunction<T>, <br/>ToDoubleFunction<T>, ToLongFunction<T> |
| Supplier<T>       | () -> T       | BooleanSupplier, IntSupplier,  LongSupplier,  DoubleSupplier |
| UnaryOperator<T>  | T -> T        | IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperator     |
| BinaryOperator<T> | (T, T) -> T   | IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator  |
| BiFunction<T,U,R> | (T, U) -> R   | ToIntBiFunction<T,U>,<br/>ToLongBiFunction<T,U>,<br/>ToDoubleBiFunction<T,U> |
| BiConsumer<T,U>   | (T,U)->void   | ObjIntConsumer<T>, ObjLongConsumer<T>, ObjDoubleConsumer<T>  |



| 使用案例                         | Lamdba                                                       | 对应函数式接口                                               |
| -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 布尔表达式                       | (List<String> list) -> list.isEmpty()                        | Predicate<T>                                                 |
| 创建一个对象                     | () -> new Apple ()                                           | supplier<T>                                                  |
| 消费一个对象                     | s -> System.out.println();                                   | Consumer<T>                                                  |
| 从一个对象中<br/>提取 **/** 选择 | s -> s.length()                                              | Function<T, R> <br>ToIntFunction<String>                     |
| 合并                             | （int a , int b）-> a*b                                      | IntBinaryOperator                                            |
| 比较两个对象                     | (Apple a1, Apple a2)  -> <br/>a1.getWeight().compareTo(a2.getWeight())<br/> | Comparator<Apple><br/>BiFunction<Apple, Apple, Integer><br/>ToIntBiFunction<Apple, Apple> |

**备注：** 任何函数式接口都不允许抛出受检异常

1. 定义一个自己的函数式接口，并声明受检异常
2. 在Lambda表达是中添加 **try-catch** 块

#### 3.5 类型检查，类型推断以及限制

​	Lambda表达式为函数式接口的方法提供实现，整个表达式作为接口的一个实例

##### 3.5.1 类型检查 

​	使用目标类型来检查Lambda是否可以用用于某个特定的上下文

​				![image-20200717013726678](images\Lambda表达式类型检查解读.png)

***上下文：*** 存储系统的一些初始化信息，如：配置文件，数据资源，保证程序运行的先决条件，数据基础（全局的存储信息空间）

```java
HttpSession session = request.getSession()
session.getServletContext()
```

​	工程环境，包含配置信息，比如：spring上下文，代表spring配置环境信息，配置文件、获得上下文也就是获得了环境信息

- Spring上下文就是系统启动的时候Spring会读取它的配置文件装载到上下文到内存，依赖注入...
- 简单理解就是spring的当前运行的环境，也可以理解是spring可以利用的资源
- 上下文切换，其实就是环境切换：
  比如咱们打电话，你现在正在接一个张三的电话，突然李四给你打电话了，你得和张三说：“稍等我接一个电话”，然后你接起来李四的电话和李四说：“我现在有点事，
  一会给你回过去”，然后你又挂断了李四的，给张三打。这就是上下文切换。上下文就是你执行程序的一个环境，存储的一些变量、等，就和你的大脑，
  一样存储的一些记忆，一个道理

**方法上下文：** 参数与返回值

##### 3.5.2 使用局部变量

​	Lambda表达式中使用**局部变量**必须显示声明为 final 或事实上是 final

​	![image-20200717021553879](images\Lambda表达式中局部变量为final类型.png)

> 实例变量存储在堆中，局部变量保存在栈上

##### 3.5.3  方法引用

> 升序排列是把数据从小到大进行排列，而降序排列是把数据从大到小进行排列

**如何构建方法引用：**  （**必须使用对象名**）

1. 指向静态方法的引用 —（Integer **::** parseInt）
2. 指向任意类型实例的方法引用——(String **::** length )
3. 指向现有对象实例方法的引用—— (Apple **::** getWeigth)

#### 3.8 复合Lambda表达式

> Comparator、Function、Predicate 都提供了可复合的方法

1. 比较器复合

   - 逆序 ----   3、2、1

     ```java
     list.sort(comparing(Apple :: getWeight).reversed())
     ```

   - 比较器链

     ```java
     list.sort(comparing(Apple :: getWeight).reversed().thenComparing(Apple :: getCountry))
     ```

2. 谓词复合

   ```java
   negate、and、or ---- 非，和，或
   ```

   > a**.or**(b)**.and**(c)  等价与 （a || b）& c  **and和or方法在链表中的位置确定优先级**

   

## 第二部分 数据处理

### 第四章 引入流

#### 4.1 什么是流

1. 遍历数据的高级迭代器
2. 以声明性的方式出来了数据集合（通过查询语句来表达）
3. 透明并行处理数据，无需写多线程代码

**java8 中StreamAPI 写代码特点**

1. 声明性——简洁，易读
2. 可复合——灵活
3. 可并行——性能更好 （**stream()**   多核并行处理使用 **parallelStream()** ）

![image-20200723012803204](images\基础操作链接组合.png)

#### 4.2 流简介

```java
Dish.menu.stream().filter(dish -> dish.getCalories()>300)
.sorted(comparing(Dish::getCalories))
.map(Dish::getName)
.limit(4)
.collect(toList());
```

过滤 —> 排序 —> 转换 —> 截断 —> 收集

```java
collect.stream().collect(groupingBy(Dish :: getType)) //分组
```



#### 4.3 流与集合

**中间操作 :**  filter  map  sorted limit  distinct ..... (处理数据)

**终端操作：**collect   count  forEach（生成结果）

流的使用包含三个时间：

- 数据源（集合）--- 执行查询
- 中间操作链、流水线
- 终端操作、生成结果集

### 第五章 使用流

#### 5.1  截短流 --- 跳过元素

> limit  与 skip 互补

- **limit(2)** 返回 流中前两个元素
- **skip(2)** 返回流中除了前两个元素之外的其他元素

#### 5.2 映射 —— faltMap (Function<? supper T, ? extend <Stream<? R>>   mappper )

```java
        String[] aaa = {"hello", "world"};
        List<String[]> arrayStream = Arrays.stream(aaa).map(a -> a.split("")).collect(toList());
        List<String> stringStream = Arrays.stream(aaa).map(a -> a.split("")).flatMap(Arrays::stream).collect(toList());
```

- List<String[]> arrayStream

  ![image-20200725002713007](images\流扁平化演进.png)

> map 映射出来的是一个  Stream<String[]> 数组流  需要的是一个 Stream<String> 字符流
>
> faltMap 支持流持续传递、实现扁平化处理

```java
	/**
	 * 	使用flatMap方法的效果是，各个数组并不是分别映射成一个流，而是映射成流的内容。所
	 *	有使用map(Arrays::stream)时生成的单个流都被合并起来，即扁平化为一个流。图5-6说明了
	 *  使用flatMap方法的效果
	/
	List<String> uniqueCharacters =words.stream()
								.map(w -> w.split(""))
								.flatMap(Arrays::stream)
								.distinct()
								.collect(Collectors.toList());
```

![image-20200725003336889](images\flatmap持续处理流图解.png)

#### 5.3 流操作

1.  查找、匹配
   - allMatch、anyMatch、noneMatch、findMatch、findAny —— 终端操作
   - allMatch、anyMatch、noneMatch —— 短路操作    **&& | |** 
   - 无需计算整个表达式，只要有一个是false 就可以返回 false 
   - findMathc —— 返回当前流中的任意元素

```
findFirst  与 findAny 并行操作 （不关心返回哪个元素，使用 findAny ---> 使用并行限制少）
```

#### 5.4 归约 —— reduce

**说明： 将流中元素 组合**

1. 元素求和

```java
List<Integer> sum ;
sum.reduce(0, (a, b) -> a + b);
//Integer 类中静态方法 sum
sum.reduce(0, Integer :: sum);
```

- 中间操作和终端操作

  ![image-20200728004059815](images\中间操作和终端操作.png)

  

#### 5.5 数值流

**写代码重点思考：** 操作基本数据时候需要注意 自动拆装箱操作 消耗 ——  **装箱成本** 

```java
int sum = menu.stream().map(Dish :: getValue).reduce(0, Integer :: sum);//暗含装箱操作，内存消耗多
int sum = menu.stream().map(Dish :: getValue).sum() //这个只是代码简化
//StreamAPI提供了 原始类型流特化，专门支持处理数值流
menu.stream().mapToInt(Dish :: getValue).sum() // 直接操作int类型数据 避免装箱操作
// 三种原始类型特化流 
// IntStream、DoubleStream、LongStream
```

1. 映射流到数值

   - 流转换为特化版本的常用方法是mapToInt、 mapToDouble和mapToLong  

   - mapToInt -- 返回值是 IntStream

     ```java
     int calories = menu.Stream().mapToInt(Dish::getCalories).sum()
     ```

     

2. 转换回流对象

   - 原始流转换成一般流   **boxed()** 

   ```java
   IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
   Stream<Integer> stream = intStream.boxed();
   ```

3. 默认值**OptionalInt** 

   ```java
    /*对于三种原始流特化，也分别有一个Optional原始类型特化版本： OptionalInt、 OptionalDouble和OptionalLong。
       例如，要找到IntStream中的最大元素，可以调用max方法，它会返回一个OptionalInt：*/
   OptionalInt maxCalories = menu.stream()
   .mapToInt(Dish::getCalories)
   .max();
   //现在，如果没有最大值的话，你就可以显式处理OptionalInt去定义一个默认值了：
   int max = maxCalories.orElse(1);
   ```


#### 5.6 数值范围

```java
//取 1 到 100 之间的数字 range 与 rangeClosed 方法第一个参数接收起始值，第二个参数接收结束值
IntStream is = IntStream.range(1, 100); //左闭右开，不包含100
IntStream.rangeClosed(1, 100); //左开右开，包含100
```

1. 勾股数 **(看的迷糊不太懂---后期回顾)**

   ```
   is.filter(b ->(a*a + b*b) % == 0).boxed().map
   ```

#### 5.7 构建流

1. #### 静态方法  Stream.of("aaa", "bbb");

2. #### 数组创建 Arrays.stream( 【数组】) 

```java
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldPre = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPre;
            }
        };
        // getAsInt 在调用时会改变对象状态。在并行操作下不推荐使用 generate
        IntStream.generate(fib).limit(20).forEach(System.out::println);

        //iterate 的方法是纯粹不变的，没有修改现有状态，但是每次迭代都会创建新的元素
        Stream<int[]> limit = Stream.iterate(fbnumb, f -> new int[]{f[1], f[0] + 1}).limit(20);
        limit.forEach( t -> System.out.println(t[0] + "===" + t[1]));
```

### 第六章 用流收集数据

#### 6.1 收集器简介

```java
// 数据总数
collectors.counting(); Stream().count();
// 最大值，最小值 maxBy、minBy
collectors.maxBy(Comparing(Dish::getCalories))
// 求和
summingInt(Dish::getCalories);
// 平均数
averagingInt(Dish::getCalories);
// 获得以上所有类型值的收集器 summarizingInt 工厂
// summarizingInt 提供了get 方法来访问结果
IntSummaryStatistics menuStatistics =
		menu.stream().collect(summarizingInt(Dish::getCalories));
// IntSummaryStatistics{count=9, sum=4300, min=120,average=477.777778, max=800}

//连接字符串 joining 工厂
joining(); joining(", ");
```

![image-20200803234004354](images\summarizingInt---GET方法.png)

#### 6.2 分组

1. 

   ```java
   // 一级分组
   collect(groupingBy(Dish::getCalories))
   // 二级分组
   collect(groupingBy(Dish::getCalories, groupingBy(Dish::getType)))
   // 收集器结果转换  collectiongAndThen (从 Optional 包装类中获取对应值，调用 Optional :: get)
   collect(groupingBy(Dish::getType, collectingAndThen(
   		maxBy(comparingInt(Dish::getCalories)),
   		Optional::get)));
   ```

   ![image-20200804010816008](images\嵌套收集器来获得多重效果.png)

   **图解：**

   - 收集器用虚线表示，因此groupingBy是最外层，根据菜肴的类型把菜单流分组，得到三
     个子流。
   - groupingBy收集器包裹着collectingAndThen收集器，因此分组操作得到的每个子流
     都用这第二个收集器做进一步归约。
   - collectingAndThen收集器又包裹着第三个收集器maxBy。
   - 随后由归约收集器进行子流的归约操作，然后包含它的collectingAndThen收集器会对
     其结果应用Optional:get转换函数。
   - 对三个子流分别执行这一过程并转换而得到的三个值，也就是各个类型中热量最高的Dish，将成为groupingBy收集器返回的Map中与各个分类键（ Dish的类型）相关联的值  

2. groupingBy 联合使用其他收集器

   - 常与 **mapping** 联合使用 

     1. 接收两个参数 一个对流中元素做变换，另一个将变换的元素收集起来

        ```java
        mapping(Disa::getName, toSet());
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
            				.collect(groupingBy(Dish::getType, mapping(dish -> { 
                              if (dish.getCalories() <= 400) return CaloricLevel.DIET;
        					else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
        					else return CaloricLevel.FAT; },
        					toCollection(HashSet::new) )));
        // 指定收集器类型, 声明构造函数
        toCollection(HashSet::new)
        ```

#### 6.3 分区

**分区是分组的特殊情况**、使用函数===>**partitioningBy** 

**备注：** **partitioningBy-----> 返回的是一个boolean值的函数**  

```java
// partitioningBy 可以结合自己实现多级分区，也可以像 groupingBy 一样结合其他收集器使用，
Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
				menu.stream().collect(partitioningBy(Dish::isVegetarian,
				groupingBy(Dish::getType)));
```

![image-20200804014156007](images\Collectors类静态工厂方法1.png)

![image-20200804014059542](images\Collectors类静态工厂方法2.png)

#### 6.4 收集器接口方法 & 自定义收集器

实现 **Collector** 接口中的五个方法:

```java
public interface Collector<T, A, R> {
    Supplier<A> supplier();
    BiConsumer<A, T> accumulator();
    Function<A, R> finisher();
    BinaryOperator<A> combiner();
    Set<Characteristics> characteristics();
}
```

**说明：** 

- T 是流中要收集的对象类型
- A 是累加器的类型，收集对象 T 过程中积累部分结果的对象，也可以是收集对象 T 最终的结果
- R 是收集操作生成的对象类型（不一定是集合）

**方法介绍：**

1. **supplier：** 初始化空的累加器对象 ---- T get();

2. **accumulator:** 将 T 元素添加到结果容器 A ------  accumulator方法会返回执行归约操作的函数  

3. **finisher:** 对结果容器进行最终转换

   ```java
   //累加器对象恰好符合预期的最终结果，因此无需进行转换。所以finisher方法只需返回identity函数：
   public Function<List<T>, List<T>> finisher() {
   	return Function.identity();
   }
   ```

4. **combiner:** 合并结果容器

   1. 定义了各个子流并行处理所得的累加器合并方式
   2. 所有子流都可以并行处理、收集器**combiner**方法返回的函数会把子流产生的结果两两合并，
   3. 原始流拆分得到的子流对应结果合并起来

5. **characteristics：** 返回 一个不可变的 characteristics 集合、定义了收集器行为，哪些可以规约、哪些可以优化提示 **characteristics**包含三个枚举  

   1. **unordered** —— 无序、规约结果不受数据遍历和积累顺序的影响
   2. **concurrent** —— accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。如果收集器没有标为UNORDERED，那它仅在用于无序数据源时才可以并行归约 
   3. **identity_finish** —— 这表明完成器方法返回的函数是一个**恒等函数**，可以跳过。这种情况下，累加器对象将会直接用作归约过程的最终结果。这也意味着，将累加器A不加检查地转换为结果R是安全的。

### 第七章 并行数据处理与性能

#### 7.1 顺序流与并行流之间的相互转换

```java
// sequential 顺序流
// parallel 并行流
stream.parallel().filter(...).sequential()
				.map(...).parallel().reduce();
// 最后调用谁就以谁的方式执行处理流
```

1. 配置并行流使用的线程池

   ```
   并行流内部使用了默认的ForkJoinPool，它默认的线程数量就是你的处理器数量，这个值是由 Runtime.getRuntime().availableProcessors()得到的。
   但是你可以通过系统属性 java.util.concurrent.ForkJoinPool.common.parallelism来改变线程池大小，如下所示：
   System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");
   这是一个全局设置，因此它将影响代码中所有的并行流。反过来说，目前还无法专为某个并行流指定这个值。一般而言，让ForkJoinPool的大小等于处理器数量是个不错的默认值，除非你有很好的理由，否则我们强烈建议你不要修改它
   ```

   **性能优化黄金规则：** 测量，测量，再测量  









### 第十章 Optiona取代 null

#### 1. optiona 来源：

- 函数式语言，比如Haskell、 Scala  中灵感来源
  1. Haskell中 Maybe 类型 --- > 本质上是多 Optiona值的封装
  2. Scala 有类似的数据结构 ---> **Option[T]** 
- **java8** 中汲取 Haskell 与 Scala 语言 引入了  **java.util.Optional<T>** 
- Optional 类方法

| 方法        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| empty       | 返回一个空的Optional 实例                                    |
| filter      | 如果值存在并且满足提供的谓词，就返回包含该值的 Optional 对象；否则返回一个空的Optional 对象 |
| flatMap     | 如果值存在，就对该值执行提供的 mapping 函数调用，返回一个 Optional 类型的值，否则就返回一个空的 Optional 对象 |
| get         | 如果该值存在，将该值用 Optional 封装返回，否则抛出一个 NoSuchElementException异常 |
| ifPresent   | consumer消费、如果值存在就使用该值的方法调用、不存在什么也不做 |
| isPresent   | 判断当前值是否存在 返回 boolean值、 如果值存在就返回 true，否则返回 false |
| map         | 如果值存在，就对该值执行提供的 mapping 函数调用              |
| of          | 将指定值用 Optional 封装之后返回，如果该值为 null，则抛出一个 NullPointerException异常 |
| ofNullable  | 将指定值用 Optional 封装之后返回，如果该值为 null，则返回一个空的 Optional 对象 |
| orElse      | 如果有值则将其返回，否则返回一个默认值                       |
| orElseGet   | 如果有值则将其返回，否则返回一个由指定的 Supplier 接口生成的值 |
| orElseThrow | 如果有值则将其返回，否则抛出一个由指定的 Supplier 接口生成的异常 |

#### 2. Optional 对应基础类型

1. #### OptionalInt 、OptionalLong 、OptionalDouble  

   1. Optional  对象最多只包含一个值、且基础类型不支持 map filter、flatMap这些方法、而这些方法是Optional  类的最有用的、所以不能像 Stream对象一样使用基础类型赛选基础类型数据提高性能

   ```java
   public int readDuration(Properties props, String name) {
       return Optional.ofNullable(props.getProperty(name))
       .flatMap(OptionalUtility::stringToInt)
       .filter(i -> i > 0)
       .orElse(0);
   }
   ```

### 第十一章 CompletableFuture 组合式异步编程

#### 1， Future 接口

- 说明：

```java
ExecutorService executor = Executors.newCachedThreadPool();
Future<Double> future = executor.submit(new Callable<Double>() {
    public Double call() {
        return doSomeLongComputation();	
    }});
doSomethingElse();
try {
	Double result = future.get(1, TimeUnit.SECONDS);
} catch (ExecutionException ee) {
	// 计算抛出一个异常
} catch (InterruptedException ie) {
	// 当前线程在等待过程中被中断
} catch (TimeoutException te) {
	// 在Future对象完成之前超过已过期
}
```

![image-20200816025048177](images\Future接口异步操作流程图.png)

- 说明： 有资源浪费、存在线程阻塞问题 （等待返回结果时间）

#### 2. 使用CompletableFuture 构建异步应用

 [CompletableFuture类方法分类对比](https://www.cnblogs.com/txmfz/p/11266411.html)

```java
 private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });
// t.setDaemon 设置为守护进程、守护进程分两类 1、stand alone（可独立启动、会一直占用系统资源、响应速度快） 2、xinetd （按需分配、需要时启动、不需要时关闭）
public List<String> findPrices(String product) {
    List<CompletableFuture<String>> priceFutures =
            shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
            () -> shop.getPrice(product), executor))
            .map(future -> future.thenApply(Quote::parse))
            .map(future -> future.thenCompose(quote ->
            CompletableFuture.supplyAsync(
            () -> Discount.applyDiscount(quote), executor)))
            .collect(toList());
            return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(toList());
}
// 下方图解
```

![image-20200816025934526](images\completableFuture类异步构建组合任务.png)