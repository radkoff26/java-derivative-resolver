package com.radkoff26.calculus.calculator;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.Operation;

public class ExpressionCalculator {
    private final String variable;

    public ExpressionCalculator(String variable) {
        this.variable = variable;
    }

    public double calculateExpression(Expression expression, double valueOfVariable) {
        if (expression == null) {
            return 0;
        }
        if (expression.getExpressionValue().isValue()) {
            if (expression.getExpressionValue().getValue().equals(variable)) {
                return valueOfVariable;
            }
            if (expression.getExpressionValue().getValue().equals("e")) {
                return Math.E;
            }
            if (expression.getExpressionValue().getValue().equals("pi")) {
                return Math.PI;
            }
            return Double.parseDouble(expression.getExpressionValue().getValue());
        }
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
