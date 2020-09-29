package org.wenruo.leetcode;

/**
 * @author wendebao
 * @Date 2020/6/25
 **/
public class N5 extends LeetCode {
    @Override
    String name() {
        return "最长回文";
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
        String s = (String) args[0];
        int start = 0;
        int end = 1;
        for (int i = 0; i < s.length(); i++) {
            if (hw(s, start, end)) {
                end++;
            } else {
                start++;
            }
        }
        String hw = s.substring(start, end);
        System.out.println(hw);
        return new Object[hw.length()];
    }

    @Override
    Args[] buildArgs() {
        return new Args[0];
    }

    private boolean hw(String s, int start, int end) {
        for (int i = start; i < (end - start) / 2; i++) {
            if (s.charAt(i) != s.charAt(end - i)) {
                return false;
            }
        }
        return true;
    }

}
