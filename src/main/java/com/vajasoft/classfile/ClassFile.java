package com.vajasoft.classfile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ClassFile {
    public static final int MAGIC = 0xCAFEBABE;

    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_SUPER = 0x0020;
    public static final int ACC_SYNCHRONIZED = 0x0020; // ???
    public static final int ACC_VOLATILE = 0x0040;
    public static final int ACC_TRANSIENT = 0x0080;
    public static final int ACC_NATIVE = 0x0100;
    public static final int ACC_INTERFACE = 0x0200;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_STRICT = 0x0800;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ANNOTATION = 0x2000;
    public static final int ACC_ENUM = 0x4000;

    private final int magic;
    private final int versionMajor;
    private final int versionMinor;
    private final Constant[] constantPool;
    private final int accessFlags;
    private final ClassInfo thisClass;
    private final ClassInfo superClass;
    private final ClassInfo[] interfaces;
    private final FieldInfo[] fields;
    private final MethodInfo[] methods;
    private final Attribute[] attributes;
    private String packageName;

//    public static String toInternalClassName(String externalClassName) {
//        return externalClassName.replace('.', '/');
//    }
    public ClassFile(java.io.InputStream input) throws IOException, InvalidClassFileException {
        java.io.DataInputStream in = new java.io.DataInputStream(input);
        magic = in.readInt();
        if (magic != MAGIC) {
            StringWriter buf = new StringWriter();
            PrintWriter writer = new PrintWriter(buf);
            writer.print("Not a valid class file, magic: ");
            writer.printf("Magic: 0x%X\n", magic);
            throw new InvalidClassFileException(writer.toString());
        }
        versionMinor = in.readUnsignedShort();
        versionMajor = in.readUnsignedShort();
        constantPool = Constant.getConstantPool(in);
        accessFlags = in.readUnsignedShort();
        thisClass = (ClassInfo) constantPool[in.readUnsignedShort()];
        superClass = (ClassInfo) constantPool[in.readUnsignedShort()];
        int iface_count = in.readUnsignedShort();
        interfaces = new ClassInfo[iface_count];
        for (int i = 0; i < iface_count; i++) {
            interfaces[i] = (ClassInfo) constantPool[in.readUnsignedShort()];
        }
        int field_count = in.readUnsignedShort();
        fields = new FieldInfo[field_count];
        for (int i = 0; i < field_count; i++) {
            fields[i] = new FieldInfo(in, constantPool);
        }
        int method_count = in.readUnsignedShort();
        methods = new MethodInfo[method_count];
        for (int i = 0; i < method_count; i++) {
            methods[i] = new MethodInfo(in, constantPool);
        }
        int attribute_count = in.readUnsignedShort();
        attributes = new Attribute[attribute_count];
        for (int i = 0; i < attribute_count; i++) {
            attributes[i] = Attribute.getAttribute(in, constantPool);
        }
//        input.close();
    }

    public String getClassName() {
        return Util.toExternalClassName(thisClass.getName());
    }

    public String getSuperClassName() {
        return Util.toExternalClassName(superClass.getName());
    }

    public String[] getInterfaces() {
        List<String> buf = new ArrayList<String>();
        for (ClassInfo ci : interfaces) {
            buf.add(Util.toExternalClassName(ci.getName()));
        }
        String[] ret = new String[buf.size()];
        return buf.toArray(ret);
    }

    public String getClassNameInternal() {
        return thisClass.getName();
    }

    public String getSuperClassNameInternal() {
        return superClass.getName();
    }

    public String getPackageName() {
        if (packageName == null) {
            packageName = "";
            String className = Util.toExternalClassName(thisClass.getName());
            int lastDot = className.lastIndexOf('.');
            if (lastDot >= 0) {
                packageName = className.substring(0, lastDot);
            }
        }
        return packageName;
    }

    public String getClassFileName() {
        return thisClass.getName().replace('/', File.separatorChar) + ".class";
    }

    public boolean hasNatives() {
        for (MethodInfo m : methods) {
            if ((m.getAccessFlags() & ACC_NATIVE) != 0) {
                return true;
            }
        }
        return false;
    }

    public String[] getReferences() {
        Set<String> refs = new TreeSet<String>(); // Sorted according to the natural order of elements (see String compareTo)
        for (Constant c : constantPool) {
            if (c instanceof ClassInfo) {
                refs.add(((ClassInfo) c).getName());
            } else if (c instanceof NameAndTypeInfo) {
                List<String> types = Descriptor.getObjectTypes((NameAndTypeInfo) c);
                for (String type : types) {
                    refs.add(type);
                }
            } else if (c instanceof RefInfo) {
                RefInfo ri = (RefInfo) c;
                String className = ri.getClassInfo().getName();
                NameAndTypeInfo nti = ri.getNameAndTypeInfo();
                refs.add(className + ' ' + nti.getName() + ' ' + nti.getDescriptor());
            }
        }
        String[] ret = new String[refs.size()];
        refs.toArray(ret);
        return ret;
    }

    public Reference[] getReferences2() {
        Set<Reference> refs = getReferencesAsSet();
        Reference[] ret = new Reference[refs.size()];
        refs.toArray(ret);
        return ret;
    }

    public Set<Reference> getReferencesAsSet() {
        Set<Reference> refs = new TreeSet<Reference>(); // Sorted according to the natural order of elements (see String compareTo)
        for (Constant c : constantPool) {
            if (c instanceof ClassInfo) {
                refs.add(new Reference(((ClassInfo) c).getName()));
            } else if (c instanceof NameAndTypeInfo) {
                List<String> types = Descriptor.getObjectTypes((NameAndTypeInfo) c);
                for (String type : types) {
                    refs.add(new Reference(type));
                }
            } else if (c instanceof RefInfo) {
                RefInfo ri = (RefInfo) c;
                String className = ri.getClassInfo().getName();
                NameAndTypeInfo nti = ri.getNameAndTypeInfo();
                refs.add(new Reference(className, nti.getName(), nti.getDescriptor()));
            }
        }
        return refs;
    }

    public FieldInfo[] getFields() {
        return (FieldInfo[]) fields.clone();
    }

    public MethodInfo[] getMethods() {
        MethodInfo[] ret = (MethodInfo[]) methods.clone();
        Arrays.sort(ret);
        return ret;
    }

    public void dump(java.io.PrintStream out) {
        dump(new java.io.PrintWriter(out));
    }

    public void dump(java.io.PrintWriter out) {
        out.printf("Magic: 0x%X\n", magic);
        out.printf("Version: %d.%d (%s)\n", versionMajor, versionMinor, getJavaVersion());
        out.println("Constant pool:");
        for (int i = 1; i < constantPool.length; i++) {
            String className = constantPool[i].getClass().getName();
            out.printf("%-5d %-22s %s\n", i, className.substring(className.lastIndexOf('.') + 1), constantPool[i]);
        }
        out.println();
        out.println("Code:");
        out.println();
        out.printf("package %s;\n", getPackageName());
        out.println();
        out.printf("%s class %s", Util.formatAccessFlags(accessFlags & ~ACC_SUPER), formatClassName(thisClass.getName()));
        String supClass = formatClassName(superClass.getName());
        if (!supClass.equals("Object")) {
            out.print(" extends " + supClass);
        }
        if (interfaces.length > 0) {
            out.print(" implements " + formatClassName(interfaces[0].getName()));
            for (int i = 1; i < interfaces.length; i++) {
                out.print(", " + formatClassName(interfaces[i].getName()));
            }
            out.println();
        }
        out.println(" {");
        for (FieldInfo f : fields) {
            out.printf("    ").printf("%s", f).println(";");
        }
        if (methods.length > 0) {
            out.println();
            StringBuffer buf = new StringBuffer(256);
            for (MethodInfo m : methods) {
                buf.setLength(0);
                buf.append("    ").append(m).append(';');
                Util.fill(buf, ' ', 80);
                buf.append("// ").append(m.getDescriptor());
                out.println(buf);
            }
        }
        out.println("}");
        out.flush();
    }

    @Override
    public String toString() {
        return getClassFileName();
    }

    private String formatClassName(String s) {
        String ret = Util.toExternalClassName(s);
        int lastDot = ret.lastIndexOf('.');
        if (lastDot >= 0 && ret.regionMatches(0, getPackageName(), 0, getPackageName().length())) {
            ret = ret.substring(lastDot + 1);
        }
        return ret;
    }

    private String getJavaVersion() {
        String ret = "Java ";
        switch (versionMajor) {
            case 45:
                ret += "1.x";
                break;
            case 46:
                ret += "1.2";
                break;
            case 47:
                ret += "1.3";
                break;
            case 48:
                ret += "1.4";
                break;
            case 49:
                ret += "1.5";
                break;
            case 50:
                ret += "1.6";
                break;
            case 51:
                ret += "1.7";
                break;
            case 52:
                ret += "1.8";
                break;
            default:
                ret += "?.?";
        }
        return ret;
    }

    public static void main(String[] args) throws IOException, InvalidClassFileException {
        if (args.length == 0) {
            System.out.println("Usage: java ClassFile <classfile>");
            return;
        }

        InputStream is = new java.io.FileInputStream(args[0]);
        ClassFile cf = new ClassFile(is);
        cf.dump(System.out);
        System.out.println("\nReferences:");
        for (Reference ref : cf.getReferences2()) {
            System.out.println(ref);
        }
        is.close();
    }
}
