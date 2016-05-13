package com.vajasoft.classfile;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Util {
    static final String JAVA_LANG = "java.lang";

    static String toExternalClassName(String internalClassName) {
        String ret = internalClassName.replace('/', '.');
        int lastDot = ret.lastIndexOf('.');
        if (lastDot >= 0 &&
            (ret.regionMatches(0, JAVA_LANG, 0, JAVA_LANG.length())))// ||
//             ret.regionMatches(0, getPackageName(), 0, getPackageName().length())))
            ret = ret.substring(lastDot + 1);
        return ret;
    }

    static List getAccessFlags(int af) {
        ArrayList<String> ret = new ArrayList<String>();
        if ((af & ClassFile.ACC_PUBLIC) != 0)
            ret.add("public");
        if ((af & ClassFile.ACC_PRIVATE) != 0)
            ret.add("private");
        if ((af & ClassFile.ACC_PROTECTED) != 0)
            ret.add("protected");
        if ((af & ClassFile.ACC_STATIC) != 0)
            ret.add("static");
        if ((af & ClassFile.ACC_FINAL) != 0)
            ret.add("final");
        if ((af & ClassFile.ACC_SUPER) != 0)
            ret.add("super");
        if ((af & ClassFile.ACC_SYNCHRONIZED) != 0)
            ret.add("synchronized");
        if ((af & ClassFile.ACC_VOLATILE) != 0)
            ret.add("volatile");
        if ((af & ClassFile.ACC_TRANSIENT) != 0)
            ret.add("transient");
        if ((af & ClassFile.ACC_NATIVE) != 0)
            ret.add("native");
        if ((af & ClassFile.ACC_INTERFACE) != 0)
            ret.add("interface");
        if ((af & ClassFile.ACC_ABSTRACT) != 0)
            ret.add("abstract");
        if ((af & ClassFile.ACC_STRICT) != 0)
            ret.add("strict");
        if ((af & ClassFile.ACC_ANNOTATION) != 0)
            ret.add("annotation");
        if ((af & ClassFile.ACC_ENUM) != 0)
            ret.add("enum");
        return ret;
    }

    public static String formatAccessFlags(int af) {
        StringBuffer buf = new StringBuffer();
        List flags = getAccessFlags(af);
        Iterator iter = flags.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next());
            if (iter.hasNext())
                buf.append(' ');
        }
        return buf.toString();
    }

    static void fill(StringBuffer b, char c, int toLen) {
        for (int i = b.length(); i < toLen; i++)
            b.append(c);
    }

    private Util() {}
}
