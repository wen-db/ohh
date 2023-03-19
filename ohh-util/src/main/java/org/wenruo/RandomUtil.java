package org.wenruo;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class RandomUtil {

    /**
     * 平均洗牌算法，生成一个[0~size)的完全随机数组
     *
     * @param size
     * @return
     */
    public static int[] randomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int r = random.nextInt(size - i) + i;
            if (i == r) {
                continue;
            }
            int tmp = array[r];
            array[r] = array[i];
            array[i] = tmp;
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(randomArray(5)));
        DecimalFormat df = new DecimalFormat("0.00%");
        System.out.println((double) 1/ 3);
        System.out.println(String.format("正确率%s", df.format((double) 1/ 5)));
    }
}
