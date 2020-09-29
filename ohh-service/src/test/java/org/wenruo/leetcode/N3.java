package org.wenruo.leetcode;

import java.util.*;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public class N3 extends LeetCode {
    @Override
    String name() {
        return "无重复的最长子串";
    }

    @Override
    String methodRemark() {
        return null;
    }

    @Override
    String resRemark() {
        return "滑动窗口";
    }

    @Override
    Object[] method(Object... args) {
        String s = (String) args[0];
        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int max = 1;
        for (int i = 0; i < s.length(); i++) {
            //当前字符
            Character c = s.charAt(i);
            if (set.contains(c)) {
                max = Math.max(max, set.size());
                set.remove(s.charAt(left));
                left++;
            } else {
                set.add(c);
            }
        }
        return new Object[]{max};
    }


    @Override
    Args[] buildArgs() {
        return new Args[]{
                new Args(new Arg("abcabcbb"), new Res(3)),
                new Args(new Arg("bbbbb"), new Res(1)),
                new Args(new Arg("pwwkew"), new Res(3)),
        };
    }


}
