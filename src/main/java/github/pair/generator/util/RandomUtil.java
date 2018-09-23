package github.pair.generator.util;

import github.pair.generator.bean.Fraction;
import github.pair.generator.bean.Symbol;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomUtil {

    private static final Random numRandom = new Random();
    private static final char[] operators = {'+', '-', 'x', '÷'};
    private static final Random opRandom = new Random();
    private static final Map<String, Symbol> symbolMatcher;

    static {
        symbolMatcher = new HashMap<>(5, 1);
        symbolMatcher.put("(", Symbol.LEFT_BRACKET);
        symbolMatcher.put("x", Symbol.MULTIPLY);
        symbolMatcher.put("÷", Symbol.DIVIDE);
        symbolMatcher.put("+", Symbol.PLUS);
        symbolMatcher.put("-", Symbol.SUBTRACT);
    }

    public static Symbol generateOperator() {
        int index = opRandom.nextInt(operators.length);
        return symbolMatcher.get(String.valueOf(operators[index]));
    }

    public static Fraction generateFraction() {
        int i = numRandom.nextInt(9);
        int numerator = numRandom.nextInt(10) + 1;
        // 防止分母为0的情况
        int denominator = numRandom.nextInt(9) + 1;

        if (i == 1) {
            return new Fraction(numerator, denominator);
        }
        return new Fraction(numerator, 1);
    }
}
