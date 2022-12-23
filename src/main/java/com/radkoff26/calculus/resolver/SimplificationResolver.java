package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.util.DoubleUtils;

public class SimplificationResolver implements Resolver<Expression> {

    @Override
    public Expression resolve(Expression expression) {
        if (expression.getExpressionValue().isValue()) {
            return expression;
        }
        expression.setLeftOperand(resolve(expression.getLeftOperand()));
        if (expression.getRightOperand() != null) {
            expression.setRightOperand(resolve(expression.getRightOperand()));
        }
        String leftValue = expression.getLeftOperand().getExpressionValue().getValue();
        if (DoubleUtils.isParseableToDouble(leftValue)) {
            double a = Double.parseDouble(leftValue);
            if (expression.getRightOperand() != null) {
                String rightValue = expression.getRightOperand().getExpressionValue().getValue();
                if (DoubleUtils.isParseableToDouble(rightValue)) {
                    double b = Double.parseDouble(rightValue);
                    switch (expression.getExpressionValue().getOperation()) {
                        case ADD:
                            return new Expression(new ExpressionValue(String.valueOf(a + b)));
                        case SUB:
                            return new Expression(new ExpressionValue(String.valueOf(a - b)));
                        case MUL:
                            return new Expression(new ExpressionValue(String.valueOf(a * b)));
                        case DIV:
                            return new Expression(new ExpressionValue(String.valueOf(a / b)));
                        case POW:
                            if (b == 0) {
                                return new Expression(new ExpressionValue("1"));
                            }
                            return new Expression(new ExpressionValue(String.valueOf(Math.pow(a, b))));
                    }
                } else {
                    if (a == 0) {
                        switch (expression.getExpressionValue().getOperation()) {
                            case ADD:
                                return expression.getRightOperand();
                            case SUB:
                                break; // ??? TODO: unary minus
//                                return new Expression(new ExpressionValue(rightValue));
                            case MUL:
                            case DIV:
                            case POW:
                                return new Expression(new ExpressionValue("0"));
                        }
                    }
                }
            } else {
                switch (expression.getExpressionValue().getOperation()) {
                    case LN:
                        if (a == 1) {
                            return new Expression(new ExpressionValue("0"));
                        }
                        return expression;
                }
            }
        } else {
            if (expression.getRightOperand() != null) {
                String rightValue = expression.getRightOperand().getExpressionValue().getValue();
                if (DoubleUtils.isParseableToDouble(rightValue)) {
                    double b = Double.parseDouble(rightValue);
                    if (b == 0) {
                        switch (expression.getExpressionValue().getOperation()) {
                            case ADD:
                            case SUB:
                                return expression.getLeftOperand();
                            case MUL:
                                return new Expression(new ExpressionValue("0"));
                            case DIV:
                                return new Expression(new ExpressionValue(String.valueOf(Double.POSITIVE_INFINITY)));
                            case POW:
                                return new Expression(new ExpressionValue("1"));
                        }
                    }
                }
            }
        }
        return expression;
    }
}
