package com.vajasoft.classfile;

public class LongInfo extends Constant {
    public static final int TAG = 5;
    private long value;

    LongInfo(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
