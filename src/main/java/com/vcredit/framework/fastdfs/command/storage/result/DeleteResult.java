package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author Dong Zhuming
 */
public class DeleteResult extends BaseOperationResult {

    public DeleteResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }

    public DeleteResult(int errorCode) {
        super(errorCode);
    }

    @Override
    public String toString() {
        return "DeleteResult{" +
                "errorCode=" + getErrorCode() +
                '}';
    }
}
