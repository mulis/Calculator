package token.number;

import token.IToken;

import java.math.BigDecimal;

public interface INumberToken extends IToken {

    BigDecimal getValue();

}
