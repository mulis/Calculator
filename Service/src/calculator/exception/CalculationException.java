package calculator.exception;

import token.IToken;

public class CalculationException extends Exception {

    public final IToken token;
    public final String message;

    public CalculationException(String message, IToken token) {
        super();
        this.message = message;
        this.token = token;
    }

    @Override
    public String toString() {
        String position = "\tposition: " + token.getStart() + "\n";
        String expression = "\texpression: " +
                token.getExpression().substring(0, token.getStart()) +
                " >>--> " +
                token.getExpression().substring(token.getStart(), token.getEnd()) +
                " <--<< " +
                token.getExpression().substring(token.getEnd());
        return message + "\n" + position + expression;
    }

}
