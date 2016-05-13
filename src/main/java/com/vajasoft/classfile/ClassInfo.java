package com.vajasoft.classfile;

public class ClassInfo extends Constant implements Comparable<ClassInfo> {
    public static final int TAG = 7;

    private int index;
    private Constant[] cp;

    ClassInfo(int index, Constant[] cp) {
        this.index = index;
        this.cp = cp;
    }

    public String getName() {
        return getNameInfo().getValue();
    }

    public String toString() {
        return "class=[" + index + "]";
    }

    public int compareTo(ClassInfo o) {
        return getNameInfo().compareTo(o.getNameInfo());
    }

    private Utf8Info getNameInfo() {
        return (Utf8Info)cp[index];
    }
}
