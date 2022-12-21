package com.radkoff26.calculus.resolver;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.radkoff26.calculus.config.RuleConfig;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.model.Rule;
import com.radkoff26.calculus.model.RuleParam;

public class DerivativeResolver implements Resolver<Expression> {
    public static DerivativeResolver INSTANCE;
    private RuleConfig ruleConfig;

    private DerivativeResolver() {
        try {
            this.ruleConfig = new RuleConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DerivativeResolver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DerivativeResolver();
        }
        return INSTANCE;
    }

    @Override
    public Expression resolve(final Expression expression) {
        if (expression.getExpressionValue().isValue()) {
            try {
                Double.parseDouble(expression.getExpressionValue().getValue());
                return new Expression(new ExpressionValue("0"));
            } catch (NumberFormatException e) {
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
        return walkAndFillDerivative(rule.getExpressionPattern(), map);
    }

    private Expression walkAndFillDerivative(Expression expression, Map<RuleParam, Expression> map) {
        if (expression.getExpressionValue().isValue()) {
            String value = expression.getExpressionValue().getValue();
            return map.get(RuleParam.LEFT.getParamByDefinition(value));
        }
        expression.setLeftOperand(walkAndFillDerivative(expression.getLeftOperand(), map));
        expression.setRightOperand(walkAndFillDerivative(expression.getRightOperand(), map));
        return expression;
    }
}
