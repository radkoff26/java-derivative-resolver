package com.radkoff26.calculus;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.resolver.DerivativeResolver;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;
import com.radkoff26.calculus.resolver.SimplificationResolver;

public class Main {
    public static void main(String[] args) {
        Resolver<String> expressionResolver = ExpressionResolver.getInstance();
        Resolver<Expression> derivativeResolver = DerivativeResolver.getInstance();
        Resolver<Expression> simplificationResolver = new SimplificationResolver();
        Expression expression = expressionResolver.resolve("x ^ 2");
        Expression derivative = derivativeResolver.resolve(expression);
        System.out.println(simplificationResolver.resolve(derivative));
    }
}
