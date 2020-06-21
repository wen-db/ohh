package org.wenruo.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public class N2 extends LeetCode {
    @Override
    String name() {
        return "两数相加";
    }

    @Override
    String methodRemark() {
        return "";
    }

    @Override
    String resRemark() {
        return null;
    }

    @Override
    Object[] method(Object... args) {
        int[] num1 = (int[]) args[0];
        int[] num2 = (int[]) args[1];
        int size = Math.max(num1.length, num2.length);
        List<Integer> res = new ArrayList<>();
        int jin = 0;
        for (int i = 0; i < size; i++) {
            int t = jin + (num1.length > i ? num1[i] : 0);
            t = t + (num2.length > i ? num2[i] : 0);
            res.add(t % 10);
            jin = t / 10;
        }
        if (jin > 0) {
            res.add(jin);
        }
        int[] array = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            array[i] = res.get(i);
        }

        return new Object[]{array};
    }

    @Override
    Object[][] buildArgs() {
        return new Object[][]{new Object[]{randomArg(), randomArg()},new Object[]{randomArg(), randomArg()}};
    }

    private int[] randomArg() {
        int[] arg = new int[new Random().nextInt(5) + 3];
        for (int i = 0; i < arg.length; i++) {
            arg[i] = new Random().nextInt(8) + 1;
        }
        return arg;
    }

    @Override
    boolean check(Object[] res, Object... args) {
        int[] num1 = (int[]) args[0];
        int[] num2 = (int[]) args[1];
        int[] resArray = (int[]) res[0];
        long n1 = arrayToInt(num1);
        long n2 = arrayToInt(num2);
        long r = arrayToInt(resArray);
        return n1 + n2 == r;
    }

    private static long arrayToInt(int[] num) {
        StringBuilder s = new StringBuilder();
        for (int i = num.length - 1; i >= 0; i--) {
            s.append(num[i]);
        }
        return Long.parseLong(s.toString());
    }

}
