package github.pair.generator.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 分数数据结构
 */
@Getter
@Setter
public class Fraction {
    // 分子
    private int numerator;
    // 分母
    private int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        if (numerator > denominator && denominator != 1) { //带分数
            int left = numerator / denominator;
            return left + "^" + (numerator - left * denominator) + "/" + denominator;
        }
        else if (numerator == 0)
            return "0";
        return numerator + (denominator==1 ? "" : ("/" + denominator));
    }
}
