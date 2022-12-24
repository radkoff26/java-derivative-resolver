package com.radkoff26.calculus.converter;

import com.radkoff26.calculus.model.Expression;

public interface Converter<T> {

    T convert(Expression expression);
}
