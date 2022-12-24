package com.radkoff26.calculus.model;

public enum MathConstants {
    E("e"),
    PI("pi");

    private final String stringValue;

    MathConstants(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
