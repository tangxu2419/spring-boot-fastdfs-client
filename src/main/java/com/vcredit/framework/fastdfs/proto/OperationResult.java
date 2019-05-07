package com.vcredit.framework.fastdfs.proto;

/**
 * @author Dong Zhuming
 */
public abstract class OperationResult {

    protected int errorCode;

    public OperationResult() {
    }

    public OperationResult(int errorCode) {
        this.errorCode = errorCode;
    }


}
