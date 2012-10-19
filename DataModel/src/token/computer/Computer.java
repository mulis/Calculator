package token.computer;

import java.math.BigDecimal;
import java.math.MathContext;

public abstract class Computer {

    public abstract BigDecimal compute(BigDecimal[] values, MathContext mathContext);

}
