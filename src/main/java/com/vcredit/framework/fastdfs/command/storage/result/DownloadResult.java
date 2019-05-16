package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author tangxu
 * @date 2019/5/918:03
 */
public class DownloadResult extends BaseOperationResult {

    private byte[] fileBytes;

    public DownloadResult(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public DownloadResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }
}
