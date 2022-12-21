package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;

public class StringExpressionParser {
    private static final int MAX_PRIORITY = 2;

    private static int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return 0;
    }

    private static Operation getOperation(char op) {
        switch (op) {
            case '+':
                return Operation.ADD;
            case '-':
                return Operation.SUB;
            case '*':
                return Operation.MUL;
            case '/':
                return Operation.DIV;
        }
        return null;
    }

    public static double parseExpression(String ex) {
        return calculate(build(ex, 0, ex.length()));
    }

    private static double calculate(Expression expression) {
        if (expression.getExpressionValue().isOperation()) {
            return calculateOperation(calculate(expression.getLeftOperand()), expression.getExpressionValue().getOperation(), calculate(expression.getRightOperand()));
        }
        return Double.parseDouble(expression.getExpressionValue().getValue());
    }

    private static Expression build(String s, int startInclusive, int endExclusive) {
        int folding = 0;
        int minPriorityOperationIndex = -1;
        int minPriority = Integer.MAX_VALUE;
        int cursor = startInclusive;
        while (folding >= 0 && cursor < endExclusive) {
            int priority = getPriority(s.charAt(cursor));
            if (priority != 0) {
                priority += folding * MAX_PRIORITY;
                if (priority < minPriority) {
                    minPriority = priority;
                    minPriorityOperationIndex = cursor;
                }
            } else {
                switch (s.charAt(cursor)) {
                    case '(':
                        folding++;
                        break;
                    case ')':
                        folding--;
                        break;
                }
            }
            cursor++;
        }
        if (minPriority == Integer.MAX_VALUE) {
            return new Expression(
                    new ExpressionValue(
                            s.substring(startInclusive, endExclusive)
                            .replace("(", "")
                            .replace(")", "")
                            .trim()
                    )
            );
        }
        Expression leftOperand = build(s, startInclusive, minPriorityOperationIndex);
        Expression rightOperand = build(s, minPriorityOperationIndex + 1, endExclusive);
        return new Expression(leftOperand, new ExpressionValue(getOperation(s.charAt(minPriorityOperationIndex))), rightOperand);
    }

    private static double calculateOperation(double leftOperand, Operation op, double rightOperand) {
        switch (op) {
            case ADD:
                return leftOperand + rightOperand;
            case SUB:
                return leftOperand - rightOperand;
            case MUL:
                return leftOperand * rightOperand;
            case DIV:
                return leftOperand / rightOperand;
        }
        return 0;
    }
}
