package org.wenruo.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public class N1 extends LeetCode {


    @Override
    String name() {
        return "两个数之和";
    }

    @Override
    String methodRemark() {
        return "给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。\n" +
                "\n" +
                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。\n" +
                "\n" +
                " \n" +
                "\n" +
                "示例:\n" +
                "\n" +
                "给定 nums = [2, 7, 11, 15], target = 9\n" +
                "\n" +
                "因为 nums[0] + nums[1] = 2 + 7 = 9\n" +
                "所以返回 [0, 1]\n" +
                "\n" +
                "来源：力扣（LeetCode）\n" +
                "链接：https://leetcode-cn.com/problems/two-sum\n" +
                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。";
    }

    @Override
    String resRemark() {
        return null;
    }

    @Override
    Object[] method(Object... args) {
        int[] nums = (int[]) args[0];
        int target = (int) args[1];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num1 = nums[i];
            int num2 = target - num1;
            if (map.containsKey(num2)) {
                int[] array = new int[]{map.get(num2), i};
                return new Object[]{array};
            } else {
                map.put(num1, i);
            }
        }
        throw new RuntimeException("m1无解");

    }

    @Override
    Args[] buildArgs() {
        int[] nums = {2, 3, 11, 15, 7, 8};
        int target = 9;
        int[] res = {0, 4};
        return new Args[]{new Args(new Arg(nums, target), new Res(res))};
    }
}
