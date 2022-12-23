package com.radkoff26.calculus.model;

import com.radkoff26.calculus.util.DoubleUtils;

public class ExpressionValue {
    private Operation operation;
    private String value;
    private boolean isVariable;

    public ExpressionValue(ExpressionValue expressionValue) {
        if (expressionValue.isValue()) {
            this.value = expressionValue.value;
            if (!DoubleUtils.isParseableToDouble(value) && !value.equals("e") && !value.equals("pi")) {
                isVariable = true;
            }
        } else {
            this.operation = expressionValue.operation;
        }
    }

    public ExpressionValue(Operation operation) {
        this.operation = operation;
    }

    public ExpressionValue(String value) {
        this.value = value;
        if (!DoubleUtils.isParseableToDouble(value) && !value.equals("e") && !value.equals("pi")) {
            isVariable = true;
        }
    }

    public boolean isOperation() {
        return operation != null;
    }

    public boolean isValue() {
        return value != null;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setValue(String value) {
        this.value = value;
        if (!DoubleUtils.isParseableToDouble(value) && !value.equals("e") && !value.equals("pi")) {
            isVariable = true;
        }
    }

    public boolean isVariable() {
        return isVariable;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getValue() {
        return value;
    }
}
