package token;

public class Token implements IToken {

    private final TokenType type;
    private final String expression;
    private final int start;
    private final int end;
    private final String text;

    public Token(TokenType type, String expression, int start, int end) {
        this.type = type;
        this.expression = expression;
        this.start = start;
        this.end = end;
        this.text = expression.substring(start, end);
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public String getText() {
        return text;
    }

    public String toString() {
        return this.getText();
    }

}
