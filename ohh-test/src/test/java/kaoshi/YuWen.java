package kaoshi;

import org.springframework.util.ResourceUtils;
import org.wenruo.qb.Answer;
import org.wenruo.qb.QuestionBank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class YuWen {

    private static List<QuestionBank> questionBanks = new ArrayList<>();

    static {
        //1、涂有饿莩而不知发 《寡人之于国也》中，揭露当时社会贫富悬殊的句子是涂有饿莩而不知发。- 单选
        questionBanks.add(new QuestionBank("《寡人之于国也》中，揭露当时社会贫富悬殊的句子是", new Answer("涂有饿莩而不知发")));
        // 2、“操舟” 《论毅力》中，作者阐述人生的逆顺两境可以转化、贵在坚持所使用的论据是 “操舟”。-单选
        questionBanks.add(new QuestionBank("《论毅力》中，作者阐述人生的逆顺两境可以转化、贵在坚持所使用的论据是", new Answer("操舟")));
        // 3、刺猬好吃油虫 《如何避免愚蠢的见识》中，属于作者早年个人偏见的是，刺猬好吃油虫。-单选
        questionBanks.add(new QuestionBank("《如何避免愚蠢的见识》中，属于作者早年个人偏见的是", new Answer("刺猬好吃油虫")));
        // 4、秦晋争夺中原霸权 《秦晋崤之战》中，崤之战爆发的根本原因是秦晋争夺中原霸权。-单选
        questionBanks.add(new QuestionBank("《秦晋崤之战》中，崤之战爆发的根本原因是", new Answer("秦晋争夺中原霸权")));
        // 5、为孟尝君赢得薛地民心 《冯谖客孟尝君》中，孟尝君令冯谖到薛地收债，而他却焚券而归，其目的是 为孟尝君赢得薛地民心。-单选
        questionBanks.add(new QuestionBank("《《冯谖客孟尝君》中，孟尝君令冯谖到薛地收债，而他却焚券而归，其目的是", new Answer("为孟尝君赢得薛地民心")));

    }

    public static void main(String[] args) throws Exception {
        System.out.println("1、涂有饿莩而不知发《寡人之于国也》中，揭露当时社会贫富悬殊的句子是涂有饿莩而不知发。-单选".matches("\\d.*"));
        // QMain.execute(questionBanks);
        String path = "04729《大学语文》.txt";
        System.out.println("开始加载题库");
         List<QuestionBank> list = init(path);
         System.out.println(String.format("题库加载完成共%d道题", list.size()));


    }


    private static List<QuestionBank> init(String path) throws Exception {
        File file = ResourceUtils.getFile("classpath:" + path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<QuestionBank> list =new ArrayList<>();
        while (true){
            QuestionBank q = analysisOne(bufferedReader);
            if (q==null){
                break;
            }
            list.add(q);
        }
        return list;
    }

    private static QuestionBank analysisOne(BufferedReader bufferedReade) throws Exception {
        String line;
        while (true) {
            String readLine = bufferedReade.readLine();
            if (readLine == null) {
                return null;
            }
            readLine = readLine.replaceAll("\\s*", "");
            if (!check(readLine)) {
                continue;
            }
            line = readLine;
            break;
        }
        System.out.println(line);
        int index =line.lastIndexOf("-");
        String qType= line.substring(index+1);
        System.out.println(qType);
        //todo
        return new QuestionBank();

    }

    private static boolean check(String s) {
        if (s.equals("")) {
            return false;
        }
        if (s.equals("成人达己追求卓越")) {
            return false;
        }
        return s.matches("[1-9].*");
    }
}
