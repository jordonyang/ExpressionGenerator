package github.pair.generator.bean;

import github.pair.generator.util.MathUtil;
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
        this.reduce();
    }

    /**
     * 取倒数
     * @return Fraction
     */
    public Fraction reciprocal() {
        if (numerator != 0) {
            int temp = this.denominator;
            this.denominator = this.numerator;
            this.numerator = temp;
        }
        return this;
    }

    // 约简
    private void reduce() {
        if (numerator > 0 ) {
            int gcd = MathUtil.gcd(denominator, numerator);
            this.numerator /= gcd;
            this.denominator /= gcd;
        }
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
