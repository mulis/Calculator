package calculator.exception;

import token.IToken;

public class AbsentArgumentException extends CalculationException {
    public AbsentArgumentException(IToken token) {
        super("Absent argument.", token);
    }
}
