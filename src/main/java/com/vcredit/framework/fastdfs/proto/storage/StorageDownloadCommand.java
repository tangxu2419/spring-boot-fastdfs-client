package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageDownloadRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageDownloadResponse;

/**
 * 文件下载指令
 * @author tangxu
 * @date 2019/5/918:10
 */
public class StorageDownloadCommand extends AbstractCommand<DownLoadResult> {

    public StorageDownloadCommand(String groupName, String path, long fileOffset, long downloadBytes) {
        super();
        this.request = new StorageDownloadRequest(groupName, path, fileOffset,downloadBytes);
        // 输出响应
        this.response = new StorageDownloadResponse();
    }
}
