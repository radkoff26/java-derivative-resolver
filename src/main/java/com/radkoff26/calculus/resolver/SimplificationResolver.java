package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.util.DoubleUtils;

// Class which simplifies an expression passed as a parameter
// This simplification process is not complete though (many things are not simplifiable yet)
public class SimplificationResolver implements Resolver<Expression> {

    @Override
    public Expression resolve(Expression expression) {
        // If current expression is null or a value or is representing a unary minus operation, then it's being returned
        if (expression == null || expression.getExpressionValue().isValue() || expression.getLeftOperand() == null) {
            return expression;
        }
        // Simplify the left operand
        expression.setLeftOperand(resolve(expression.getLeftOperand()));
        // Simplify the right operand if it's given
        if (expression.getRightOperand() != null) {
            expression.setRightOperand(resolve(expression.getRightOperand()));
        }
        // Apply simplification to the expression
        String leftValue = expression.getLeftOperand().getExpressionValue().getValue();
        // If the left operand represents a value which is not a variable
        if (DoubleUtils.isParseableToDouble(leftValue)) {
            // Then parse this value, so that it's possible to simplify expression
            double a = Double.parseDouble(leftValue);
            // If the right operand exist
            if (expression.getRightOperand() != null) {
                String rightValue = expression.getRightOperand().getExpressionValue().getValue();
                // And if it's a numeric value
                if (DoubleUtils.isParseableToDouble(rightValue)) {
                    // Then it's parsed
                    double b = Double.parseDouble(rightValue);
                    // Here expression is being simplified
                    // Considering that two operands are numeric, it's easy two execute the given operation
                    switch (expression.getExpressionValue().getOperation()) {
                        case ADD:
                            return new Expression(new ExpressionValue(String.valueOf(a + b)));
                        case SUB:
                            return new Expression(new ExpressionValue(String.valueOf(a - b)));
                        case MUL:
                            return new Expression(new ExpressionValue(String.valueOf(a * b)));
                        case POW:
                            if (b == 0) {
                                // It's made to avoid excessive float value
                                return new Expression(new ExpressionValue("1"));
                            }
                            return new Expression(new ExpressionValue(String.valueOf(Math.pow(a, b))));
                        case LOG:
                            if (b == 1) {
                                // Take away float value
                                return new Expression(new ExpressionValue("1"));
                            }
                            return expression;
                        default:
                            // DIV case also goes to default since simplification does not resolve fractions
                            return expression;
                    }
                } else {
                    // In other cases, the right operand is a variable or some other operational expression
                    if (a == 0) {
                        switch (expression.getExpressionValue().getOperation()) {
                            case ADD:
                                // Here is the left operand is being eliminated
                                // Example: 0 + expression = expression
                                return expression.getRightOperand();
                            case SUB:
                                // In this case the given expression will result in unary minus expression
                                // Example: 0 - expression = -expression
                                return new Expression(null, new ExpressionValue(Operation.SUB), expression.getRightOperand());
                            case MUL:
                            case DIV:
                            case POW:
                                // In these cases it's quite obvious that the expression will result in zero
                                return new Expression(new ExpressionValue("0"));
                            default:
                                return expression;
                        }
                    }
                }
            } else {
                // Otherwise, when the right operand does not exist, then expression is performed by one arg function
                switch (expression.getExpressionValue().getOperation()) {
                    case LN:
                        if (a == 1) {
                            // Here is the elimination of float value as well
                            return new Expression(new ExpressionValue("0"));
                        }
                        return expression;
                    default:
                        return expression;
                }
            }
        } else {
            // In case, when the left operand is a variable or a non-parseable expression
            // It's necessary to check the right operand for existence
            if (expression.getRightOperand() != null) {
                String rightValue = expression.getRightOperand().getExpressionValue().getValue();
                // And then check for value represented by this operand
                if (DoubleUtils.isParseableToDouble(rightValue)) {
                    // If it's a value, parse it
                    double b = Double.parseDouble(rightValue);
                    // In this case it's necessary to check if the right operand is represented by zero
                    if (b == 0) {
                        // Examples below
                        switch (expression.getExpressionValue().getOperation()) {
                            case ADD:
                            case SUB:
                                // expression +(-) 0 = expression
                                return expression.getLeftOperand();
                            case MUL:
                                // expression * 0 = 0
                                return new Expression(new ExpressionValue("0"));
                            case DIV:
                                // expression / 0 = Infinity (not in each case though)
                                // Here is no uncertainty considered
                                return new Expression(new ExpressionValue(String.valueOf(Double.POSITIVE_INFINITY)));
                            case POW:
                                // expression ^ 0 = 1
                                // Also no uncertainty is considered
                                return new Expression(new ExpressionValue("1"));
                            default:
                                return expression;
                        }
                    }
                }
            }
        }
        return expression;
    }
}
