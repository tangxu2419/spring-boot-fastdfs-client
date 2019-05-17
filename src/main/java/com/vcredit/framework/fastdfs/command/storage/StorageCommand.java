package com.vcredit.framework.fastdfs.command.storage;

import com.vcredit.framework.fastdfs.command.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.FastdfsCommand;
import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

import java.io.InputStream;

/**
 * Storage指令
 *
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


    @Override
    public BaseOperationResult execute() throws InvokeCommandException {
        return AbstractCommandInvoker.prepare(this).action();
    }

    /**
     * 指令-上传文件
     */
    public static class Upload extends StorageCommand {
        private InputStream inputStream;
        private long fileSize;
        private String fileExtension;

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


        public InputStream getInputStream() {
            return inputStream;
        }

        public long getFileSize() {
            return fileSize;
        }

        public String getFileExtension() {
            return fileExtension;
        }

    }


    /**
     * 指令-设置metaData
     */
    public static class SetMeta extends StorageCommand {

        /**
         * 操作标记（重写/覆盖）
         */
        private byte opFlag;
        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;

        /**
         * 元数据
         */
        private MetaData metaData;

        SetMeta(StorageNode storageNode) {
            super(storageNode);
        }

        public static SetMeta create(StorageNode storageNode) {
            return new SetMeta(storageNode);
        }

        public SetMeta opFlag(byte opFlag) {
            this.opFlag = opFlag;
            return this;
        }

        public SetMeta groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public SetMeta fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SetMeta metaData(MetaData metaData) {
            this.metaData = metaData;
            return this;
        }

        public MetaData getMetaData() {
            return metaData;
        }

        public byte getOpFlag() {
            return opFlag;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * 获取metaData
     */
    public static class GetMeta extends StorageCommand {
        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;

        GetMeta(StorageNode storageNode) {
            super(storageNode);
        }

        public static GetMeta create(StorageNode storageNode) {
            return new GetMeta(storageNode);
        }


        public GetMeta groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public GetMeta fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * 下载
     */
    public static class Download extends StorageCommand {

        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;
        /**
         * 开始位置
         */
        private long fileOffset = 0;
        /**
         * 读取文件长度
         */
        private long downloadBytes = 0;

        Download(StorageNode storageNode) {
            super(storageNode);
        }

        public static Download create(StorageNode storageNode) {
            return new Download(storageNode);
        }


        public Download groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Download fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Download fileOffset(long fileOffset) {
            this.fileOffset = fileOffset;
            return this;
        }

        public Download downloadBytes(long downloadBytes) {
            this.downloadBytes = downloadBytes;
            return this;
        }


        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }

        public long getFileOffset() {
            return fileOffset;
        }

        public long getDownloadBytes() {
            return downloadBytes;
        }
    }

    /**
     * 删除
     */
    public static class Delete extends StorageCommand {

        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;

        Delete(StorageNode storageNode) {
            super(storageNode);
        }

        public static Delete create(StorageNode storageNode) {
            return new Delete(storageNode);
        }


        public Delete groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Delete fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * 文件信息
     */
    public static class FileInfo extends StorageCommand {

        FileInfo(StorageNode storageNode) {
            super(storageNode);
        }

        public static FileInfo create(StorageNode storageNode) {
            return new FileInfo(storageNode);
        }
    }

    /**
     * 上传从文件
     */
    public static class SlaveFile extends StorageCommand {
        SlaveFile(StorageNode storageNode) {
            super(storageNode);
        }

        public static SlaveFile create(StorageNode storageNode) {
            return new SlaveFile(storageNode);
        }
    }


}
