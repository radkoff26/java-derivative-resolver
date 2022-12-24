package com.radkoff26.calculus.util;

public class DoubleUtils {

    private DoubleUtils() {
    }

    public static boolean isParseableToDouble(String s) {
        if (s == null) {
            return false;
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
