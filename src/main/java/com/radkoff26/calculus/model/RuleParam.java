package com.radkoff26.calculus.model;

import java.util.Arrays;

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

    public RuleParam getParamByDefinition(String definition) {
        RuleParam[] ruleParams = RuleParam.values();
        for (RuleParam ruleParam : ruleParams) {
            if (ruleParam.getDefinition().equals(definition)) {
                return ruleParam;
            }
        }
        return null;
    }
}
