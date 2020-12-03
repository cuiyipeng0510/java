package com.cuiyp.demo.leetcode.queue;

import com.cuiyp.demo.leetcode.queue.catordog.Cat;
import com.cuiyp.demo.leetcode.queue.catordog.Dog;
import com.cuiyp.demo.leetcode.queue.catordog.Pet;

import java.util.Stack;

public class CatAndDogQueue<T> {
    private Stack<Dog> dogStack;
    private Stack<Cat> catStack;

    public CatAndDogQueue() {
        this.dogStack = new Stack<>();
        this.catStack = new Stack<>();
    }

    public void add(Pet pet) {
        if (pet instanceof Dog) {
            dogStack.push((Dog) pet);
        }
        if (pet instanceof Cat) {
            catStack.push((Cat) pet);
        }
    }

    public Pet pollAll() {
        if (dogStack.isEmpty() && catStack.isEmpty()) {
            throw new RuntimeException("没宠物了");
        }
        if (!catStack.isEmpty() && !dogStack.isEmpty()) {
            if (dogStack.peek().getTimeStamp() > catStack.peek().getTimeStamp()) {
                return catStack.pop();
            } else {
                return dogStack.pop();
            }
        }
        if (dogStack.isEmpty()) {
            return catStack.pop();
        } else {
            return dogStack.pop();
        }
    }

    public Dog pollDog() {
        if (!dogStack.isEmpty()) {
            return dogStack.pop();
        }
        throw new RuntimeException("没狗了");
    }

    public Cat pollCat() {
        if (!catStack.isEmpty()) {
            return catStack.pop();
        }
        throw new RuntimeException("没猫了");
    }


    public boolean isEmpty() {
        return dogStack.isEmpty() && catStack.isEmpty();
    }

    public boolean isDogEmpty() {
        return dogStack.isEmpty();
    }

    public boolean isCatEmpty() {
        return catStack.isEmpty();
    }

}
