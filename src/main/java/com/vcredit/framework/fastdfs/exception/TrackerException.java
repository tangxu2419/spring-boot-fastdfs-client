package com.vcredit.framework.fastdfs.exception;

/**
 * @author Dong Zhuming
 */
public class TrackerException extends Exception {

    protected TrackerException(String message) {
        super(message);
    }

    protected TrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}
