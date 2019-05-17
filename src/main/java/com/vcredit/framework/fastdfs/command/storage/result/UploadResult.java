package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author Dong Zhuming
 */
public class UploadResult extends BaseOperationResult {

    private String groupName;
    private String fileName;

    public UploadResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }


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

    @Override
    public String toString() {
        return "UploadResult{" +
                "groupName='" + groupName + '\'' +
                ", fileName='" + fileName + '\'' +
                "} " + super.toString();
    }


}
