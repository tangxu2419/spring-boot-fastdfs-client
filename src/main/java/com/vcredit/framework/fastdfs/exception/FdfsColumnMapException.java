package com.vcredit.framework.fastdfs.exception;

/**
 * @author tangxu
 * @date 2019/5/816:51
 */
public class FdfsColumnMapException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    protected FdfsColumnMapException() {
    }

    protected FdfsColumnMapException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected FdfsColumnMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public FdfsColumnMapException(String message) {
        super(message);
    }

    protected FdfsColumnMapException(Throwable cause) {
        super(cause);
    }

    public FdfsColumnMapException(Exception e) {
        super(e);
    }
}
