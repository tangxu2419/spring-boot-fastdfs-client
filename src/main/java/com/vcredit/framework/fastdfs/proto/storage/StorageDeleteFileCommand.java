package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageDeleteFileRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageDeleteFileResponse;

/**
 * 文件删除指令
 * @author tangxu
 * @date 2019/5/917:33
 */
public class StorageDeleteFileCommand extends AbstractCommand<DeleteResult> {

    public StorageDeleteFileCommand(String groupName, String path) {
        super();
        this.request = new StorageDeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new StorageDeleteFileResponse();
    }
}
