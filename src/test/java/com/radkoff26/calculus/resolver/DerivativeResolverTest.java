package com.radkoff26.calculus.resolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.radkoff26.calculus.calculator.ExpressionCalculator;
import com.radkoff26.calculus.model.Expression;

class DerivativeResolverTest {
    public static final double DELTA = 1e-6;
    private final Resolver<String> expressionResolver = new ExpressionResolver();
    private final Resolver<Expression> derivativeResolver = new DerivativeResolver();
    private final ExpressionCalculator expressionCalculator = new ExpressionCalculator("x");

    private double calculate(String expression, double value) {
        return expressionCalculator.calculateExpression(derivativeResolver.resolve(expressionResolver.resolve(expression)), value);
    }

    @Test
    void basicDerivativeTest() {
        Assertions.assertEquals(5, calculate("x ^ 3 + x ^ 2 - 1", 1), DELTA);
    }

    @Test
    void trigonometricSinAndCosTest() {
        String s = "cos(x) * (2 * x + 4)";
        Assertions.assertEquals(2, calculate(s, 0), DELTA);
        Assertions.assertEquals(-Math.PI - 4, calculate(s, Math.PI / 2), DELTA);
    }

    @Test
    void exponentialTest() {
        Assertions.assertEquals(192, calculate("(x ^ 2) ^ 3", 2), DELTA);
        Assertions.assertEquals(1, calculate("x ^ x", 1), DELTA);
    }
}