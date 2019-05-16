package com.vcredit.framework.fastdfs.exception;

/**
 * @author tangxu
 */
public class InvokeCommandException extends FastdfsException {

    public InvokeCommandException() {
    }

    public InvokeCommandException(Throwable t) {
        super(t);
    }

    public InvokeCommandException(String message) {
        super(message, null);
    }

    public InvokeCommandException(String message, Throwable t) {
        super(message, t);
    }

}
