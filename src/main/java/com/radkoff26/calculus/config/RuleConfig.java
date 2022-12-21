package com.radkoff26.calculus.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.radkoff26.calculus.model.Operation;
import com.radkoff26.calculus.model.Rule;
import com.radkoff26.calculus.model.RuleParam;

public class RuleConfig {
    private final Map<Operation, Rule> rules;

    public RuleConfig() throws IOException {
        this.rules = loadRules();
    }

    public Map<Operation, Rule> getRules() {
        return rules;
    }

    private Map<Operation, Rule> loadRules() throws IOException {
        Map<Operation, Rule> rulesMap = new EnumMap<>(Operation.class);
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get("src", "main", "resources", "rules.cfg").toFile()))) {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] lineParams = line.split("=");
                Operation op = Operation.ADD.getOperationByDefinition(lineParams[0]);
                String[] params = lineParams[1].split(",");
                String pattern = lineParams[2];
                rulesMap.put(
                        op,
                        new Rule(
                                Arrays.stream(params)
                                        .map(RuleParam.LEFT::getParamByDefinition)
                                        .collect(Collectors.toList()),
                                pattern
                        )
                );
            }
        }
        return rulesMap;
    }
}
