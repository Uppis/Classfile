package com.vajasoft.classfile;

public class Constant {

    public static Constant[] getConstantPool(java.io.DataInputStream input) throws java.io.IOException, InvalidClassFileException {
        int cp_count = input.readUnsignedShort();
        Constant[] ret = new Constant[cp_count];

        // First read indexes and simple constants
        for (int i = 1; i < cp_count; i++) {
            int tag = input.readUnsignedByte();
            switch (tag) {
            case Utf8Info.TAG:
                ret[i] = new Utf8Info(input.readUTF());
                break;
            case IntegerInfo.TAG:
                ret[i] = new IntegerInfo(input.readInt());
                break;
            case FloatInfo.TAG:
                ret[i] = new FloatInfo(input.readFloat());
                break;
            case LongInfo.TAG:
                ret[i++] = new LongInfo(input.readLong());
                ret[i] = new Constant();
                break;
            case DoubleInfo.TAG:
                ret[i++] = new DoubleInfo(input.readDouble());
                ret[i] = new Constant();
                break;
            case ClassInfo.TAG:
                ret[i] = new ClassInfo(input.readUnsignedShort(), ret);
                break;
            case StringInfo.TAG:
                ret[i] = new StringInfo(input.readUnsignedShort(), ret);
                break;
            case FieldrefInfo.TAG:
                ret[i] = new FieldrefInfo(input.readUnsignedShort(), input.readUnsignedShort(), ret);
                break;
            case MethodrefInfo.TAG:
                ret[i] = new MethodrefInfo(input.readUnsignedShort(), input.readUnsignedShort(), ret);
                break;
            case InterfaceMethodrefInfo.TAG:
                ret[i] = new InterfaceMethodrefInfo(input.readUnsignedShort(), input.readUnsignedShort(), ret);
                break;
            case NameAndTypeInfo.TAG:
                ret[i] = new NameAndTypeInfo(input.readUnsignedShort(), input.readUnsignedShort(), ret);
                break;
            case MethodHandleInfo.TAG:
                ret[i] = new MethodHandleInfo(input.readByte(), input.readUnsignedShort(), ret);
                break;
            case MethodTypeInfo.TAG:
                ret[i] = new MethodTypeInfo(input.readUnsignedShort(), ret);
                break;
            case InvokeDynamicInfo.TAG:
                ret[i] = new InvokeDynamicInfo(input.readUnsignedShort(), input.readUnsignedShort(), ret);
                break;
            default:
                throw new InvalidClassFileException("Unknown tag: " + tag);
            }
        }

        return ret;
    }

    public String toString() {
        return "dummy";
    }
}
