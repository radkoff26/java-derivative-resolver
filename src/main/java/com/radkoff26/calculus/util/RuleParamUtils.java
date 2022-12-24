package com.radkoff26.calculus.util;

import com.radkoff26.calculus.model.RuleParam;

public class RuleParamUtils {

    private RuleParamUtils() {
    }

    public static RuleParam getParamByDefinition(String definition) {
        RuleParam[] ruleParams = RuleParam.values();
        for (RuleParam ruleParam : ruleParams) {
            if (ruleParam.getDefinition().equals(definition)) {
                return ruleParam;
            }
        }
        return null;
    }
}
