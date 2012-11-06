package token;

import token.operator.OperatorEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TokenType {

    SIGNED_NUMBER("\\G\\([\\+\\-]\\d+\\)"),
    NUMBER("\\G\\d*\\.?\\d+"),
    PARENTHESIS_LEFT("\\G\\("),
    PARENTHESIS_RIGHT("\\G\\)"),
    OPERATOR(OperatorEnum.getPattern()),
    UNKNOWN("\\G\\.+");

    public final Matcher matcher;

    TokenType(String pattern) {
        matcher = Pattern.compile(pattern).matcher("");
    }

}
