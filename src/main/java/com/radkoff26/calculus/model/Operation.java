package com.radkoff26.calculus.model;

public enum Operation {
    ADD(1),
    SUB(2),
    DIV(3),
    MUL(4),
    POW(5);

    private final int priority;

    Operation(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
