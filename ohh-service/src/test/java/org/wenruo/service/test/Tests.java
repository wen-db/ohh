package org.wenruo.service.test;

import org.junit.Test;
import org.wenruo.ohh.service.ioc.A;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * @author wendebao
 * @Date 2020/3/27
 **/
public class Tests {
    @Test
    public void t() throws IOException {
        String mapperLocations = "";
        System.out.println(100690 % 520);
    }

    @Test
    public void queq() {
        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(1);
        IntStream.range(0, 10).forEach(i -> {
            abq.offer("abq" + i);
        });
        System.out.println("ArrayBlockingQueue:" + abq.size());
        LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();
        IntStream.range(0, 10).forEach(i -> {
            lbq.offer("abq" + i);
        });
        System.out.println("LinkedBlockingQueue:" + lbq.size());
    }

    @Test
    public void minMoves() {
        int[] nums = {1, 2,4};
        Arrays.sort(nums);
        int len = nums.length;
        int M1 = nums[len / 2];
        int M2 = nums[len / 2 + 1];
        int ans = Integer.MAX_VALUE;
        for (int m = M1; m <= M2; m++) {
            int tmp = 0;
            for (int i = 0; i < len; i++) {
                tmp = tmp + Math.abs(nums[i] - m);
            }
            if (ans > tmp)
                ans = tmp;
        }
        System.out.println(ans);
    }
}
