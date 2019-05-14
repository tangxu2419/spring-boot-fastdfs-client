package com.vcredit.framework.fastdfs.refine.storage;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.StorageNode;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsCommand;
import com.vcredit.framework.fastdfs.refine.MetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author dongzhuming
 */
public abstract class StorageCommand implements FastdfsCommand {


    private final StorageNode storageNode;

    StorageCommand(StorageNode storageNode) {
        this.storageNode = storageNode;
    }

    public StorageNode getStorageNode() {
        return storageNode;
    }


    /**
     * 执行Fastdfs指令
     *
     * @return 操作结果
     */
    @Override
    public OperationResult execute() {
        return AbstractCommandInvoker.prepare(this).action();
    }

    public static class Upload extends StorageCommand {

        private InputStream inputStream;
        private long fileSize;
        private String fileExtension;
        private MetaData metaData;

        private Upload(StorageNode storageNode) {
            super(storageNode);
        }


        public static Upload create(StorageNode storageNode) {
            return new Upload(storageNode);
        }

        public Upload inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Upload fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Upload fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public Upload metaData(MetaData metaData) {
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
