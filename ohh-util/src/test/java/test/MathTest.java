/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package test;

/**
 * @author 文得保 2018/7/17.
 */
public class MathTest {
    /**
     * 寻找小于目标的最近的回文数字
     *
     * @param n 源数
     * @return 目标数
     */
    public String nearestPalindromic(String n) {
        char[] arr = n.toCharArray();
        int center = arr.length / 2;//中间位
        int rem = arr.length % 2 > 0 ? 1 : 0;//余数
        for (int i = center; i > 0; i--) {//向左验证
            int c = arr[i - 1] - '0';//转成数字
            if (c > i){

            }
        }
        return null;
    }
}
