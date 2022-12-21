package com.radkoff26.calculus.model;

public enum Operation {
    ADD(1, "+"),
    SUB(2, "-"),
    DIV(3, "/"),
    MUL(4, "*"),
    POW(5, "^");

    private final int priority;
    private final String definition;

    Operation(int priority, String definition) {
        this.priority = priority;
        this.definition = definition;
    }

    public int getPriority() {
        return priority;
    }

    public String getDefinition() {
        return definition;
    }

    public Operation getOperationByDefinition(String definition) {
        Operation[] operations = Operation.values();
        for (Operation operation : operations) {
            if (operation.getDefinition().equals(definition)) {
                return operation;
            }
        }
        return null;
    }
}
