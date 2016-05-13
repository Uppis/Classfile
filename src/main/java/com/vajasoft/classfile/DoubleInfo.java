package com.vajasoft.classfile;

public class DoubleInfo extends Constant {
    public static final int TAG = 6;
    private double value;

    DoubleInfo(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
