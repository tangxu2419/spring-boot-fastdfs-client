package com.vcredit.framework.fastdfs.command;

/**
 * @author Dong Zhuming
 */
public class BaseOperationResult {

    private int errorCode;
    private String errorMessage;

    public BaseOperationResult() {
        this.errorCode = 0;
    }

    public BaseOperationResult(int errorCode) {
        this(errorCode, null);
    }

    public BaseOperationResult(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
