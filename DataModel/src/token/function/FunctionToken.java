package token.function;

import token.Token;
import token.TokenType;
import token.number.INumberToken;
import token.number.NumberToken;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class FunctionToken extends Token implements IFunctionToken {

    private final FunctionEnum function;
    private int argumentCount = 1;

    public FunctionToken(String expression, int start, int end) {
        super(TokenType.FUNCTION, expression, start, end);
        this.function = FunctionEnum.getFunction(this.getText());
    }

    @Override
    public int getArgumentCount() {
        return argumentCount;
    }

    @Override
    public void setArgumentCount(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    @Override
    public boolean isArgumentCountInRange() {
        return (argumentCount >= function.argumentCountMin) && (argumentCount <= function.argumentCountMax);
    }

    @Override
    public INumberToken compute(INumberToken[] argumentTokens) {
        return compute(argumentTokens, MathContext.UNLIMITED);
    }

    @Override
    public INumberToken compute(INumberToken[] argumentTokens, MathContext mathContext) {

        int start = getStart();
        int end = getEnd();
        ArrayList<BigDecimal> argumentsList = new ArrayList<BigDecimal>();

        for (INumberToken argumentToken : argumentTokens) {

            if (start > argumentToken.getStart()) {
                start = argumentToken.getStart();
            }

            if (end < argumentToken.getEnd()) {
                end = argumentToken.getEnd();
            }

            argumentsList.add(argumentToken.getValue());

        }

        BigDecimal result = function.compute(argumentsList.toArray(new BigDecimal[argumentsList.size()]), mathContext);

        return new NumberToken(this.getExpression(), start, end, result);

    }

    public String toString() {
        return this.getText() + "@" + this.getArgumentCount();
    }

}
