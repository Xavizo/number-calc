package org.decerto.source;

import org.decerto.source.impl.RandomNumberSource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class RandomNumberSourceTest {
    @Test
    void whenGenerateRandomNumber_returnDoubleValue() {
        BigDecimal value = new RandomNumberSource().getValue();
        assertThat(value).isNotNull();
    }
}