package com.radkoff26.calculus.model;

public enum RuleParam {
    LEFT("l"),
    LEFT_DERIVATIVE("ld"),
    RIGHT("r"),
    RIGHT_DERIVATIVE("rd");

    private final String definition;

    RuleParam(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }
}
