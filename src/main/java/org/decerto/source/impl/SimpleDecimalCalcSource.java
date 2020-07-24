package org.decerto.source.impl;

import org.decerto.source.CalcSource;
import org.decerto.source.InputSource;

import java.math.BigDecimal;

public class SimpleDecimalCalcSource<V1, V2> implements CalcSource<BigDecimal, V1, V2> {
    @Override
    public BigDecimal add(InputSource<V1> i1, InputSource<V2> i2) {
        V1 firstInput = i1.getValue();
        V2 secondInput = i2.getValue();

        return toBigDecimal(firstInput)
                .add(toBigDecimal(secondInput));
    }

    private static BigDecimal toBigDecimal(Object obj){
        return new BigDecimal(String.valueOf(obj));
    }
}
