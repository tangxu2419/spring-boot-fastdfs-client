package com.vcredit.framework.fastdfs;

/**
 * @author Dong Zhuming
 */
public class UploadResult extends OperationResult {

    private final String groupName;
    private final String fileName;

    public UploadResult(String groupName, String fileName) {
        this.groupName = groupName;
        this.fileName = fileName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getFileName() {
        return fileName;
    }
}
