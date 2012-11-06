import calculator.Calculator;
import calculator.exception.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void testSign() {
        testCalculation(" (+2) ", new BigDecimal(2));
        testCalculation(" (-2) ", new BigDecimal(-2));
        testCalculation(" (+2) - 2 ", new BigDecimal(0));
        testCalculation(" (-2) + 2 ", new BigDecimal(0));
    }

    @Test
    public void testAddition() {
        testCalculation(" 2 + 2 ", new BigDecimal(4));
    }

    @Test
    public void testSubtraction() {
        testCalculation(" 2 - 1 ", new BigDecimal(1));
        testCalculation(" 1 - 1 ", new BigDecimal(0));
        testCalculation(" 1 - 2 ", new BigDecimal(-1));
    }

    @Test
    public void testMultiply() {
        testCalculation(" 2 * 2 ", new BigDecimal(4));
        testCalculation(" 2 * 0 ", new BigDecimal(0));
        testCalculation(" 2 * 0.00000000000000000001 ", new BigDecimal("0.00000000000000000002"));
    }

    @Test
    public void testDivide() {
        testCalculation(" 2 / 2 ", new BigDecimal(1));
        testCalculation(" 2 / 0 ", CalculationException.class);
        testCalculation(" 2 / (4 - 2 - 2) ", CalculationException.class);
        testCalculation(" 3 / 2 ", new BigDecimal("1.5"));
        testCalculation(" 2 / 0.1 ", new BigDecimal("20"));
    }

    @Test
    public void testPower() {
        testCalculation(" 2 ^ 2 ", new BigDecimal(4));
        testCalculation(" 2 ^ 0 ", new BigDecimal(1));
        testCalculation(" 0 ^ 2 ", new BigDecimal(0));
        testCalculation(" 9 ^ (1 / 2) ", new BigDecimal(3));
        testCalculation(" 9 ^ (3 / 2) ", new BigDecimal(27));
    }

    @Test
    public void testPrecision() {
        // Precision controlled by field mathContext in Calculator class
        // Default value of mathContext is MathContext.DECIMAL64, so default precision is 16 digits
        testCalculation(" 1.2345678901234567890 ", new BigDecimal("1.234567890123457"));
        testCalculation(" 12345678901234567890 ", new BigDecimal("12345678901234570000"));
        testCalculation(" 2 / 3 ", new BigDecimal("0.6666666666666667"));
        testCalculation(" 5 / 3 ", new BigDecimal("1.666666666666667"));
    }

    //@Test
    public void testFunctions() {
        testCalculation(" sqr( 2 * 2 * 4) ", new BigDecimal(4));
        testCalculation(" min( 2 + 2 , 5) ", new BigDecimal(4));
        testCalculation(" max( 2 + 2 , 3) ", new BigDecimal(4));
        testCalculation(" 2 + sqr( 2 * 2 * 4) - 2 ", new BigDecimal(4));
        testCalculation(" 2 + min( (-2), 0, 2, (-2.2), 2.2 ) - 2", new BigDecimal("-2.2"));
        testCalculation(" min( (-2), 0, 2, (-2.2), 2.2 ) ", new BigDecimal("-2.2"));
        testCalculation(" max( (-2), 0, 2, (-2.2), 2.2 ) ", new BigDecimal("2.2"));
        testCalculation(" sum( (-2), 0, 2, (-2.2), 2.2 ) ", new BigDecimal("0"));
        testCalculation(" sqr( min(9,10) )  ", new BigDecimal("3"));
        testCalculation(" sqr( min(9,max(9,10,8)) )  ", new BigDecimal("3"));
        testCalculation(" 2+ sum( sqr(0.25), 3-3+1, min((-3),3,0,1), (-1), max((-3),3,0), 3-2 ) - 2 ", new BigDecimal("1.5"));
        testCalculation(" sqr( 2, 3 ) ", WrongArgumentsNumberException.class);
        testCalculation(" min( 2 ) ", WrongArgumentsNumberException.class);
    }

    @Test
    public void testRealNumber() {
        testCalculation(" 2.2 + 0.8 ", new BigDecimal(3.000));
    }

    @Test
    public void testParenthesis() {
        testCalculation(" 2 - ( 0 - 2 ) ", new BigDecimal(4));
        testCalculation(" 2 - ( (-2) ) ", new BigDecimal(4));
        testCalculation(" 2 - ( ( 1 - 1 ) - 2 ) ", new BigDecimal(4));
    }

    @Test
    public void testExceptions() {
        testCalculation(" 2 + a ", UnknownTokenException.class);
        testCalculation(" 2 2 ", AbsentOperatorException.class);
        testCalculation(" 2 + ", AbsentOperandException.class);
        testCalculation(" ( 2 + 2 ) ) ", ParenthesesNotMatchException.class);
        testCalculation("1 (-2) ", AbsentOperatorException.class);
    }

    public void testCalculation(String expression, BigDecimal expressionResult) {
        testCalculation(expression, expressionResult, null);
    }

    public void testCalculation(String expression, Class exceptionClass) {
        testCalculation(expression, null, exceptionClass);
    }

    public void testCalculation(String expression, BigDecimal resultExpected, Class exceptionClassExpected) {
        BigDecimal resultReceived = null;
        try {
            resultReceived = calculator.calculate(expression);
            String message = String.format("\n\tExpression: %s \n\tResult expected: %s \n\tResult received: %s \n\tException expected: %s", expression, resultExpected, resultReceived, exceptionClassExpected);
            Assert.assertTrue(message, resultReceived.compareTo(resultExpected) == 0);
        } catch (Exception exceptionReceived) {
            String message = String.format("\n\tExpression: %s \n\tResult expected: %s \n\tResult received: %s \n\tException expected: %s \n\tException received: %s \n%s\n%s\n", expression, resultExpected, resultReceived, exceptionClassExpected, exceptionReceived.getClass(), exceptionReceived, calculator.getProcessBuffer().toString());
            Assert.assertTrue(message, exceptionReceived.getClass().equals(exceptionClassExpected));
        }
    }

}
