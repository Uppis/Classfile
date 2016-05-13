package com.vajasoft.classfile;

public class IntegerInfo extends Constant {
    public static final int TAG = 3;
    private int value;

    IntegerInfo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public String toString() {
        return String.valueOf(value);
    }
}
