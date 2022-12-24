package com.radkoff26.calculus.converter;

import org.junit.jupiter.api.Test;

import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;

import static org.junit.jupiter.api.Assertions.*;

class LatexConverterTest {
    private final Resolver<String> resolver = new ExpressionResolver();
    private final Converter<String> converter = new LatexConverter();

    @Test
    void easyTest() {
        assertEquals("$({x} ^ {2} + 2)$", converter.convert(resolver.resolve("x ^ 2 + 2")));
    }

    @Test
    void mediumTest() {
        assertEquals("$\\frac{sin({\\pi} \\cdot {x})}{cos(x)}$", converter.convert(resolver.resolve("sin(pi * x) / cos(x)")));
    }

    @Test
    void hardTest() {
        assertEquals(
                "$\\frac{{50} \\cdot {x}}{{(27 + {x} ^ {3})} ^ {\\frac{1}{3}}}$",
                converter.convert(resolver.resolve("(50 * x) / ((27 + x ^ 3) ^ (1 / 3))"))
        );
    }
}