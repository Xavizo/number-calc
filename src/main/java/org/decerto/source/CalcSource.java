package org.decerto.source;

public interface CalcSource<R extends Number, V1, V2> {
    R add(InputSource<V1> i1, InputSource<V2> i2);
}
