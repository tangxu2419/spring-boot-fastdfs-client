package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.MetaInfo;
import com.vcredit.framework.fastdfs.proto.AbstractCommand;
import com.vcredit.framework.fastdfs.proto.FdfsResponse;
import com.vcredit.framework.fastdfs.proto.storage.interactive.StorageSetMetadataRequest;

import java.util.Set;

/**
 * 设置文件标签
 *
 * @author tangxu
 * @date 2019/5/1011:09
 */
public class StorageSetMetadataCommand extends AbstractCommand<Void> {


    public StorageSetMetadataCommand(String groupName, String fileName, Set<MetaInfo> metaDataSet, byte type) {
        this.request = new StorageSetMetadataRequest(groupName, fileName, metaDataSet, type);
        // 输出响应
        this.response = new FdfsResponse<>() {
            // default response
        };
    }
}
