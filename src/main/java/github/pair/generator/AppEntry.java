package github.pair.generator;

import java.io.IOException;

/**
 * 程序入口
 */
public class AppEntry {
    public static void main( String[] args ) throws IOException {
        if (args.length != 4 || !args[0].equals("-r") && !args[2].equals("-n")) {
            System.out.println(printUsage());
        }
    }

    private static String printUsage() {
        return "Usage:" + "\t Myapp.exe [parameter] [value]\r\n" +
                "\t-n\tspecify the total count of exercises\r\n" +
                "\t-r\tspecify the maximum of the value generated in the exercises\r\n" +
                "the pattern must be like this : Myapp.exe -r value -n value";
    }
}
