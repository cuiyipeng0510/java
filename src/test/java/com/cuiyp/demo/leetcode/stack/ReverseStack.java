package com.cuiyp.demo.leetcode.stack;

import java.util.Stack;

public class ReverseStack {

    /**
     *
     * @param stack
     * @return
     */
    public static int getAndRemoveLastElement(Stack<Integer> stack){
        int result = stack.pop();
        if(stack.isEmpty()){
            return result;
        }else {
            // 最后一个不压栈 返回
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            //移除并返回当前栈底元素
            return last;
        }
    }
    /**
     * 使用递归与栈操作，逆序一个栈
     * 1. 需要两个递归
     *    1. 清空栈
     *    2. 反向压栈
     *
     */
    public static void rs(Stack<Integer> stack){
        if(stack.isEmpty()) return;
        int pop = getAndRemoveLastElement(stack);
        rs(stack);
        stack.push(pop);
    }

    public static Stack<Integer> generateRandomStack(int maxSize, int value) {
        Stack<Integer> randomStack = new Stack<>();
        if (randomStack.size() != maxSize) {
            int numb = (int) (Math.random() * (value + 1)) - (int) (Math.random() * value);
            randomStack.push(numb);
        }
        return randomStack;
    }

    public static void printStack(Stack<Integer> stack){
        while(!stack.isEmpty()){
            System.out.println(stack.peek());
        }
    }
}
