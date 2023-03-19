package org.wenruo.qb;

import org.wenruo.RandomUtil;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class QMain {
    public static void execute(List<QuestionBank> questionBanks) {
        Scanner scanner = new Scanner(System.in);
        int size = questionBanks.size();
        int[] randomArr = RandomUtil.randomArray(size);
        QuestionBank[] t = new QuestionBank[size];
        for (int i = 0; i < randomArr.length; i++) {
            System.out.println("第" + (i + 1) + "题，共" + size + "题");
            QuestionBank qb = questionBanks.get(randomArr[i]);
            System.out.println(qb.getQuestion());
            System.out.print("答案：");
            String redLine = scanner.nextLine();
            qb.getAnswer().answer(redLine);
            t[i] = qb;
        }
        System.out.println("全部作答完毕");
        int success = 0;
        int sumScore = 0;
        int fail = 0;
        for (QuestionBank qb : t) {
            Answer answer = qb.getAnswer();
            int score = answer.check();
            if (score > 0) {
                success++;
                sumScore += score;
            } else {
                System.out.println(String.format("错题%d 题目:%s,%n正确答案:%s%n你的答案:%s", ++fail, qb.getQuestion(), qb.getAnswer().successData(), qb.getAnswer().youData()));
            }
        }
        DecimalFormat df = new DecimalFormat("0%");
        System.out.println(String.format("共得分%d,正确率%s", sumScore, df.format((double) success / size)));
    }
}
