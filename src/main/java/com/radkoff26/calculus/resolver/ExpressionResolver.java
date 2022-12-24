package com.radkoff26.calculus.resolver;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.util.ExpressionUtils;
import com.radkoff26.calculus.util.OperationUtils;

// Expression resolver is a class which performs translation of math expression from string into expression tree
public class ExpressionResolver implements Resolver<String> {
    private static final int MAX_PRIORITY = 3;

    @Override
    public Expression resolve(String s) {
        // Eliminate all the whitespaces from the expressions
        s = s.replaceAll("\\s+", "").toLowerCase();
        return build(s, 0, s.length());
    }

    private Expression build(String s, int startInclusive, int endExclusive) {
        int folding = 0;
        int minPriorityOperationIndex = -1;
        int minPriority = Integer.MAX_VALUE;
        int cursor = startInclusive;
        // Calculate minimal priority operation and find its index
        while (folding >= 0 && cursor < endExclusive) {
            int priority = OperationUtils.getPriorityOfOperationOrFunction(String.valueOf(s.charAt(cursor)));
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
        Expression function = ExpressionUtils.parseFunction(s, startInclusive, endExclusive);
        if (function != null) {
            // Check if whole expression is function and return it if so
            return function;
        }
        // Otherwise, keep dividing expression
        // If current expression does not appear to be a value, then
        if (minPriorityOperationIndex == -1) {
            return new Expression(
                    new ExpressionValue(
                            s.substring(startInclusive, endExclusive)
                                    .replace("(", "")
                                    .replace(")", "")
                                    .trim()
                    )
            );
        }
        // Otherwise, build expressions out of left and right expressions of the current one
        Expression leftOperand = build(s, startInclusive, minPriorityOperationIndex);
        Expression rightOperand = build(s, minPriorityOperationIndex + 1, endExclusive);
        return new Expression(
                leftOperand,
                new ExpressionValue(OperationUtils.getOperationOrFunction(String.valueOf(s.charAt(minPriorityOperationIndex)))),
                rightOperand
        );
    }
}
