package github.pair.generator;

import github.pair.generator.bean.tree.ExpressionTree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 程序入口
 */
public class AppEntry {

    private static final String SAVE_PATH = new File("").getAbsolutePath()
                + File.separator + "test-result" + File.separator;

    public static void main( String[] args ) throws IOException {
        if (args.length != 4 || !args[0].equals("-r") && !args[2].equals("-n")) {
            System.out.println(printUsage());
        }else {
            int num = Integer.parseInt(args[3]);
            long start = System.currentTimeMillis();
            FileWriter fileWriter = new FileWriter(new File(SAVE_PATH + "exercises.txt"));
            FileWriter fileWriter1 = new FileWriter(new File(SAVE_PATH + "answers.txt"));

            BufferedWriter questionsWriter = new BufferedWriter(fileWriter);
            BufferedWriter answersWriter = new BufferedWriter(fileWriter1);
            for (int i = 0; i < num; i++) {
                ExpressionTree expression = new ExpressionTree(new Random().nextInt(4) + 1);
                ExpressionTree.TraverseResult result = expression.getResult();
                questionsWriter.write(i + ". " + result.getSubExpression() + " =");
                questionsWriter.newLine();

                answersWriter.write(i + ". " + result.getData().toString());
                answersWriter.newLine();
            }
            questionsWriter.flush();
            questionsWriter.close();

            answersWriter.flush();
            answersWriter.close();
            long end = System.currentTimeMillis();
            System.out.println("spent " + (end - start) + " milliseconds");
        }
    }

    private static String printUsage() {
        return "Usage:" + "\t Myapp.exe [parameter] [value]\r\n" +
                "\t-n\tspecify the total count of exercises\r\n" +
                "\t-r\tspecify the maximum of the value generated in the exercises\r\n" +
                "the pattern must be like this : Myapp.exe -r value -n value";
    }
}
