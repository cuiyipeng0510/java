package com.cuiyp.demo.leetcode.stack;

import java.util.Stack;

public class GetMinStack {
    public class MyStack{
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MyStack(){
            this.dataStack = new Stack<Integer>();
            this.minStack = new Stack<Integer>();
        }

        private void push(int value){
            if(this.minStack.isEmpty()){
                this.minStack.push(value);
            }else if(this.getMin() < value) {
                int min = this.minStack.peek();
                this.minStack.push(min);
            }else {
                this.minStack.push(value);
            }
            this.dataStack.push(value);
        }

        private Integer pop(){
            this.minStack.pop();
            return this.dataStack.pop();
        }

        private Integer getMin(){
            if(this.minStack.isEmpty()) throw new RuntimeException();
            return this.minStack.peek();
        }
    }
}
