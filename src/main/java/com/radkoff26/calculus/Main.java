package com.radkoff26.calculus;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.resolver.DerivativeResolver;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;

public class Main {
    public static void main(String[] args) {
        Resolver<String> expressionResolver = ExpressionResolver.getInstance();
        Resolver<Expression> derivativeResolver = DerivativeResolver.getInstance();
        Expression expression = expressionResolver.resolve("200000 + 1 / x");
        System.out.println(derivativeResolver.resolve(expression));
    }
}
