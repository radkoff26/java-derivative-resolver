package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;

public class ExpressionResolver implements Resolver<String> {
    private static final int MAX_PRIORITY = 2;
    private static ExpressionResolver INSTANCE;

    private ExpressionResolver() {
    }

    public static ExpressionResolver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExpressionResolver();
        }
        return INSTANCE;
    }

    @Override
    public Expression resolve(String s) {
        return build(s, 0, s.length());
    }

    private int getPriority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private Operation getOperation(char op) {
        switch (op) {
            case '+':
                return Operation.ADD;
            case '-':
                return Operation.SUB;
            case '*':
                return Operation.MUL;
            case '/':
                return Operation.DIV;
            default:
                return null;
        }
    }

    private Expression build(String s, int startInclusive, int endExclusive) {
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
                if (s.charAt(cursor) == '(') {
                    folding++;
                } else if (s.charAt(cursor) == ')') {
                    folding--;
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
}
