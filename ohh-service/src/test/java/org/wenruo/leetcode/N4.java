package org.wenruo.leetcode;

/**
 * @author wendebao
 * @Date 2020/6/12
 **/
public class N4 extends LeetCode {
    @Override
    String name() {
        return null;
    }

    @Override
    String methodRemark() {
        return null;
    }

    @Override
    String resRemark() {
        return null;
    }

    @Override
    Object[] method(Object... args) {
        int[] num1 = (int[]) args[0];
        int[] num2 = (int[]) args[1];
        int count = num1.length + num2.length;
        int middle = count / 2;
        int[] target;
        if (count % 2 == 0) {
            target = new int[]{middle - 1, middle};
        } else {
            target = new int[]{middle, middle};
        }
        int lcount = 0;
        int rcount = 0;
        int lmax = Math.min(num1[num1.length - 1], num2[num2.length - 1]);
        int rmint = Math.min(num1[0], num2[0]);
        for (int i = 0; i < num1.length; i++) {
         //   if (num1[i])
        }
        return new Object[0];
    }

    @Override
    Args[] buildArgs() {
        return new Args[0];
    }

}
