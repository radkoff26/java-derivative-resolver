package com.radkoff26.calculus.model;

import java.util.List;

import com.radkoff26.calculus.resolver.ExpressionResolver;

public class Rule {
    private final List<RuleParam> params;
    private final Expression expressionPattern;

    public Rule(List<RuleParam> params, String stringPattern) {
        this.params = params;
        this.expressionPattern = new ExpressionResolver().resolve(stringPattern);
    }

    public List<RuleParam> getParams() {
        return params;
    }

    public Expression getExpressionPattern() {
        return expressionPattern;
    }
}
