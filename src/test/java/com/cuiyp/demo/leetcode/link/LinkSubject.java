package com.cuiyp.demo.leetcode.link;

import java.util.HashMap;
import java.util.Map;

public class LinkSubject {

    static class Node {
        int value;
        Node next;
        Node rand;
        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 一种特殊的单链表节点类描述如下
     * class Node {
     * int value;
     * Node next;
     * Node rand;
     * Node(int val) { value = val; }
     * }
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
     * 给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
     * 【要求】
     * 时间复杂度O(N)，额外空间复杂度O(1)
     * 实现方式两种：
     * 1. 使用 Map<Node, Node> map = new HahsMap<>(); key 是旧节点， value 是拷贝节点 循环处理旧链表 设置拷贝链表链接关系
     * 2. 在当前链表上操作，每个Node 后边复制插入新的 Node  遍历操作新链表 设置rand指针，spilt 分割链表
     */

    public static Node copyLinkNodeByHashMap(Node head){
        if(head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        while(cur != null){
            map.put(cur, new Node(cur.value));
        }
        //设置 next
        Node copy = map.get(head);
        cur = head;
        while (cur != null){
            Node next = cur.next;
            copy.next = map.get(next);
            copy.rand = map.get(cur.rand);
            cur = next;
        }
        return copy;
    }

    public static Node copyNodeByLink(Node head){
        if(head == null) return null;
        Node cur = head;
        Node next = null;
        //复制节点
        while(head != null){
            next = head.next;
            head.next = new Node(cur.value);
            head.next.next = next;
            cur = next;
        }
        // 处理rand 指针
        cur = head;
        Node copy = null;
        while (cur != null) {
            next = cur.next.next;
            copy = head.next;
            copy.rand = cur.rand.next == null ? null : cur.rand.next;
            cur = next;
        }
        // spilt
        Node ret = head.next;
        cur = head;

        while (cur != null) {
            next = head.next.next;
            copy = cur.next;
            cur.next = next;
            copy.next = next.next != null ? next.next : null;
            cur = next;
        }
        return ret;
    }

    /**
     * 给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
     * 【要求】
     * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
     *
     * 实现方式：
     * 1.
     */


    /**
     * 链表问题
     *
     * 1. 单链表环问题
     *    - HashSet 遍历添加，先查找比较，在添加节点
     *    - 快慢指针
     *      1. 第一次相遇，快指针回到头部
     *      2. 双指针同步运行，再次相遇必定是第一个入环节点
     */


    /**
     * 2. 两个单链表相交 问题
     *    1. HashSet
     *       - 先把一个链表添加到 hashSet中 在查询添加另一个链表节点
     *    2. 遍历两个链表，找到 end节点 并记录两个链表长度的差值
     *      end节点相同 才会有相交节点
     *      在遍历两个链表 长链表先行 差值的长度
     *      开始比较两链表 节点值，第一个相等的 就是相交点
     *   3. 两个链表都有环
     */


    /**
     * 链表删除节点  只给一个删除节点
     * 1. 借尸还魂 复制下一个节点的值，拷贝到当前节点，干掉下一个节点。我还是我，只是干掉了下一个而已
     *      问题 1.最后一个节点删不掉 （引用类型 内存模型问题）
     *          2.把别人干掉了，可是自己还是存在的。
     */
}
