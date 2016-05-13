package com.vajasoft.classfile;

public class FloatInfo extends Constant {
    public static final int TAG = 4;
    private float value;

    FloatInfo(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
