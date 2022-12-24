package com.radkoff26.calculus.util;

import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;

public class ExpressionUtils {

    private ExpressionUtils() {
    }

    public static Expression parseFunction(String expression, int startInclusive, int endExclusive) {
        int i = startInclusive;
        Expression result = new Expression();
        while (expression.charAt(i) == '(') {
            i++;
        }
        if (expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z') {
            StringBuilder function = new StringBuilder();
            while (i < endExclusive && expression.charAt(i) >= 'a' && expression.charAt(i) <= 'z') {
                function.append(expression.charAt(i));
                i++;
            }
            Operation operation = OperationUtils.getOperationOrFunction(function.toString());
            if (operation == null) {
                return null;
            }
            result.setExpressionValue(new ExpressionValue(operation));
        } else {
            return null;
        }
        if (expression.charAt(i) == '(' && i < endExclusive) {
            int folding = 1;
            int start = i + 1;
            while (folding > 0) {
                i++;
                if (expression.charAt(i) == ')') {
                    folding--;
                } else if (expression.charAt(i) == '(') {
                    folding++;
                }
            }
            String[] args = expression.substring(start, i).split(",");
            if (args.length == 0) {
                return null;
            }
            Resolver<String> resolver = new ExpressionResolver();
            result.setLeftOperand(resolver.resolve(args[0]));
            if (args.length > 1) {
                result.setRightOperand(resolver.resolve(args[1]));
            }
        } else {
            return null;
        }
        boolean hasOperations = false;
        while (i < endExclusive) {
            Operation operation = OperationUtils.getOperationOrFunction(String.valueOf(expression.charAt(i)));
            if (operation != null) {
                hasOperations = true;
                break;
            }
            i++;
        }
        if (hasOperations) {
            return null;
        }
        return result;
    }
}
