package github.pair.generator;

import github.pair.generator.bean.tree.ExpressionTree;
import java.io.*;
import java.util.*;

/**
 * 程序入口
 */
public class AppEntry {

    private static final String SAVE_PATH = new File("").getAbsolutePath()
                + File.separator + "test-result" + File.separator;
    private static final Random RANDOM = new Random();


    public static void main( String[] args ) throws IOException {
        if (args[0].equals("-r") && args[2].equals("-n")){
            int bound = Integer.parseInt(args[3]);
            int range = Integer.parseInt(args[1]);
            generate(bound, range);
        }
        else if (args[0].equals("-e") && args[2].equals("-a")){// 比对答案
            compareAnswer(args[1], args[3]);
        }else {
            System.out.println(printUsage());
        }
    }

    /**
     * 比对用户在路径为answerFiLeStr的文件中输入的对应题号题目的答案与正确的答案
     * @param exerciseFileStr 练习文件的正确答案文件的存放的绝对路径
     * @param answerFiLeStr  用户提交的答案文件存放的绝对路径
     */
    private static void compareAnswer(String exerciseFileStr, String answerFiLeStr) throws IOException {
        File resultFiLe = new File(exerciseFileStr);
        File answerFiLe = new File(answerFiLeStr);

        if (resultFiLe.exists() && answerFiLe.exists()) {
            BufferedReader resultReader = new BufferedReader(new FileReader(resultFiLe));
            BufferedReader answerReader = new BufferedReader(new FileReader(answerFiLe));

            String resultLine, answerLine;
            List<String> rightList = new ArrayList<>();
            List<String> wrongList = new ArrayList<>();
            while ((resultLine = resultReader.readLine()) != null &&
                        ((answerLine = answerReader.readLine()) != null)) {
                String[] strings = resultLine.split(". ");
                if (resultLine.equals(answerLine)) {
                    rightList.add(strings[0]);
                }else {
                    wrongList.add(strings[0]);
                }
            }
            String rightInfo = "Correct: " + getListResult(rightList);
            String wrongInfo = "Wrong: " + getListResult(wrongList);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(SAVE_PATH + "Grade.txt")));
            writer.write(rightInfo);
            writer.newLine();
            writer.write(wrongInfo);
            writer.newLine();
            writer.flush();
            writer.close();
        }else {
            System.out.println("check your file path input");
        }
    }

    /**
     * 将正确或错误的题目标号集格式化成字符串
     * @param list 正确或错误的题目标号集
     * @return 格式化后的字符串
     */
    private static String getListResult(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < list.size(); i++) {
            if (i != list.size() - 1) {
                stringBuilder.append(list.get(i)).append(", ");
            }else {
                stringBuilder.append(list.get(i));
            }
        }
        return list.size() + " (" + stringBuilder.toString() + ")";
    }

    /**
     * 生成表达式树
     * @param bound 获取多少条表达式
     * @param range 表达式中的数字的最大值
     */
    private static void generate(int bound, int range) throws IOException {
        long start = System.currentTimeMillis();
        List<ExpressionTree.TraverseResult> results = new ArrayList<>(bound);
        for (int i = 0; i < bound; i++) {
            ExpressionTree tree = new ExpressionTree(RANDOM.nextInt(3) + 1, range);
            results.add(tree.getResult());
        }
        write (results);
        long end = System.currentTimeMillis();
        System.out.println("spent " + (end - start) + " milliseconds");
    }

    /**
     * 写入题目及答案到不同的文件
     * @param results 处理完的表达式集
     */
    private static void write(List<ExpressionTree.TraverseResult> results) throws IOException {
        BufferedWriter questionsWriter = new BufferedWriter(new FileWriter(new File(SAVE_PATH + "Exercises.txt")));
        BufferedWriter answersWriter = new BufferedWriter(new FileWriter(new File(SAVE_PATH + "Answers.txt")));

        for (int i=0; i < results.size(); i++) {
            questionsWriter.write((i + 1) + ". " + results.get(i).getSubExpression() + " =");
            questionsWriter.newLine();

            answersWriter.write((i + 1) + ". " + results.get(i).getData().toString());
            answersWriter.newLine();
        }

        questionsWriter.flush();
        questionsWriter.close();

        answersWriter.flush();
        answersWriter.close();
    }

    /**
     * 获取帮助信息
     * @return 帮助信息
     */
    private static String printUsage() {
        return "Usage:" + "\t Myapp.exe [parameter] [value]\r\n" +
                "\t-n\tspecify the total count of exercises\r\n" +
                "\t-r\tspecify the maximum of the value generated in the exercises\r\n" +
                "the pattern must be like this : Myapp.exe -r value -n value";
    }
}
