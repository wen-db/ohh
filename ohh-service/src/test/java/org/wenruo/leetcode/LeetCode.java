package org.wenruo.leetcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public abstract class LeetCode {
    abstract String name();

    abstract String methodRemark();

    abstract String resRemark();

    abstract Object[] method(Object... args);

    abstract Object[][] buildArgs();

    abstract boolean check(Object[] res, Object[] args);

    void invoke() {
        Object[][] args = buildArgs();
        for (Object[] arg : args) {
            long start = System.currentTimeMillis();
            Object[] res = method(arg);
            long end = System.currentTimeMillis();
            System.out.println("算法名：" + name());
            System.out.println("耗时：" + (end - start) + " ms");
            System.out.println("方法参数：" + gson.toJson(arg));
            System.out.println("运行结果：" + gson.toJson(res));
            boolean check = check(res, arg);
            System.out.println("结果验证：" + check);
            System.out.println("-------------------------");
            System.out.println("题目简介：" + methodRemark());
            System.out.println("-------------------------");
            System.out.println("算法思想：" + resRemark());
            Assert.assertTrue(check);
            System.out.println();
            System.out.println();
        }
    }

    Gson gson = new GsonBuilder().create();

    @Test
    public void t() {
        invoke();
    }
}
