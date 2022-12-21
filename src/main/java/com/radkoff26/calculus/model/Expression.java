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
        sb.append("(");
        sb.append(leftOperand.toString());
        sb.append(" ");
        sb.append(expressionValue.getOperation().getDefinition());
        sb.append(" ");
        sb.append(rightOperand.toString());
        sb.append(")");
        return sb.toString();
    }
}
