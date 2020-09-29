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
        return "给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。\n" +
                "\n" +
                "如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。\n" +
                "\n" +
                "您可以假设除了数字 0 之外，这两个数都不会以 0 开头。\n" +
                "\n" +
                "示例：\n" +
                "\n" +
                "输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)\n" +
                "输出：7 -> 0 -> 8\n" +
                "原因：342 + 465 = 807\n" +
                "\n" +
                "来源：力扣（LeetCode）\n" +
                "链接：https://leetcode-cn.com/problems/add-two-numbers\n" +
                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。";
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
    Args[] buildArgs() {
        return new Args[]{random(), random(), random()};
    }

    private int[] randomArg() {
        int[] arg = new int[new Random().nextInt(5) + 3];
        for (int i = 0; i < arg.length; i++) {
            arg[i] = new Random().nextInt(8) + 1;
        }
        return arg;
    }

    private Args random() {
        int[] r1 = randomArg();
        int[] r2 = randomArg();
        int[] res = longToArray(arrayToInt(r1) + arrayToInt(r2));
        return new Args(new Arg(r1, r2), new Res(res));
    }

    private static long arrayToInt(int[] num) {
        StringBuilder s = new StringBuilder();
        for (int i = num.length - 1; i >= 0; i--) {
            s.append(num[i]);
        }
        return Long.parseLong(s.toString());
    }

    private static int[] longToArray(long l) {
        String str = String.valueOf(l);
        int length = str.length();
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            int tmp = str.charAt(i) - '0';
            arr[length - i-1] = tmp;
        }
        return arr;
    }
}
