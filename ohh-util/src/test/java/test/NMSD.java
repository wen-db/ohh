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

    private static String[] arr;

    public static void main(String[] args) {
        String s1 = "6085980|89953.00,6086058|34469.65,6088380|83954.80,6088627|113825.84,6088275|93951.80,6088632|85234.42,6088199|47865.63,6088783|61841.44,6088419|54443.66,6088742|53463.95";
        String s2 = "6088420|34369.68,6090909|52164.34,6091264|67919.61,6091611|55563.32,6091231|94351.68,6091344|73557.92,6091630|61561.52,6091246|362471.22,6093925|78556.42,6094097|56513.04,6094048|120143.94,6094240|70158.94,6094427|131740.46,6094504|151794.44,6094273|46709.98,6097143|126941.90,6097164|178926.30,6097100|29571.12,6097099|35569.32,6097153|27371.78,6097001|180725.76,6097169|36968.90,6097160|53347.99,6096926|66560.02,6097101|82155.34,6096946|60761.76,6097022|112006.38,6097159|34319.70,6096996|41087.67,6096962|78612.40,6097089|107247.81,6097174|302489.22,6097244|74267.71,6100061|145736.26,6099904|84334.69,6100070|96990.89,6100072|105948.20,6100074|50494.84,6100063|92452.25,6102301|83694.88,6104153|64160.74,6103959|42567.22,6104245|69219.22,6104238|69259.21,6104260|68919.31,6104122|71158.64,6104233|84754.56,6104274|92312.29";
        String s3 = "6104278|41767.46,6106412|60361.88,6106483|151794.44,6106129|29291.21,6106128|47165.84,6106474|128941.30,6106502|125342.38,6104520|54943.51,6106392|67779.66,6106484|73557.92,6106377|51564.52,6106131|52764.16,6106342|67559.72,6106500|47965.60,6106509|54160.74,6106550|488033.54,6108538|145236.41,6106728|39068.27,6109022|59762.06,6108983|52764.16,6109043|134439.65,6109041|409657.06,6106763|47725.67,6108990|28971.30,6108882|73817.84,6108915|104448.65,6108881|97950.60,6106828|73357.98,6108809|114820.54,6108824|108347.48,6108988|551014.64,6108933|31170.64,6108967|58922.31,6108944|38028.58,6109068|133939.80,6142330|85954.20,6141984|91872.43,6145828|77056.87,6145890|112466.25,6149251|44706.58,6151444|46346.09";
        String s = s1 + "," + s2 + "," + s3 ;
        BigDecimal all = new BigDecimal("5704305.72");
        BigDecimal deviation = new BigDecimal("100");
        arr = s.split(",");
        BigDecimal[] a = new BigDecimal[arr.length];
        for (int i = 0; i < arr.length; i++) {
            try {
                a[i] = new BigDecimal(arr[i].split("\\|")[1]);
            } catch (Exception e) {
                System.out.println(arr[i]);
            }
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
                //  System.out.println("a[" + aB + "]=" + a[aB]);
                System.out.print(arr[aB].split("\\|")[0] + ",");
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
