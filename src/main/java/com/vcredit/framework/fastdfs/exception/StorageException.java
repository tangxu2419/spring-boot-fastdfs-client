package com.vcredit.framework.fastdfs.exception;

/**
 * @author Dong Zhuming
 */
public class StorageException extends Exception {

    protected StorageException(String message) {
        super(message);
    }

    protected StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
