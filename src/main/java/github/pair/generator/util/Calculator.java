package github.pair.generator.util;

import github.pair.generator.bean.Fraction;

/**
 * 分数运算类
 * @author Jordon
 */
public abstract class Calculator {

    public abstract Fraction operate(Fraction a, Fraction b);

    public static Fraction calculate(String operator, Fraction a, Fraction b) {
        if (operator != null) {
            switch (operator) {
                case "+":
                    return Calculator.PLUS.operate(a, b);
                case "-":
                    return Calculator.SUBTRACT.operate(a, b);
                case "x":
                    return Calculator.MUL.operate(a, b);
                case "÷":
                    return Calculator.DIV.operate(a, b);
                default:
                    throw new RuntimeException("operator " + operator + " does not exist");
            }
        }
        return null;
    }

    private static final Calculator PLUS = new Calculator() {
        public Fraction operate(Fraction first, Fraction second) {
            return new Fraction((first.getNumerator() * second.getDenominator() +
                    first.getDenominator() * second.getNumerator()),
                    first.getDenominator() * second.getDenominator());
        }
    };

    private static final Calculator SUBTRACT = new Calculator() {
        public Fraction operate(Fraction first, Fraction second) {
            return new Fraction((first.getNumerator() * second.getDenominator() -
                    first.getDenominator() * second.getNumerator()),
                    first.getDenominator() * second.getDenominator());
        }
    };

    private static final Calculator MUL = new Calculator() {
        public Fraction operate(Fraction a, Fraction b) {
            return new Fraction(a.getNumerator() * b.getNumerator(),
                    a.getDenominator() * b.getDenominator());
        }
    };

    private static final Calculator DIV = new Calculator() {
        public Fraction operate(Fraction dividend, Fraction divisor) {
            return Calculator.MUL.operate(dividend, divisor.reciprocal());
        }
    };
}
