package calculator.exception;

import token.IToken;

public class UnknownTokenException extends CalculationException {
    public UnknownTokenException(IToken token) {
        super("Unknown token.", token);
    }
}
