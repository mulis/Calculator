package calculator;

import calculator.exception.CalculationException;

import java.math.BigDecimal;
import java.math.MathContext;

public interface ICalculator {

    public void setMathContext(MathContext mathContext);

    public MathContext getMathContext();

    public BigDecimal calculate(String expression) throws CalculationException;

    public StringBuffer getProcessBuffer();

}
