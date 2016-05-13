package com.vajasoft.classfile;

public class NameAndTypeInfo extends Constant implements NameAndType, Comparable<NameAndTypeInfo> {
    public static final int TAG = 12;

    private int nameIndex;
    private int descriptorIndex;
    private Constant[] cp;

    NameAndTypeInfo(int nameIndex, int descriptorIndex, Constant[] cp) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.cp = cp;
    }

    public String toString() {
        return "name=[" + nameIndex + "], " + "descr=[" + descriptorIndex + "]";
    }

    public String getName() {
        return ((Utf8Info)cp[nameIndex]).getValue();
    }

    public String getDescriptor() {
        return ((Utf8Info)cp[descriptorIndex]).getValue();
    }

    public int compareTo(NameAndTypeInfo o) {
        int ret = getName().compareTo(o.getName());
        return ret != 0 ? ret : getDescriptor().compareTo(o.getDescriptor());
    }
}
