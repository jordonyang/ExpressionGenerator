package github.pair.generator;

import github.pair.generator.bean.tree.ExpressionTree;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppTest {

    /**
     * 参数非法
     */
    @Test
    public void testArgs() throws IOException {
        String[] args = {"-n", "10000"};
        AppEntry.main(args);
    }

    /**
     * 生成10000道题目
     */
    @Test
    public void genTest() throws IOException {
        String[] args = {"-r", "10", "-n", "10000"};
        AppEntry.main(args);
    }

    @Test
    public void testPath() {
        System.out.println(new File("").getAbsolutePath());
    }


}
