package token.number;

import token.Token;
import token.TokenType;

import java.math.BigDecimal;

public class NumberToken extends Token implements INumberToken {

    private final BigDecimal value;

    public NumberToken(String expression, int start, int end) {
        super(TokenType.NUMBER, expression, start, end);
        this.value = new BigDecimal(this.getText());
    }

    public NumberToken(String expression, int start, int end, BigDecimal value) {
        super(TokenType.NUMBER, expression, start, end);
        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    public String toString() {
        return this.getValue().toString();
    }

}
