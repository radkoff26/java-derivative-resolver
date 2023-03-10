package com.radkoff26.calculus.model;

public class Expression {
    private Expression leftOperand;
    private ExpressionValue expressionValue;
    private Expression rightOperand;

    public Expression() {
    }

    public Expression(Expression leftOperand, ExpressionValue expressionValue, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.expressionValue = expressionValue;
        this.rightOperand = rightOperand;
    }

    public Expression(Expression expression) {
        this.expressionValue = new ExpressionValue(expression.expressionValue);
        if (expression.leftOperand != null) {
            this.leftOperand = new Expression(expression.leftOperand);
        }
        if (expression.rightOperand != null) {
            this.rightOperand = new Expression(expression.rightOperand);
        }
    }

    public Expression(ExpressionValue expressionValue) {
        this.expressionValue = expressionValue;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public ExpressionValue getExpressionValue() {
        return expressionValue;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    public void setLeftOperand(Expression leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setExpressionValue(ExpressionValue expressionValue) {
        this.expressionValue = expressionValue;
    }

    public void setRightOperand(Expression rightOperand) {
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        if (expressionValue.isValue()) {
            return expressionValue.getValue();
        }
        StringBuilder sb = new StringBuilder();
        if (expressionValue.getOperation().isFunction()) {
            // In case, when expression represents a function, then it's written in particular way
            sb.append(expressionValue.getOperation().getDefinition());
            if (rightOperand != null) {
                // Two arg function
                sb.append("(");
                sb.append(leftOperand.toString());
                sb.append(", ");
                sb.append(rightOperand.toString());
                sb.append(")");
            } else {
                // One arg function
                sb.append("(");
                sb.append(leftOperand.toString());
                sb.append(")");
            }
        } else {
            // Otherwise, this is not a function but operation
            if (leftOperand == null && getExpressionValue().getOperation() == Operation.SUB) {
                // Unary minus operation
                sb.append("(");
                sb.append(expressionValue.getOperation().getDefinition());
                sb.append(rightOperand.toString());
                sb.append(")");
            } else {
                // Other operations
                sb.append("(");
                sb.append(leftOperand.toString());
                sb.append(" ");
                sb.append(expressionValue.getOperation().getDefinition());
                sb.append(" ");
                sb.append(rightOperand.toString());
                sb.append(")");
            }
        }
        return sb.toString();
    }
}
