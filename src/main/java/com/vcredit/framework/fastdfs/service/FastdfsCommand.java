package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.StorageNode;

import java.io.InputStream;

/**
 * @author dongzhuming
 */
abstract class FastdfsCommand {
    private final StorageNode storageNode;

    FastdfsCommand(StorageNode storageNode) {
        this.storageNode = storageNode;
    }

    /**
     * 执行Fastdfs指令
     * @return 操作结果
     */
    OperationResult execute() {
        return new AbstractCommandInvoker(this).action();
    }

    static class Upload extends FastdfsCommand {

        private InputStream inputStream;
        private long fileSize;
        private String fileExtension;
        private MetaData metaInfo;

        private Upload(StorageNode storageNode) {
            super(storageNode);
        }

        static Upload create(StorageNode storageNode) {
            return new Upload(storageNode);
        }

        Upload inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        Upload fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        Upload fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        Upload metaInfo(MetaData metaInfo) {
            this.metaInfo = metaInfo;
            return this;
        }
    }

}
