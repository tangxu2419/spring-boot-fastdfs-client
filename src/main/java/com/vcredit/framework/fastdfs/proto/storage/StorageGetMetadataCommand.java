package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.proto.MetaInfo;
import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageGetMetadataRequest;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageGetMetadataResponse;

import java.util.Set;

/**
 * 获取文件标签
 *
 * @author tangxu
 * @date 2019/5/1011:43
 */
public class StorageGetMetadataCommand extends AbstractCommand<Set<MetaInfo>> {

    public StorageGetMetadataCommand(String groupName, String filename) {
        this.request = new StorageGetMetadataRequest(groupName, filename);
        // 输出响应
        this.response = new StorageGetMetadataResponse();
    }
}
