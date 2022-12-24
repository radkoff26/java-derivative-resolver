package com.radkoff26.calculus.converter;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.MathConstants;
import com.radkoff26.calculus.model.Operation;

public class LatexConverter implements Converter<String> {

    @Override
    public String convert(Expression expression) {
        return String.format("$%s$", toLatexString(expression));
    }

    private String toLatexString(Expression expression) {
        // If the expression is value
        if (expression.getExpressionValue().isValue()) {
            // Then it's returned
            String value = expression.getExpressionValue().getValue();
            // If it's constant PI, then it's reformatted
            if (value.equals(MathConstants.PI.getStringValue())) {
                return "\\pi";
            }
            // Otherwise, this is returned as it is
            return value;
        }
        // Otherwise, this is a resolvable operational expression
        return proceedExpressionOperation(expression);
    }

    private String proceedExpressionOperation(Expression expression) {
        if (expression.getLeftOperand() != null) {
            // If the left operand is not null, then it's possible to stringify it
            String left = toLatexString(expression.getLeftOperand());
            Operation operation = expression.getExpressionValue().getOperation();
            if (expression.getRightOperand() != null) {
                // If the right one exists, then it's also reformatted
                String right = toLatexString(expression.getRightOperand());
                if (operation.isFunction()) {
                    // Two args function
                    return String.format("%s(%s, %s)", operation.getDefinition(), left, right);
                } else {
                    // Just an operation
                    switch (operation) {
                        case DIV:
                            return String.format("\\frac{%s}{%s}", left, right);
                        case MUL:
                            return String.format("{%s} \\cdot {%s}", left, right);
                        case POW:
                            return String.format("{%s} ^ {%s}", left, right);
                        default:
                            return String.format("(%s %s %s)", left, operation.getDefinition(), right);
                    }
                }
            } else {
                // One arg function
                return String.format("%s(%s)", operation.getDefinition(), left);
            }
        } else {
            // If the left one is not present, then it's a unary minus operation
            return String.format("-(%s)", proceedExpressionOperation(expression.getRightOperand()));
        }
    }
}
