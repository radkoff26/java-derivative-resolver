package com.radkoff26.calculus.calculator;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.MathConstants;
import com.radkoff26.calculus.model.Operation;

// Here is a class which calculates value of the expression in the particular point
public class ExpressionCalculator {
    private final String variable;

    public ExpressionCalculator(String variable) {
        this.variable = variable;
    }

    public double calculateExpression(Expression expression, double valueOfVariable) {
        // The expression can be null
        if (expression == null) {
            return 0;
        }
        // If expression is value
        if (expression.getExpressionValue().isValue()) {
            String value = expression.getExpressionValue().getValue();
            // Then it's changed to the value of point by the given variable
            if (value.equals(variable)) {
                return valueOfVariable;
            }
            // Or this expression represents a constant E
            if (value.equals(MathConstants.E.getStringValue())) {
                return Math.E;
            }
            // Or this expression represents a constant PI
            if (value.equals(MathConstants.PI.getStringValue())) {
                return Math.PI;
            }
            // Otherwise, this is a numeric parseable value which will be parsed and returned
            return Double.parseDouble(value);
        }
        // When the expression is not a value but an operation, it's calculated
        return calculateExpressionOperation(
                calculateExpression(expression.getLeftOperand(), valueOfVariable),
                expression.getExpressionValue().getOperation(),
                calculateExpression(expression.getRightOperand(), valueOfVariable)
        );
    }

    private double calculateExpressionOperation(double a, Operation operation, double b) {
        switch (operation) {
            case ADD:
                return a + b;
            case SUB:
                return a - b;
            case MUL:
                return a * b;
            case DIV:
                return a / b;
            case POW:
                return Math.pow(a, b);
            case LN:
                return Math.log(a);
            case LOG:
                return Math.log(b) / Math.log(a);
            case SIN:
                return Math.sin(a);
            case COS:
                return Math.cos(a);
            case TG:
                return Math.tan(a);
            case CTG:
                return 1 / Math.tan(a);
        }
        return 0;
    }
}
