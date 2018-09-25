package github.pair.generator.util;

import github.pair.generator.bean.Fraction;

/**
 * 分数运算类
 * @author Jordon
 */
public abstract class Calculator {

    public abstract Fraction calculate(Fraction a, Fraction b);

    public static final Calculator PLUS = new Calculator() {
        public Fraction calculate(Fraction first, Fraction second) {
            return new Fraction((first.getNumerator() * second.getDenominator() +
                    first.getDenominator() * second.getNumerator()),
                    first.getDenominator() * second.getDenominator());
        }
    };

    public static final Calculator SUBTRACT = new Calculator() {
        public Fraction calculate(Fraction first, Fraction second) {
            return new Fraction((first.getNumerator() * second.getDenominator() -
                    first.getDenominator() * second.getNumerator()),
                    first.getDenominator() * second.getDenominator());
        }
    };

    public static final Calculator MUL = new Calculator() {
        public Fraction calculate(Fraction a, Fraction b) {
            return new Fraction(a.getNumerator() * b.getNumerator(),
                    a.getDenominator() * b.getDenominator());
        }
    };

    public static final Calculator DIV = new Calculator() {
        public Fraction calculate(Fraction dividend, Fraction divisor) {
            return Calculator.MUL.calculate(dividend, divisor.reciprocal());
        }
    };
}
