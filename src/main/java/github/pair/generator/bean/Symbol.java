package github.pair.generator.bean;

import github.pair.generator.util.Calculator;
import lombok.Getter;

/**
 * 符号枚举类
 */
public enum Symbol {
    PLUS("+", 1, Calculator.PLUS),
    SUBTRACT("-", 1, Calculator.SUBTRACT),
    MULTIPLY("x", 2, Calculator.MUL),
    DIVIDE("÷", 2, Calculator.DIV),
    LEFT_BRACKET("(", 3, null),
    RIGHT_BRACKET(")", 3, null);

    private String symbol;
    private int priority;
    private Calculator calculator;

    Symbol(String symbol, int priority, Calculator calculator) {
        this.symbol = symbol;
        this.priority = priority;
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public Calculator getCalculator() {
        return calculator;
    }

    /**
     * 比较操作符的优先级
     * @param symbol  待比较的操作符
     * @return 比较结果
     */
    public boolean priGreaterThan(Symbol symbol) {
        return this.priority >= symbol.priority;
    }
}
