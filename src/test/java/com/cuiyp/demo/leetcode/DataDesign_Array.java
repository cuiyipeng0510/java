package com.cuiyp.demo.leetcode;

/**
 * 总纲:
 * 1. | 或运算 有1 为1 没有为 0、
 *      0001 1101
 *      1010 1011
 *      0001 1101 | 1011 0011 = 1011 1111
 * 2. & 与运算 相同不变，不同取 0
 *      0001 1101 & 1011 0011 = 0000 1001
 * 3. ^ 异或运算 无进位相加
 *      0001 1101 ^ 1011 0011 = 1011 0110
 *
 *      1011 0110
 *      1010 1011
 *      0000 0101
 */
public class DataDesign_Array {

    public static void main(String[] args) {

    }

    // 一个数组中有两种数出现了奇数次、其他数都出现了偶数次、怎么找到这两种数并打印
    /**
     * 1. 异或取值，a^a = 0 可以去除这两个数
     *
     */
    /**
     * 1、 ^ 运算  N ^ N = 0;  N ^ 0 = N
     * 2、 找出一个数中最右边的1
     * 3、& 与运算 都是 1 等于 1 不同等于0
     * 4、 N & ((~N)+1)
     * <p>
     * 思路：
     * 1、偶数次的数进行 异或运算结果为 0 无论顺序
     * 2、所以最后异或的结果肯定是 出现了奇数次的两种数 假设 a ^ b
     * 3、找出 a ^ b 结果中 最右边是1的数 c
     * 1、a或b 肯定有一个数 最右边的这个位置上是1 其他数肯定都是0
     * 2、
     */

    public static void testMethord(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int eor = arr[0];
        for (int i = 0; i < arr.length; i++) {
            eor = eor ^ arr[i];
        }

    }


    /**
     * 对数器
     * 1. Math.random() [0, 1) 所有小数
     * 2. Math.random() * N [0, N) 所有小数
     * 3. (int)(Math.random() * N) [0, N-1] 所有正数
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] array = new int[(int) (Math.random() * (maxSize + 1))];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return array;
    }
}
