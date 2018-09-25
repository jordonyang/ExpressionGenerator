package github.pair.generator;

import github.pair.generator.bean.tree.ExpressionTree;
import org.junit.Test;
import java.io.*;

public class AppTest {

    /**
     * 参数非法
     */
    @Test
    public void testArgs() throws IOException {
        String[] args = {"-n", "10000"};
        AppEntry.main(args);
    }

    @Test
    public void testEfficiency() {
        int[] nums = {10,100,1000,10000,100000};
        for (int num : nums) {
            generate(num);
        }
    }

    private void generate(int num) {
        long start = System.currentTimeMillis();
        for (int i= 0; i < num;i++) {
            new ExpressionTree(3,100).getResult();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds " +
                "spent to generate and traverse " + num + " exercises");
    }
    /**
     * 操作符个数非法
     */
    @Test
    public void testTree() throws IOException {
        ExpressionTree tree = new ExpressionTree(-1,100);
    }

    /**
     * 生成10000道题目
     */
    @Test
    public void genTest() throws IOException {
        String[] args = {"-r", "10", "-n", "10000"};
        AppEntry.main(args);
    }

    /**
     * 比对答案
     */
    @Test
    public void testCompare() throws IOException {
        String[] args = {"-e", "E:\\projects\\java\\ExpressionGenerator\\test-result\\first\\answers.txt", "-a", "E:\\projects\\java\\ExpressionGenerator\\test-result\\first\\submit.txt"};
        AppEntry.main(args);

        String[] args1 = {"-e", "E:\\projects\\java\\ExpressionGenerator\\test-result\\answers bla.txt", "-a", "E:\\projects\\java\\ExpressionGenerator\\test-result\\submit.txt"};
        AppEntry.main(args1);
    }
}
