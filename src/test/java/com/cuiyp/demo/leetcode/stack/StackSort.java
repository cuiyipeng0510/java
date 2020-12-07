package com.cuiyp.demo.leetcode.stack;

import com.cuiyp.demo.leetcode.utils.ArrayUtils;

import java.util.Stack;

public class StackSort {
    /**
     * 1. 用一个栈实现另一个栈的排序 --- 栈底到栈顶，递增
     * 2. 一个stack 一个 help
     *   1.pop stack中元素cur 与 help顶元素比较，
     *   cur <= help.peek() hlep.push(cur)
     *   cur > help.peek() stack.push(help.pop())
     */

    private static final Stack<Integer> stack = new Stack<>();
    private static final Stack<Integer> help = new Stack<>();

    static{
        int[] ints = ArrayUtils.generateRandomArray(10, 10);
        for (int numb: ints) {
            stack.push(numb);
        }
    }

    public static void sortStack() {
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            while (!help.isEmpty() && pop > help.peek()) {
                stack.push(help.pop());
            }
            help.push(pop);
        }
        while(!help.isEmpty()){
            stack.push(help.pop());
        }
    }
}
