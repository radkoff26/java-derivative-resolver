package com.radkoff26.calculus.model;

public enum Operation {
    ADD(1, "+", false),
    SUB(1, "-", false),
    DIV(2, "/", false),
    MUL(2, "*", false),
    POW(3, "^", false),
    LN(3, "ln", true),
    LOG(3, "log", true),
    SIN(3, "sin", true),
    COS(3, "cos", true),
    TG(3, "tg", true),
    CTG(3, "ctg", true);

    private final int priority;
    private final String definition;
    private final boolean isFunction;

    Operation(int priority, String definition, boolean isFunction) {
        this.priority = priority;
        this.definition = definition;
        this.isFunction = isFunction;
    }

    public int getPriority() {
        return priority;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean isFunction() {
        return isFunction;
    }
}
