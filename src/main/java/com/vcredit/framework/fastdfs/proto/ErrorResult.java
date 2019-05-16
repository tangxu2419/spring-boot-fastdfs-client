package com.vcredit.framework.fastdfs.proto;

/**
 * @author tangxu
 * @date 2019/5/1615:04
 */
public class ErrorResult extends OperationResult {

    private String message;

    public ErrorResult(String message) {
        this.message = message;
    }

    public ErrorResult(int errorCode, String message) {
        super(errorCode);
        this.message = message;
    }
}
