package com.cuiyp.demo.leetcode;

public class DataDesign_Link {
    public static void main(String[] args) {

    }

    public static Node reverseNode(Node head){
        Node pre = null;
        Node next = null;
        while(head != null){
            // 抓一下head的 next
            next = head.next;
            // 修改head 指针
            head.next = pre;
            // pre 要抓取当前节点 以便下一次循环处理
            pre = head;
            // head 移动到下一个节点
            head = next;
        }
        return pre;
    }

    public static DoubleNode reverseDoubleNode(DoubleNode head){
        DoubleNode pre = null;
        DoubleNode next = null;
        //没次处理 都当做头节点了对待
        while (head != null){
            //抓取 head 下一个节点
            next = head.next;
            // 修改当前节点 next 指针
            head.next = pre;
            // 修改当前节点 pre 指针
            head.pre = next;
            pre = head;
            // 指定下一个遍历的头节点
            head = next;
        }
        return pre;
    }

    //删除链表中指定值
    public static Node removeNode(Node head, int value){
        while(head != null){
            if(head.value == value){
                 head = head.next;
            }else {
                break;
            }
        }
        Node pre = null;
        Node cur = head;
        while (cur != null){
            if (cur.value != value){
                pre = cur;
            }else {
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * 链表指定一个头部即可
     * 链表翻转，需要返回新的头部
     */

    //1. 单链表翻转
    public class Node {
        private int value;
        private Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    //双向链表
    public class DoubleNode {
        private int value;
        private DoubleNode pre;
        private DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }
}
