package com.vcredit.framework.fastdfs.exception;

/**
 * Fastdfs通用异常
 *
 * @author dongzhuming
 */
public class FastdfsException extends Exception {

    public FastdfsException() {

    }

    public FastdfsException(Throwable t) {
        super(t);
    }

    public FastdfsException(String message, Throwable t) {
        super(message, t);
    }

}
