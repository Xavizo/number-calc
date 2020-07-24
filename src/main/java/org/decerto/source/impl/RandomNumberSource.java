package org.decerto.source.impl;

import org.decerto.source.InputSource;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberSource implements InputSource<BigDecimal> {
    @Override
    public BigDecimal getValue() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble());
    }
}
