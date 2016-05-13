package com.vajasoft.classfile;

public class MethodTypeInfo extends Constant implements Comparable<MethodTypeInfo> {
    public static final int TAG = 16;

    private int index;
    private Constant[] cp;

    MethodTypeInfo(int index, Constant[] cp) {
        this.index = index;
        this.cp = cp;
    }

    public String getName() {
        return getNameInfo().getValue();
    }

    public String toString() {
        return "class=[" + index + "]";
    }

    public int compareTo(MethodTypeInfo o) {
        return getNameInfo().compareTo(o.getNameInfo());
    }

    private Utf8Info getNameInfo() {
        return (Utf8Info)cp[index];
    }
}
