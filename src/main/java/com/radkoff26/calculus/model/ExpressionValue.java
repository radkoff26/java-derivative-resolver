package com.radkoff26.calculus.model;

public class ExpressionValue {
    private Operation operation;
    private String value;

    public ExpressionValue(Operation operation) {
        this.operation = operation;
    }

    public ExpressionValue(String value) {
        this.value = value;
    }

    public boolean isOperation() {
        return operation != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }
}
