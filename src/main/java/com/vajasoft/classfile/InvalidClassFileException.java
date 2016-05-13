package com.vajasoft.classfile;

import java.io.IOException;

/**
 *
 * @author Z705692
 */
public class InvalidClassFileException extends IOException {

    public InvalidClassFileException(String message) {
        super(message);
    }

    public InvalidClassFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
