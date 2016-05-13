package com.vajasoft.classfile;

public class StringInfo extends Constant {
    public static final int TAG = 8;
    private int index;
    private Constant[] cp;

    StringInfo(int index, Constant[] cp) {
        this.index = index;
        this.cp = cp;
    }

    public String getValue() {
        return getInfo().getValue();
    }

    public String toString() {
        return "value=[" + index + "]";
    }

    private Utf8Info getInfo() {
        return (Utf8Info)cp[index];
    }
}
