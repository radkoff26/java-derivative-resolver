package com.radkoff26.calculus.util;

import com.radkoff26.calculus.model.Operation;

public class OperationUtils {

    private OperationUtils() {
    }

    public static Operation getOperationOrFunction(String operation) {
        Operation[] operations = Operation.values();
        for (Operation op : operations) {
            if (op.getDefinition().equals(operation)) {
                return op;
            }
        }
        return null;
    }

    public static int getPriorityOfOperationOrFunction(String operation) {
        Operation op = getOperationOrFunction(operation);
        if (op == null) {
            return 0;
        }
        return op.getPriority();
    }
}
