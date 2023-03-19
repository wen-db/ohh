package org.wenruo.qb;

import java.lang.reflect.Array;
import java.util.*;

public class Answer {
    /**
     * 正确答案集
     * key=答案，value=分数
     */
    private Map<String, Integer> correctData = new HashMap<>();
    private List<String> userData = new ArrayList<>();

    public Answer() {
    }

    public Answer(String data) {
        correctData.put(data, 1);
    }


    public void answer(String a) {
        userData.add(a);
    }

    public int check() {
        int sum = 0;
        for (String key : userData) {
            Integer score = correctData.get(key);
            if (score != null) {
                sum += score;
            }
        }
        return sum;
    }


    public String successData() {
        return Arrays.toString(correctData.keySet().toArray());
    }

    public String youData() {
        return Arrays.toString(userData.toArray());

    }
}
