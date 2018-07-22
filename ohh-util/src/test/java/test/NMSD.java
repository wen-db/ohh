/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package test;

import java.math.BigDecimal;

/**
 * n个数中，取m个元素，之和为s,允许偏差d
 *
 * @author 文得保 2018/6/5.
 */
public class NMSD {


    public static void main(String[] args) {
        String s = "55703.28,91952.40,82375.28,128691.38,190222.91,284994.47,79756.06,65360.38,71058.67,61761.46,470438.82,39268.21,33270.01,100349.88,101229.62,135799.24,58842.34,124342.68,237318.78,43267.01,274577.60,81555.52,78556.42,65109.46,29671.09,72678.19,160631.79,125942.20,129281.20,95101.46,348275.48,69159.24,342807.12,72478.25,118344.48,52164.34,339798.03,60761.76,175047.47,74257.71,179126.24,41187.64,60236.92,101939.40,56633.00,154453.65,189463.14,48395.47,136539.02,102969.10,50454.85,221383.56,72078.37,51484.55,41187.64,324582.59,64870.53,73757.86,264500.62,51484.55,204908.50,72078.37,73757.86,205938.20,128941.30,78456.45,71048.67,195641.29,134939.50,102969.10,152723.76,175047.47";
        BigDecimal all = new BigDecimal("644306.63");
        BigDecimal deviation = new BigDecimal("300");
        String arr[] = s.split(",");
        BigDecimal[] a = new BigDecimal[arr.length];
        for (int i = 0; i < arr.length; i++) {
            a[i] = new BigDecimal(arr[i]);
        }
        for (int i = 1; i <= a.length; i++) {
            start(a, i, all, deviation);
        }
    }

    /**
     * @param a 源数组
     * @param m 取出m个元素
     * @param s m个元素之和的预期值
     * @param d m个元素之和的预期值允许偏差量
     */
    public static void start(BigDecimal[] a, int m, BigDecimal s, BigDecimal d) {
        int b[] = new int[m];
        for (int i = 0; i < b.length; i++) {
            b[i] = -1;
        }
        match(a, b, 0, s, d);
    }

    public static void match(BigDecimal[] a, int[] b, int aStartIndex, BigDecimal s, BigDecimal d) {
        if (aStartIndex >= a.length) {
            return;
        }
        for (int i = aStartIndex; i < a.length; i++) {
            if (!moveOne(a, i, b)) {
                continue;
            }
            if (b[b.length - 1] != -1) {
                isCompare(a, b, s, d);
                removeLast(b);
            }
        }
        int lastIndex = removeLast(b);
        int index = lastIndex == -1 ? aStartIndex + 1 : lastIndex + 1;
        match(a, b, index, s, d);

    }

    /**
     * 验证是否符合预期
     */
    private static boolean isCompare(BigDecimal[] a, int[] b, BigDecimal s, BigDecimal d) {
        BigDecimal sm = BigDecimal.ZERO;
        for (int aB : b) {
            sm = sm.add(a[aB]);
        }
        if (sm.compareTo(s) == 0) {
            System.out.println("#####");
        }
        int c0 = d.compareTo(BigDecimal.ZERO);
        int c1 = sm.compareTo(s);
        int c2 = sm.subtract(s).abs().compareTo(d.abs());

        if ((c0 >= 0 && c1 >= 0 && c2 <= 0) ||
                (c0 <= 0 && c1 <= 0 && c2 <= 0)) {
            for (int aB : b) {
                System.out.println("a[" + aB + "]=" + a[aB]);
            }
            System.out.println("m=" + b.length + "---以上命中数组与预期值偏差:" + sm.subtract(s));
            return true;
        }
        return false;
    }

    /**
     * 将a数组中坐标为index的元素移动到b数组的末尾
     * <p>
     * <p>
     * <p>
     * 考虑到这个操作a,b数组长度都会变化，而拷贝数组开销巨大所以这里做逻辑移动
     * 即
     * a数组实际长度与内容都固定，b数组长度固定，只将a数组中选中的元素坐标存储在b数组中
     * 而b数组中则用-1表示空
     */
    public static boolean moveOne(BigDecimal[] a, int index, int[] b) {
        for (int aB : b) {
            if (aB == index)
                return false;
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i] == -1) {
                b[i] = index;
                return true;
            }
        }
        b[b.length - 1] = index;
        return true;
    }

    /**
     * 逻辑删除b数组中的最后一个元素
     *
     * @return 返回删除的元素
     */
    public static int removeLast(int[] b) {
        for (int i = b.length - 1; i > 0; i--) {
            if (b[i] != -1) {
                int r = b[i];
                b[i] = -1;
                return r;
            }
        }
        return -1;
    }

}
