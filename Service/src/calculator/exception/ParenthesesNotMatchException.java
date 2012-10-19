package calculator.exception;

import token.IToken;

public class ParenthesesNotMatchException extends CalculationException {
    public ParenthesesNotMatchException(IToken token) {
        super("Parentheses did not match.", token);
    }
}
