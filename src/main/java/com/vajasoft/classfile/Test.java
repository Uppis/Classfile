
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */


package com.vajasoft.classfile;

import java.io.*;
import java.util.Vector;

public final class Test implements Serializable {
    public static final int SOME_CONSTANT = 7;

    private String mString;

    public String stringMethod() throws Exception {
        return mString;
    }

    protected void methodWithArguments(int[] ia, boolean b, Vector v) {
        File f = new File("huuhaa");
    }
}
