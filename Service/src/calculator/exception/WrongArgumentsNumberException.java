package calculator.exception;

import token.IToken;

public class WrongArgumentsNumberException extends CalculationException {
    public WrongArgumentsNumberException(IToken token) {
        super("Wrong arguments number.", token);
    }
}
