package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

import java.util.Arrays;

/**
 * @author tangxu
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

    @Override
    public String toString() {
        return "DownloadResult{" +
                "fileBytes=" + Arrays.toString(fileBytes) +
                "} " + super.toString();
    }
}
