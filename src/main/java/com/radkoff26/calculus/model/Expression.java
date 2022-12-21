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
}
