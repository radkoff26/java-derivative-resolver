package com.radkoff26.calculus.resolver;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.radkoff26.calculus.config.RuleConfig;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.model.Rule;
import com.radkoff26.calculus.model.RuleParam;
import com.radkoff26.calculus.util.DoubleUtils;

public class DerivativeResolver implements Resolver<Expression> {
    private RuleConfig ruleConfig;
    private final SimplificationResolver simplificationResolver;

    public DerivativeResolver() {
        this.simplificationResolver = new SimplificationResolver();
        try {
            this.ruleConfig = new RuleConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Expression resolve(final Expression expression) {
        if (expression.getExpressionValue().isValue()) {
            String value = expression.getExpressionValue().getValue();
            if (DoubleUtils.isParseableToDouble(value) || value.equals("e") || value.equals("pi")) {
                return new Expression(new ExpressionValue("0"));
            } else {
                return new Expression(new ExpressionValue("1"));
            }
        }
        Operation operation = expression.getExpressionValue().getOperation();
        Rule rule = ruleConfig.getRules().get(operation);
        final Map<RuleParam, Expression> map = new EnumMap<>(RuleParam.class);
        rule.getParams().forEach(ruleParam -> {
            Expression currentExpression;
            switch (ruleParam) {
                case LEFT:
                    currentExpression = expression.getLeftOperand();
                    break;
                case LEFT_DERIVATIVE:
                    currentExpression = resolve(expression.getLeftOperand());
                    break;
                case RIGHT:
                    currentExpression = expression.getRightOperand();
                    break;
                case RIGHT_DERIVATIVE:
                    currentExpression = resolve(expression.getRightOperand());
                    break;
                default:
                    currentExpression = null;
            }
            map.put(ruleParam, currentExpression);
        });
        return simplificationResolver.resolve(walkAndFillDerivative(new Expression(rule.getExpressionPattern()), map));
    }

    private Expression walkAndFillDerivative(Expression expression, Map<RuleParam, Expression> map) {
        if (expression == null) {
            return null;
        }
        if (expression.getExpressionValue().isValue()) {
            if (expression.getExpressionValue().isVariable()) {
                String value = expression.getExpressionValue().getValue();
                return map.get(RuleParam.LEFT.getParamByDefinition(value));
            } else {
                return expression;
            }
        }
        expression.setLeftOperand(walkAndFillDerivative(expression.getLeftOperand(), map));
        expression.setRightOperand(walkAndFillDerivative(expression.getRightOperand(), map));
        return expression;
    }
}
