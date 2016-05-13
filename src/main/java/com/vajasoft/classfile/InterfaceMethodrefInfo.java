package com.vajasoft.classfile;

public class InterfaceMethodrefInfo extends RefInfo {
    public static final int TAG = 11;

    InterfaceMethodrefInfo(int classIndex, int nameAndTypeIndex, Constant[] cp) {
        super(classIndex, nameAndTypeIndex, cp);
    }

}
