package com.radkoff26.calculus.exception;

public class ExpressionParseException extends Exception {

    public ExpressionParseException() {
    }

    public ExpressionParseException(String message) {
        super(message);
    }

    public ExpressionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionParseException(Throwable cause) {
        super(cause);
    }
}
