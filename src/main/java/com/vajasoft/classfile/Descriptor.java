package com.vajasoft.classfile;

import java.util.*;
import java.text.ParsePosition;

public class Descriptor {
    public static List<String> getTypes(NameAndType nt) {
        return getTypes(nt, false);
    }

    public static List<String> getObjectTypes(NameAndType nt) {
        return getTypes(nt, true);
    }

    public static String getFirstType(NameAndType nt) {
        String ret = null;
        String descr = nt.getDescriptor();
        if (descr.length() > 0) {
            ret = getNextType(descr, new ParsePosition(0), false);
        }
        return ret;
    }

    public static String getLastType(NameAndType nt) {
        String ret = null;
        String descr = nt.getDescriptor();
        ParsePosition pos = new ParsePosition(0);
        while (pos.getIndex() < descr.length()) {
            String t = getNextType(descr, pos, false);
            if (t != null)
                ret = t;
        }
        return ret;
    }

    public static String toExternalFormat(NameAndType nt) {
        StringBuffer ret = new StringBuffer();
        List<String> types = getTypes(nt);
        ret.append(Util.toExternalClassName(types.get(types.size() - 1))).append(' ');
        ret.append(nt.getName());
        int tc = types.size();
        if (tc > 1) {
            ret.append("(");
            ret.append(Util.toExternalClassName(types.get(0)));
            for (int j = 1; j < tc - 1; j++)
                ret.append(", " + Util.toExternalClassName((types.get(j))));
            ret.append(")");
        }
        return ret.toString();
    }

    private static List<String> getTypes(NameAndType nt, boolean objectTypesOnly) {
        List<String> ret = new ArrayList<String>();
        String descr = nt.getDescriptor();
        ParsePosition pos = new ParsePosition(0);
        while (pos.getIndex() < descr.length()) {
            String t = getNextType(descr, pos, objectTypesOnly);
            if (t != null)
                ret.add(t);
        }
        return ret;
    }

    private static String getNextType(String descr, ParsePosition pos, boolean objectTypesOnly) {
        String ret = null; //"";

        do {
            char ch = descr.charAt(pos.getIndex());
            pos.setIndex(pos.getIndex() + 1);

            switch (ch) {
            case '(':
            case ')':
                ret = getNextType(descr, pos, objectTypesOnly);
                break;
            case '[':
                if (!objectTypesOnly || descr.charAt(pos.getIndex()) == 'L')
                    ret = getNextType(descr, pos, objectTypesOnly) + "[]";
                else
                    ret = getNextType(descr, pos, objectTypesOnly);
                break;
            case 'B':
                if (!objectTypesOnly)
                    ret = "byte";
                break;
            case 'C':
                if (!objectTypesOnly)
                    ret = "char";
                break;
            case 'D':
                if (!objectTypesOnly)
                    ret = "double";
                break;
            case 'F':
                if (!objectTypesOnly)
                    ret = "float";
                break;
            case 'I':
                if (!objectTypesOnly)
                    ret = "int";
                break;
            case 'J':
                if (!objectTypesOnly)
                    ret = "long";
                break;
            case 'S':
                if (!objectTypesOnly)
                    ret = "short";
                break;
            case 'Z':
                if (!objectTypesOnly)
                    ret = "boolean";
                break;
            case 'L':
                int ix = descr.indexOf(';', pos.getIndex());
                ret = descr.substring(pos.getIndex(), ix);
                pos.setIndex(ix + 1);
                break;
            case 'V':
                if (!objectTypesOnly)
                    ret = "void";
                break;
            }
        } while (pos.getIndex() < descr.length() && ret == null);
        return ret;
    }
}
