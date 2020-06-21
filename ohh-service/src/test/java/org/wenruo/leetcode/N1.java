package org.wenruo.leetcode;

import org.junit.Assert;

import java.util.*;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public class N1 {
    public static void main(String[] args) {
        int[] nums = {2, 3, 11, 15, 7, 8};
        int target = 9;
        check(nums, target, m(nums, target), "m");
        check(nums, target, m1(nums, target), "m1");

    }

    private static void check(int[] nums, int target, int[] res, String m) {
        System.out.println(m + ",res:" + Arrays.toString(res));
        Assert.assertTrue(nums[res[0]] + nums[res[1]] == target);
    }

    private static int[] m(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == j) {
                    continue;
                }
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("m无解");
    }

    private static int[] m1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num1 = nums[i];
            int num2 = target - num1;
            if (map.containsKey(num2)) {
                return new int[]{i, map.get(num2)};
            } else {
                map.put(num1, i);
            }
        }
        throw new RuntimeException("m1无解");

    }
}
