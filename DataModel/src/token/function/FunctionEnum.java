package token.function;

import token.IComputable;
import token.computer.Computer;

import java.math.BigDecimal;
import java.math.MathContext;

public enum FunctionEnum implements IComputable {

    SQR("sqr", 1, 1, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            return new BigDecimal(Math.sqrt(values[0].doubleValue()), mathContext);
        }
    }),

    MIN("min", 2, Integer.MAX_VALUE, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            BigDecimal result = values[0];
            for (int i = 1; i < values.length; ++i) {
                result = new BigDecimal(Math.min(result.doubleValue(), values[i].doubleValue()), mathContext);
            }
            return result;
        }
    }),

    MAX("max", 2, Integer.MAX_VALUE, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            BigDecimal result = values[0];
            for (int i = 1; i < values.length; ++i) {
                result = new BigDecimal(Math.max(result.doubleValue(), values[i].doubleValue()), mathContext);
            }
            return result;
        }
    }),

    SUM("sum", 1, Integer.MAX_VALUE, new Computer() {
        @Override
        public BigDecimal compute(BigDecimal[] values, MathContext mathContext) {
            BigDecimal result = values[0];
            for (int i = 1; i < values.length; ++i) {
                result = result.add(values[i], mathContext);
            }
            return result;
        }
    });

    public final String abbreviation;
    public final int argumentCountMin;
    public final int argumentCountMax;
    public final Computer computer;

    FunctionEnum(String abbreviation, int argumentCountMin, int argumentCountMax, Computer computer) {
        this.abbreviation = abbreviation;
        this.argumentCountMin = argumentCountMin;
        this.argumentCountMax = argumentCountMax;
        this.computer = computer;
    }

    public static FunctionEnum getFunction(String name) {

        for (FunctionEnum function : FunctionEnum.values()) {
            if (name.equals(function.abbreviation)) return function;
        }

        return null;

    }

    public static String getPattern() {

        StringBuilder pattern = new StringBuilder();

        for (FunctionEnum function : FunctionEnum.values()) {
            pattern.append("\\G").append(function.abbreviation).append("|");
        }

        if (pattern.length() > 0) {
            pattern.deleteCharAt(pattern.length() - 1);
        }

        return pattern.toString();

    }

    @Override
    public BigDecimal compute(BigDecimal[] arguments) {
        return compute(arguments, MathContext.UNLIMITED);
    }

    @Override
    public BigDecimal compute(BigDecimal[] arguments, MathContext mathContext) {
        return computer.compute(arguments, mathContext);
    }

}
