package com.radkoff26.calculus.resolver;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.radkoff26.calculus.config.RuleConfig;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.MathConstants;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.model.Rule;
import com.radkoff26.calculus.model.RuleParam;
import com.radkoff26.calculus.util.DoubleUtils;
import com.radkoff26.calculus.util.RuleParamUtils;

// This class calculates a derivative of the expression as an expression tree
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
        // If expression is representing a value or a variable
        if (expression.getExpressionValue().isValue()) {
            // Then it's either 1 (a derivative of a variable) or 0 (a derivative of a constant)
            String value = expression.getExpressionValue().getValue();
            if (
                    DoubleUtils.isParseableToDouble(value)
                            || value.equals(MathConstants.E.getStringValue())
                            || value.equals(MathConstants.PI.getStringValue())) {
                return new Expression(new ExpressionValue("0"));
            } else {
                return new Expression(new ExpressionValue("1"));
            }
        }
        // Otherwise, it's an operation
        // Receive operation of the expression
        Operation operation = expression.getExpressionValue().getOperation();
        // Pull a rule needed for this operation
        Rule rule = ruleConfig.getRules().get(operation);
        // Create a map for containing args for the rule
        final Map<RuleParam, Expression> map = new EnumMap<>(RuleParam.class);
        // Fill this map with necessary args
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
        // Calculate a derivative and simplify it
        return simplificationResolver.resolve(walkAndFillDerivative(new Expression(rule.getExpressionPattern()), map));
    }

    private Expression walkAndFillDerivative(Expression expression, Map<RuleParam, Expression> map) {
        // Expression can be null since not every operation or function needs to have both operands non-nullable
        if (expression == null) {
            return null;
        }
        // If this expression represents a value or a variable, then
        if (expression.getExpressionValue().isValue()) {
            if (expression.getExpressionValue().isVariable()) {
                String value = expression.getExpressionValue().getValue();
                return map.get(RuleParamUtils.getParamByDefinition(value));
            } else {
                return expression;
            }
        }
        // Otherwise, this is an operation
        expression.setLeftOperand(walkAndFillDerivative(expression.getLeftOperand(), map));
        expression.setRightOperand(walkAndFillDerivative(expression.getRightOperand(), map));
        return expression;
    }
}
