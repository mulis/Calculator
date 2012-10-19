package token;

public interface IToken {

    public TokenType getType();

    public int getStart();

    public int getEnd();

    public String getExpression();

    public String getText();

}
