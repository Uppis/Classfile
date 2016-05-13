package com.vajasoft.classfile;

public abstract class Attribute {

    public static Attribute getAttribute(java.io.DataInputStream input, Constant[] constantPool) throws java.io.IOException {
        Attribute ret = null;
        int nameIndex = input.readUnsignedShort();
        int length = input.readInt();
        Utf8Info name = (Utf8Info)constantPool[nameIndex];
/*        if ("Code".equals(name.value))
        	ret = new CodeAttribute(input);
        else
*/
        	input.skipBytes(length);
       	return ret;
    }
}
