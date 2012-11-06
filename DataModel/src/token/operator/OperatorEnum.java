package token.operator;

import token.IComputable;
import token.computer.Computer;

import java.math.BigDecimal;
import java.math.MathContext;

public enum OperatorEnum implements IComputable {

    // operators       precedence   associativity
    // !               4            right to left
    // * / ^           3            left to right
    // + -             2            left to right
    // =               1            right to left

    ADDITION('+', 2, AssociativityType.LEFT_TO_RIGHT, 2, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].add(values[1], mathContext);
        }
    }),

    SUBTRACTION('-', 2, AssociativityType.LEFT_TO_RIGHT, 2, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].subtract(values[1], mathContext);
        }
    }),

    MULTIPLICATION('*', 3, AssociativityType.LEFT_TO_RIGHT, 2, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].multiply(values[1], mathContext);
        }
    }),

    DIVISION('/', 3, AssociativityType.LEFT_TO_RIGHT, 2, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return values[0].divide(values[1], mathContext);
        }
    }),

    EXPONENTIATION('^', 3, AssociativityType.LEFT_TO_RIGHT, 2, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return new BigDecimal(Math.pow(values[0].doubleValue(), values[1].doubleValue()), mathContext);
        }
    });

    public final char sign;
    public final int precedence;
    public final int associativity;
    public final int argumentCount;
    public final Computer computer;

    OperatorEnum(char sign, int precedence, int associativity, int argumentCount, Computer computer) {
        this.sign = sign;
        this.precedence = precedence;
        this.associativity = associativity;
        this.argumentCount = argumentCount;
        this.computer = computer;
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

    @Override
    public BigDecimal compute(BigDecimal[] operands) {
        return compute(operands, MathContext.UNLIMITED);
    }

    @Override
    public BigDecimal compute(BigDecimal[] operands, MathContext mathContext) {
        return computer.compute(operands, mathContext);
    }

}
