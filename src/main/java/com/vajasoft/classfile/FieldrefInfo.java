package com.vajasoft.classfile;

public class FieldrefInfo extends RefInfo {
    public static final int TAG = 9;

    FieldrefInfo(int classIndex, int nameAndTypeIndex, Constant[] cp) {
        super(classIndex, nameAndTypeIndex, cp);
    }

}
