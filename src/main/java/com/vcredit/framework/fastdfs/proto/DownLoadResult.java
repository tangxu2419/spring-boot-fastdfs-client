package com.vcredit.framework.fastdfs.proto;

/**
 * @author tangxu
 * @date 2019/5/918:03
 */
public class DownloadResult extends OperationResult {

    private byte[] fileBytes;

    public DownloadResult(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }
}
