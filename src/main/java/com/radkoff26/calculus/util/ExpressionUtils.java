package com.radkoff26.calculus.util;

import com.radkoff26.calculus.exception.ExpressionParseException;
import com.radkoff26.calculus.model.Expression;
import com.radkoff26.calculus.model.ExpressionValue;
import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.resolver.ExpressionResolver;
import com.radkoff26.calculus.resolver.Resolver;

public class ExpressionUtils {
    public static Expression parseFunction(String expression, int startInclusive, int endExclusive) throws ExpressionParseException {
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
            Operation operation = Operation.ADD.getOperationByDefinition(function.toString());
            if (operation == null) {
                throw new ExpressionParseException("Unknown function is parsed!");
            }
            result.setExpressionValue(new ExpressionValue(operation));
        } else {
            throw new ExpressionParseException("Given expression is not a function!");
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
                throw new ExpressionParseException("Given function has no arguments!");
            }
            Resolver<String> resolver = new ExpressionResolver();
            result.setLeftOperand(resolver.resolve(args[0]));
            if (args.length > 1) {
                result.setRightOperand(resolver.resolve(args[1]));
            }
        } else {
            throw new ExpressionParseException("Given expression is not a function!");
        }
        boolean hasOperations = false;
        while (i < endExclusive) {
            Operation operation = Operation.ADD.getOperationByDefinition(String.valueOf(expression.charAt(i)));
            if (operation != null) {
                hasOperations = true;
                break;
            }
            i++;
        }
        if (hasOperations) {
            throw new ExpressionParseException("Given expression contains not only function!");
        }
        return result;
    }
}
