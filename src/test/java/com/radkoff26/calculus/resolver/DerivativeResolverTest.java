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
    void exponentialTest() {
        Assertions.assertEquals(192, calculate("(x ^ 2) ^ 3", 2), DELTA);
        Assertions.assertEquals(1, calculate("x ^ x", 1), DELTA);
        Assertions.assertEquals(2 * Math.E, calculate("e ^ (x ^ 2)", 1), DELTA);
    }

    @Test
    void logarithmTest() {
        Assertions.assertEquals(1 / Math.log(2), calculate("log(2*x,x)", 1), DELTA);
        Assertions.assertEquals(-1, calculate("ln(cos(x))", Math.PI / 4), DELTA);
    }

    @Test
    void trigonometricSinAndCosTest() {
        String s = "cos(x) * (2 * x + 4)";
        Assertions.assertEquals(2, calculate(s, 0), DELTA);
        Assertions.assertEquals(-Math.PI - 4, calculate(s, Math.PI / 2), DELTA);
        Assertions.assertEquals(2 * Math.cos(1), calculate("sin(tg(x))", Math.PI / 4), DELTA);
    }

    @Test
    void trigonometricTgAndCtgTest() {
        Assertions.assertEquals(2 * Math.sqrt(Math.PI), calculate("tg(x ^ 2)", Math.sqrt(Math.PI)), DELTA);
        Assertions.assertEquals(-2, calculate("ctg(2 * x)", Math.PI / 4), DELTA);
    }
}