package com.cuiyp.demo;

public class CountTest {
    private static volatile int ccc = 0;
    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        System.out.println(a++);
        System.out.println(++b);
    }
}
