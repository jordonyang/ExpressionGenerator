package github.pair.generator.util;

import github.pair.generator.bean.Fraction;
import github.pair.generator.bean.Symbol;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomUtil {

    private static final Random RANDOM = new Random();
    private static final char[] operators = {'+', '-', 'x', '÷'};
    public static final Map<String, Symbol> symbolMatcher;

    static {
        symbolMatcher = new HashMap<>(5, 1);
        symbolMatcher.put("(", Symbol.LEFT_BRACKET);
        symbolMatcher.put("x", Symbol.MULTIPLY);
        symbolMatcher.put("÷", Symbol.DIVIDE);
        symbolMatcher.put("+", Symbol.PLUS);
        symbolMatcher.put("-", Symbol.SUBTRACT);
    }

    /**
     * 随机生成运算符
     * @return  Symbol
     */
    public static Symbol generateOperator() {
        int index = RANDOM.nextInt(operators.length);
        return symbolMatcher.get(String.valueOf(operators[index]));
    }

    /**
     * 随机生成分数
     * @param range 单个数据的范围
     * @return 分数
     */
    public static Fraction generateFraction(int range) {
        // 控制生成的表达式集中的分数的数量
        int i = RANDOM.nextInt(7);
        int numerator = RANDOM.nextInt(range) + 1;
        // 防止分母为0的情况
        int denominator = RANDOM.nextInt(range - 1) + 1;

        if (i == 1) {
            return new Fraction(numerator, denominator);
        }
        return new Fraction(numerator, 1);
    }
}
