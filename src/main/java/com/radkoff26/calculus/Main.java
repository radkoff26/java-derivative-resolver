package com.radkoff26.calculus;

import com.radkoff26.calculus.resolver.StringExpressionParser;

public class Main {
    public static void main(String[] args) {
        // TODO: fix unary minus operation
        System.out.println(StringExpressionParser.parseExpression("(12 - 28) * (56 / 8)"));
    }
}
