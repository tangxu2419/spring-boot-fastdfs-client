package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.StorageNode;

import java.io.InputStream;

/**
 * @author dongzhuming
 */
public abstract class StorageCommand implements FastdfsCommand {

    private final StorageNode storageNode;

    StorageCommand(StorageNode storageNode) {
        this.storageNode = storageNode;
    }

    /**
     * 执行Fastdfs指令
     * @return 操作结果
     */
    @Override
    public OperationResult execute() {
        return AbstractCommandInvoker.prepare(this).action();
    }

    static class Upload extends StorageCommand {

        private InputStream inputStream;
        private long fileSize;
        private String fileExtension;
        private MetaData metaData;

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

        Upload metaData(MetaData metaData) {
            this.metaData = metaData;
            return this;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public long getFileSize() {
            return fileSize;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public MetaData getMetaData() {
            return metaData;
        }
    }

}
