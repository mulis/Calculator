package token.function;

import token.IToken;
import token.number.INumberToken;

import java.math.MathContext;

/**
 * Created with IntelliJ IDEA.
 * User: Mulishov Serhij
 * Date: 08.10.12
 * Time: 1:53
 */
public interface IFunctionToken extends IToken {

    int getArgumentCount();

    void setArgumentCount(int argumentCount);

    boolean isArgumentCountInRange();

    INumberToken compute(INumberToken[] argumentTokens);

    INumberToken compute(INumberToken[] argumentTokens, MathContext mathContext);

}
