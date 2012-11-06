package token;

import token.function.FunctionToken;
import token.number.NumberToken;
import token.operator.OperatorToken;

public class TokenFactory {

    public static IToken makeToken(TokenType type, String expression, int start, int end) {

        if (type == TokenType.SIGNED_NUMBER) {
            return new NumberToken(expression, start + 1, end - 1);
        }

        if (type == TokenType.NUMBER) {
            return new NumberToken(expression, start, end);
        }

        if (type == TokenType.OPERATOR) {
            return new OperatorToken(expression, start, end);
        }

        if (type == TokenType.FUNCTION) {
            return new FunctionToken(expression, start, end);
        }

        if (type == TokenType.FUNCTION_ARGUMENT_SEPARATOR) {
            return new Token(TokenType.FUNCTION_ARGUMENT_SEPARATOR, expression, start, end);
        }

        if (type == TokenType.PARENTHESIS_LEFT) {
            return new Token(TokenType.PARENTHESIS_LEFT, expression, start, end);
        }

        if (type == TokenType.PARENTHESIS_RIGHT) {
            return new Token(TokenType.PARENTHESIS_RIGHT, expression, start, end);
        }

        return new Token(TokenType.UNKNOWN, expression, start, end);

    }

}
