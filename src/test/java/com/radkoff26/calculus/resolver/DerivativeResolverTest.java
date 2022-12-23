package com.radkoff26.calculus.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.radkoff26.calculus.calculator.ExpressionCalculator;
import com.radkoff26.calculus.model.Expression;

class DerivativeResolverTest {
    private final Resolver<String> expressionResolver = new ExpressionResolver();
    private final Resolver<Expression> derivativeResolver = new DerivativeResolver();
    private final ExpressionCalculator expressionCalculator = new ExpressionCalculator("x");

    private double calculate(String expression, double value) {
        return expressionCalculator.calculateExpression(derivativeResolver.resolve(expressionResolver.resolve(expression)), value);
    }

    @Test
    void basicDerivativeTest() {
        Assertions.assertEquals(5, calculate("x ^ 3 + x ^ 2 - 1", 1), 1e-6);
    }
}