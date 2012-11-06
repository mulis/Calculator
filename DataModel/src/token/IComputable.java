package token;

import java.math.BigDecimal;
import java.math.MathContext;

public interface IComputable {

    public BigDecimal compute(BigDecimal[] arguments);

    public BigDecimal compute(BigDecimal[] operands, MathContext mathContext);

}
