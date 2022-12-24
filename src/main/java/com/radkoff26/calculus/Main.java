package com.radkoff26.calculus;

import com.radkoff26.calculus.calculator.ExpressionCalculator;
import com.radkoff26.calculus.converter.Converter;
import com.radkoff26.calculus.converter.LatexConverter;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.resolver.DerivativeResolver;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;
import com.radkoff26.calculus.resolver.SimplificationResolver;

public class Main {
    public static void main(String[] args) {
        // Here is a subtle example how the project is working
        // You create necessary resolvers
        Resolver<String> expressionResolver = new ExpressionResolver();
        Resolver<Expression> derivativeResolver = new DerivativeResolver();
        // This resolver is not necessary to use since derivative resolver does this job itself
        Resolver<Expression> simplificationResolver = new SimplificationResolver();
        // Calculator to find out the value of the expression in the certain point
        ExpressionCalculator expressionCalculator = new ExpressionCalculator("x");
        // Instantiate converter to LaTeX if it's necessary
        Converter<String> converter = new LatexConverter();
        // ...
    }
}
