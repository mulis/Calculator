package token.operator;

import java.math.BigDecimal;
import java.math.MathContext;

public enum OperatorEnum {

    // operators       precedence   associativity
    // !               4            right to left
    // * / ^           3            left to right
    // + -             2            left to right
    // =               1            right to left

    ADDITION('+', 2, AssociativityType.LEFT_TO_RIGHT, 2) {
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].add(values[1], mathContext);
        }
    },

    SUBTRACTION('-', 2, AssociativityType.LEFT_TO_RIGHT, 2) {
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].subtract(values[1], mathContext);
        }
    };

    public final char sign;
    public final int precedence;
    public final int associativity;
    public final int argumentCount;

    OperatorEnum(char sign, int precedence, int associativity, int argumentCount) {
        this.sign = sign;
        this.precedence = precedence;
        this.associativity = associativity;
        this.argumentCount = argumentCount;
    }

    static OperatorEnum getOperator(char sign) {

        for (OperatorEnum operator : OperatorEnum.values()) {
            if (sign == operator.sign) return operator;
        }

        return null;

    }

    public static String getPattern() {

        StringBuilder pattern = new StringBuilder();

        for (OperatorEnum operator : OperatorEnum.values()) {
            pattern.append("\\G\\").append(operator.sign).append("|");
        }

        if (pattern.length() > 0) {
            pattern.deleteCharAt(pattern.length() - 1);
        }

        return pattern.toString();

    }

    public BigDecimal compute(BigDecimal[] operands) {
        return compute(operands, MathContext.UNLIMITED);
    }

    public abstract BigDecimal compute(BigDecimal[] operands, MathContext mathContext);

}
