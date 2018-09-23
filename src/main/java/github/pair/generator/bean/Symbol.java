package github.pair.generator.bean;

import lombok.Getter;

/**
 * 符号枚举类
 */
@Getter
public enum Symbol {
    PLUS("+", 1),
    SUBTRACT("-", 1),
    MULTIPLY("x", 2),
    DIVIDE("÷", 2),
    LEFT_BRACKET("(", 3),
    RIGHT_BRACKET(")", 3);

    private String symbol;
    private int priority;

    Symbol(String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }
}
