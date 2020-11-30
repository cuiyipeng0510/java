package com.cuiyp.demo.leetcode.queue;

import com.cuiyp.demo.leetcode.queue.catordog.Cat;
import com.cuiyp.demo.leetcode.queue.catordog.Dog;
import com.cuiyp.demo.leetcode.queue.catordog.Pet;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Stack;

public class CatAndDogQueue<T> {
    private Stack<T> stack;
    private volatile int catSize;
    private volatile int dogSize;
    public CatAndDogQueue(){
        this.stack = new Stack<T>();
    }

    public void add(Pet pet){
        if(pet instanceof Dog){
            dogSize ++;
        }
        if(pet instanceof Cat){
            catSize ++;
        }
        stack.push((T) pet);
    }

    public List<T> pollAll() {
        List<T> all = Lists.newArrayList();
        while (!stack.isEmpty()) {
            all.add(stack.pop());
        }
        return all;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public boolean isDogEmpty(){
        return dogSize == 0;
    }

    public boolean isCatEmpty(){
        return catSize == 0;
    }

}
