package com.vcredit.framework.fastdfs.proto;

/**
 * @author Dong Zhuming
 */
public class DeleteResult extends OperationResult {


    public DeleteResult(int errorCode) {
        super(errorCode);
    }

    @Override
    public String toString() {
        return "DeleteResult{" +
                "errorCode=" + errorCode +
                '}';
    }
}
