package com.vcredit.framework.fastdfs.exception;

/**
 * @author tangxu
 * @date 2019/5/711:08
 */
public class FdfsConnectException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public FdfsConnectException(String message, Throwable t) {
        super(message, t);
    }
}
