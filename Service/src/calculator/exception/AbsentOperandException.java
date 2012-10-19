package calculator.exception;

import token.IToken;

public class AbsentOperandException extends CalculationException {
    public AbsentOperandException(IToken token) {
        super("Absent operand.", token);
    }
}
