package com.vajasoft.classfile;

public class MethodrefInfo extends RefInfo {
    public static final int TAG = 10;

    MethodrefInfo(int classIndex, int nameAndTypeIndex, Constant[] cp) {
        super(classIndex, nameAndTypeIndex, cp);
    }
}
