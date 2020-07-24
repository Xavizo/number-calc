package org.decerto.source;

import org.decerto.source.impl.ApiGenerateNumberSource;
import org.decerto.source.impl.RandomNumberSource;
import org.decerto.source.impl.SimpleDecimalCalcSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SimpleDecimalCalcSourceTest {

    @Test
    void whenAddBigDecimalInputSourceAndIntegerInputSource_thenReturnTotalResult() {
        //given
        ApiGenerateNumberSource i1 = mock(ApiGenerateNumberSource.class);
        given(i1.getValue()).willReturn(100);

        RandomNumberSource i2 = mock(RandomNumberSource.class);
        given(i2.getValue()).willReturn(BigDecimal.valueOf(150.75));

        //when
        BigDecimal result = new SimpleDecimalCalcSource<Integer, BigDecimal>()
                .add(i1, i2);

        //then
        assertThat(result).isEqualTo(BigDecimal.valueOf(250.75));
    }

    @Test
    void whenAddTwoIntegerInputSources_thenReturnTotalResult(){
        //given
        ApiGenerateNumberSource i1 = mock(ApiGenerateNumberSource.class);
        given(i1.getValue()).willReturn(100);

        ApiGenerateNumberSource i2 = mock(ApiGenerateNumberSource.class);
        given(i2.getValue()).willReturn(250);

        //when
        BigDecimal result = new SimpleDecimalCalcSource<Integer, Integer>()
                .add(i1, i2);

        //then
        assertThat(result).isEqualTo(BigDecimal.valueOf(350));
    }
}