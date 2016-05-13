package com.vajasoft.classfile;

public class InvokeDynamicInfo extends Constant implements Comparable<InvokeDynamicInfo> {
    public static final int TAG = 18;

    private final int bootstrapMethodAttrIndex;
    private final int nameAndTypeIndex;
    private final Constant[] cp;

    InvokeDynamicInfo(int bootstrapMethodAttrIndex, int nameAndTypeIndex, Constant[] cp) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "bootstrap=[" + bootstrapMethodAttrIndex + "], " + "nameAndType=[" + nameAndTypeIndex + "]";
    }

    public String getName() {
        return getNameAndTypeInfo().getName();
    }

    public String getDescriptor() {
        return getNameAndTypeInfo().getDescriptor();
    }

    @Override
    public int compareTo(InvokeDynamicInfo o) {
        return getNameAndTypeInfo().compareTo(o.getNameAndTypeInfo());
    }

    private NameAndTypeInfo getNameAndTypeInfo() {
        return (NameAndTypeInfo)cp[nameAndTypeIndex];
    }
}
