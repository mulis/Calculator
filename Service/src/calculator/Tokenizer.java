package calculator;

import token.IToken;
import token.TokenFactory;
import token.TokenType;

import java.util.regex.Matcher;

public class Tokenizer implements ITokenizer {

    private final String expression;
    private int expressionPosition;

    Tokenizer(String expression) {
        this.expression = expression;
        reset(expression);
    }

    @Override
    public void reset(String expression) {

        for (TokenType type : TokenType.values()) {
            type.matcher.reset(expression);
        }
        expressionPosition = 0;
        skipSpaces();

    }

    @Override
    public IToken nextToken() {

        TokenType nextTokenType = TokenType.UNKNOWN;
        int nextTokenStart = expressionPosition;
        int nextTokenEnd = expression.length();

        for (TokenType type : TokenType.values()) {

            Matcher matcher = type.matcher;

            if (matcher.find(expressionPosition)) {
                nextTokenType = type;
                nextTokenStart = matcher.start();
                nextTokenEnd = matcher.end();
                expressionPosition = matcher.end();
                skipSpaces();
                break;
            }

        }

        return TokenFactory.makeToken(nextTokenType, expression, nextTokenStart, nextTokenEnd);

    }

    @Override
    public Boolean hasNext() {
        return (expressionPosition < expression.length());
    }

    private void skipSpaces() {

        while (hasNext() && (expression.charAt(expressionPosition) == ' ')) {
            expressionPosition++;
        }

    }

}
