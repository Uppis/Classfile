package com.vajasoft.classfile;

public class Reference implements Comparable<Reference> {
    private String className;
    private String memberName;
    private String memberType;
    private transient volatile int hashCode;

    public Reference(String classname) {
        this(new String[]{classname});
    }

    public Reference(String classname, String membername) {
        this(new String[]{classname, membername});
    }

    public Reference(String classname, String membername, String membertype) {
        this(new String[]{classname, membername, membertype});
    }

    public Reference(String[] refParts) {
        if (refParts.length == 0 || refParts[0] == null) {
            throw new IllegalArgumentException("Class name missing");
        }
        className = refParts[0].replace('.', '/');
        if (refParts.length > 1) {
            this.memberName = refParts[1];
        }
        if (refParts.length > 2) {
            this.memberType = refParts[2];
        }
        if (memberName == null && memberType != null) {
            throw new IllegalArgumentException("Member name missing ");
        }
    }

    public String getClassName() {
        return className;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberType() {
        return memberType;
    }

    public boolean equals(Object other) {
        boolean ret = (other == this);
        if (ret == false && other instanceof Reference) {
            Reference o = (Reference)other;
            ret = className.equals(o.className) &&
                  (memberName == null && o.memberName == null || memberName.equals(o.memberName)) &&
                  (memberType == null && o.memberType == null || memberType.equals(o.memberType))
                  ;
        }
        return ret;
    }

    public int hashCode() {
        if (hashCode == 0) {
            int hc = 17;
            hc = 37 * hc + className.hashCode();
            if (memberName != null) {
                hc = 37 * hc + memberName.hashCode();
                if (memberType != null) {
                    hc = 37 * hc + memberType.hashCode();
                }
            }
            hashCode = hc;
        }
        return hashCode;
    }

    public int compareTo(Reference o) {
        int ret = className.compareTo(o.className);
        if (ret == 0) {
            if (memberName != null && o.memberName != null) {
                ret = memberName.compareTo(o.memberName);
                if (ret == 0) {
                    if (memberType != null && o.memberType != null) {
                        ret = memberType.compareTo(o.memberType);
                    } else if (memberType != null) {
                        ret = 1;
                    }
                    else if (o.memberType != null) {
                        ret = -1;
                    }
                }
            } else if (memberName != null) {
                ret = 1;
            }
            else if (o.memberName != null) {
                ret = -1;
            }
        }
        return ret;
    }

    public boolean matchesTo(Reference filter) {
        boolean ret = false;
//        if (className.startsWith(filter.className)) {
//            if (filter.memberName == null)
//                ret = true;
//            else if (className.equals(filter.className)) {
//                if (memberName.startsWith(filter.memberName)) {
//                    if (filter.memberType == null) {
//                        ret = true;
//                    } else if (memberName.equals(filter.memberName)) {
//                        ret = memberType.equals(filter.memberType);
//                    }
//                }
//            }
//        }

//        if (filter.memberType == null) {
//            if (filter.memberName == null) {
//                ret = className.startsWith(filter.className);
//            } else if (memberName.startsWith(filter.memberName)) {
//                ret = className.equals(filter.className);
//            }
//        } else {
//            ret = equals(filter);
//        }

        if (filter.memberType != null) {
            ret = equals(filter);
        } else if (filter.memberName != null) {
            ret = memberName != null && memberName.startsWith(filter.memberName) && className.equals(filter.className);
        } else {
            ret = className.startsWith(filter.className);
        }
        return ret;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(className);
        if (memberName != null) {
            buf.append('.').append(memberName);
            if (memberType != null) {
                buf.append(' ').append(memberType);
            }
        }
        return buf.toString();
    }
}
