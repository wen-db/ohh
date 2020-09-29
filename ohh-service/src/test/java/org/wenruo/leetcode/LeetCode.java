package org.wenruo.leetcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * @author wendebao
 * @Date 2020/6/11
 **/
public abstract class LeetCode {
    abstract String name();

    abstract String methodRemark();

    abstract String resRemark();

    abstract Object[] method(Object... args);

    abstract Args[] buildArgs();


    void invoke() {
        Args[] args = buildArgs();
        System.out.println("算法名：" + name());
        System.out.println("-------------------------");
        System.out.println("题目简介：" + methodRemark());
        System.out.println("-------------------------");
        System.out.println("算法思想：" + resRemark());
        for (int i = 0; i < args.length; i++) {
            Object[] arg = args[i].getArgs().getArg();
            Object[] targetResult = args[i].getRes().getRes();
            long start = System.currentTimeMillis();
            Object[] res = method(arg);
            long end = System.currentTimeMillis();
            System.out.println("第" + i + "组测试参数：" + gson.toJson(arg));
            System.out.print("运行结果：" + gson.toJson(res));
            System.out.print(" 目标结果：" + gson.toJson(targetResult));
            System.out.println(" 耗时：" + (end - start) + " ms");
            if (targetResult == null) {
                System.err.println("结果验证：未设置目标值");
            }
            if (gson.toJson(res).equals(gson.toJson(targetResult))) {
                System.out.println("结果验证：" + true);
            } else {
                System.err.println("结果验证：" + false);
            }
            System.out.println("**************************************");
        }
    }

    Gson gson = new GsonBuilder().create();


    @Test
    public void t() {
        invoke();
    }

    @Getter
    @Setter
    class Args {
        private Arg args;
        private Res res;

        public Args(Arg args, Res res) {
            this.args = args;
            this.res = res;
        }
    }

    @Getter
    class Arg {
        private Object[] arg;

        public Arg(Object... arg) {
            this.arg = arg;
        }
    }

    @Getter
    class Res {
        private Object[] res;

        public Res(Object... res) {
            this.res = res;
        }
    }
}
