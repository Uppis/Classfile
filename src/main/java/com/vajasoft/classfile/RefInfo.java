package com.vajasoft.classfile;

public abstract class RefInfo extends Constant implements Comparable<RefInfo> {
    private int classIndex;
    private int nameAndTypeIndex;
    private Constant[] cp;

    RefInfo(int classIndex, int nameAndTypeIndex, Constant[] cp) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
        this.cp = cp;
    }

    public ClassInfo getClassInfo() {
        return (ClassInfo)cp[classIndex];
    }

    public NameAndTypeInfo getNameAndTypeInfo() {
        return (NameAndTypeInfo)cp[nameAndTypeIndex];
    }

    public String toString() {
        return String.valueOf(getClassInfo()) + ", " + String.valueOf(getNameAndTypeInfo());
    }

    public int compareTo(RefInfo o) {
        int ret = getClassInfo().compareTo(o.getClassInfo());
        return ret != 0 ? ret : getNameAndTypeInfo().compareTo(o.getNameAndTypeInfo());
    }
}
