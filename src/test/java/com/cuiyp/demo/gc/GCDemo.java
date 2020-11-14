package com.cuiyp.demo.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * 1. java -XX: +PrintCommandLineFlags GCDemo
 * 2. java -Xmn10M -Xms10M -Xmx10M -XX: +PrintCommandLineFlags -XX:PrintGC GCDemo
 * 3. java -XX:+UseConcMarkSweepGC -XX:+PrintCommandLineFlags GCDemo
 * 4. java -XX:+PrintFlagsInitial 默认参数值
 * 5. java -XX:+PrintFlagsFinal -version | grep GC
 * 定位垃圾
 * 1. 引用计数（ReferenceCount）
 * 2. 根可达算法（RootSearching）
 * 垃圾回收算法
 * 1. 标记清除（mark sweep）-- 位置不连续 产生碎片 效率偏低（两边扫描）
 * 2. 拷贝算法（copying）没有碎片，效率高 浪费空间
 * 3. 标记压缩 （mark compact）- 没有碎片 效率偏低（扫描两边，指针需要调整）
 * 常见垃圾回收器
 * STW --- Stop The World
 * Serial: copying coollect single GC thread
 * Serial Old:
 * Paraller Scavenge: multiple GC threads
 * Paraller Old: a compacting collector that uses multiple GC threads
 * ParNew: it differs from PS in that it enhancements that make it usable with CMS
 * CMS(concurrent mark sweep):
 *      1. inital mark
 *      2. concurrent mark
 *      3. remark
 *      4. concurrent sweep
 * G1
 *
 * JDK 1.8 default used PS + PO
 * PN + CMS + Serial Old
 * e
 * -XX:+UseConcMarkSweepGC  -> ParNew + CMS + sERIAL Old
 * -XX:+UseParallelGC ->  Parrallel Scavenge + Parallel Old
 * -XX:+UseParallelOldGC -> Parallel Scavenge + Parallel Old
 * -XX:+UseG1GC ->  G1
 * 永久代（以下为 1.7 1.8 名称）
 *  Perm Generation 启动可以指定大小（不会变） 有限制可能会出现内存溢出 1.7 字符串常量存于 永久代
 *  Metaspace （受限于物理内存）1.8 - 字符串常量存于堆
 *
 * 垃圾收集器跟内存大小有关
 * 1. Serial 几十兆
 * 2. PS 上百兆 - 几个G
 * 3. CMS - 20G
 * 4. G1 - 上百G
 * 5. ZGC - 4T - 16T（JDK13）
 */
public class GCDemo {
    public static void main(String[] args) {
        System.out.println("GC");
        List list = new LinkedList<>();
        for(;;){
            byte[] b = new byte[1024*1024];
            list.add(b);
        }
    }
}
