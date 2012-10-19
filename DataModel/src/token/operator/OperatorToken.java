package token.operator;

import token.Token;
import token.TokenType;
import token.number.INumberToken;
import token.number.NumberToken;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class OperatorToken extends Token implements IOperatorToken {

    private final OperatorEnum operator;

    public OperatorToken(String expression, int start, int end) {
        super(TokenType.OPERATOR, expression, start, end);
        this.operator = OperatorEnum.getOperator(this.getText().charAt(0));
    }

    @Override
    public int getPrecedence() {
        return operator.precedence;
    }

    @Override
    public int getAssociation() {
        return operator.associativity;
    }

    @Override
    public int getArgumentCount() {
        return operator.argumentCount;
    }

    @Override
    public INumberToken operate(INumberToken[] operandTokens) {
        return operate(operandTokens, MathContext.UNLIMITED);
    }

    @Override
    public INumberToken operate(INumberToken[] operandTokens, MathContext mathContext) {

        int start = getStart();
        int end = getEnd();
        ArrayList<BigDecimal> operandsList = new ArrayList();

        for (INumberToken operandToken : operandTokens) {

            if (start > operandToken.getStart()) {
                start = operandToken.getStart();
            }

            if (end < operandToken.getEnd()) {
                end = operandToken.getEnd();
            }

            operandsList.add(operandToken.getValue());

        }

        BigDecimal result = operator.compute(operandsList.toArray(new BigDecimal[operandsList.size()]), mathContext);

        return new NumberToken(this.getExpression(), start, end, result);

    }

}
