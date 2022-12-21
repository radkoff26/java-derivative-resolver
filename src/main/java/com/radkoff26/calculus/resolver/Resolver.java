package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;

public interface Resolver<T> {
    Expression resolve(T t);
}
