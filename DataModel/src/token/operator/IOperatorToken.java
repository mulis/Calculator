package token.operator;

import token.IToken;
import token.number.INumberToken;

import java.math.MathContext;

public interface IOperatorToken extends IToken {

    int getPrecedence();

    int getAssociation();

    int getArgumentCount();

    INumberToken operate(INumberToken[] operandTokens);

    INumberToken operate(INumberToken[] operandTokens, MathContext mathContext);

}
