package calculator;

import token.IToken;

public interface ITokenizer {

    public void reset(String expression);

    public IToken nextToken();

    public Boolean hasNext();

}
