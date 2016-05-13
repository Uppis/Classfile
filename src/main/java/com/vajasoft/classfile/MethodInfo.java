package com.vajasoft.classfile;

import java.util.List;

public class MethodInfo implements Member, Comparable<MethodInfo> {
    private int accessFlags;
    private String name;
    private String descr;
    private Attribute[] attributes;

    public MethodInfo(java.io.DataInputStream input, Constant[] constantPool) throws java.io.IOException {
        accessFlags = input.readUnsignedShort();
        int nameIndex = input.readUnsignedShort();
        int descriptorIndex = input.readUnsignedShort();
        name = ((Utf8Info)constantPool[nameIndex]).getValue();
        descr = ((Utf8Info)constantPool[descriptorIndex]).getValue();
        int attr_count = input.readUnsignedShort();
        attributes = new Attribute[attr_count];
        for (int i = 0; i < attr_count; i++)
            attributes[i] = Attribute.getAttribute(input, constantPool);
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descr;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(256);
        String af = Util.formatAccessFlags(accessFlags);
        if (af.length() > 0)
            buf.append(af).append(' ');
        List<String> types = Descriptor.getTypes(this);
        buf.append(Util.toExternalClassName(types.get(types.size() - 1))).append(' ');
        buf.append(getName());
        buf.append("(");
        int tc = types.size();
        if (tc > 1) {
            buf.append(Util.toExternalClassName(types.get(0)));
            for (int j = 1; j < tc - 1; j++)
                buf.append(", " + Util.toExternalClassName((types.get(j))));
        }
        buf.append(")");
        return buf.toString();
    }

    public int compareTo(MethodInfo o) {
        int ret = o.accessFlags > accessFlags ? 1 : (o.accessFlags < accessFlags ? -1 : 0);
        if (ret == 0) {
            ret = getName().compareTo(o.getName());
            if (ret == 0) {
                ret = getDescriptor().compareTo(o.getDescriptor());
            }
        }
        return ret;
    }
}
