package calculator.exception;

import token.IToken;

public class AbsentOperatorException extends CalculationException {
    public AbsentOperatorException(IToken token) {
        super("Absent operator.", token);
    }
}
