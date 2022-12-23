package com.radkoff26.calculus;

import com.radkoff26.calculus.calculator.ExpressionCalculator;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.resolver.DerivativeResolver;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;
import com.radkoff26.calculus.resolver.SimplificationResolver;

public class Main {
    public static void main(String[] args) {
        Resolver<String> expressionResolver = new ExpressionResolver();
        Resolver<Expression> derivativeResolver = new DerivativeResolver();
        Resolver<Expression> simplificationResolver = new SimplificationResolver();
        ExpressionCalculator expressionCalculator = new ExpressionCalculator("x");
        Expression expression = expressionResolver.resolve("(((((sin(x) + 2) * x))))");
        Expression derivative = derivativeResolver.resolve(expression);
        System.out.println(derivative);
    }
}
