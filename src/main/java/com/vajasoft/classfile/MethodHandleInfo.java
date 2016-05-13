package com.vajasoft.classfile;

public class MethodHandleInfo extends Constant implements Comparable<MethodHandleInfo> {
    public static final int TAG = 15;

    private final int refKind;
    private final int refIndex;
    private final Constant[] cp;

    MethodHandleInfo(short refKind, int refIndex, Constant[] cp) {
        this.refKind = refKind;
        this.refIndex = refIndex;
        this.cp = cp;
    }

    public RefInfo getRefInfo() {
        return (RefInfo)cp[refIndex];
    }

    @Override
    public String toString() {
        return String.valueOf(getRefInfo());
    }

    @Override
    public int compareTo(MethodHandleInfo o) {
        return getRefInfo().compareTo(o.getRefInfo());
    }
}
