package com.vajasoft.classfile;

public class Utf8Info extends Constant implements Comparable<Utf8Info> {
    public static final int TAG = 1;

    final String value;

    Utf8Info(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "\"" + value + "\"";
    }

    public int compareTo(Utf8Info o) {
        return value.compareTo(o.value);
    }
}
