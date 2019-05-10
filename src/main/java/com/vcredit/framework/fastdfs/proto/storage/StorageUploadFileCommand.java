package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageUploadFileResponse;

import java.io.InputStream;

/**
 * @author tangxu
 * @date 2019/5/914:52
 */
public class StorageUploadFileCommand extends AbstractCommand<UploadResult> {

    public StorageUploadFileCommand(byte storeIndex, InputStream inputStream, String fileExtName, long fileSize) {
        super();
        this.request = new StorageUploadFileRequest(storeIndex, inputStream, fileExtName, fileSize);
        // 输出响应
        this.response = new StorageUploadFileResponse();
    }
}
