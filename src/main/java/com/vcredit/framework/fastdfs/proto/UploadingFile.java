package com.vcredit.framework.fastdfs.proto;

import java.io.InputStream;
import java.util.Set;

/**
 * 文件上传对象
 *
 * @author Dong Zhuming
 */
public class UploadingFile {

    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件扩展名
     */
    private String fileExtName;
    /**
     * 文件元数据
     * TODO 评估Set<ImmutablePair<String, String>>
     */
    private Set<MetaInfo> metaData;

    /**
     * 上传文件分组
     */
    private String groupName;

    public UploadingFile(InputStream inputStream, long size, String fileExtName) {
        this.inputStream = inputStream;
        this.size = size;
        this.fileExtName = fileExtName;
    }


    public UploadingFile(InputStream inputStream, long size, String fileExtName, Set<MetaInfo> metaData) {
        this.inputStream = inputStream;
        this.size = size;
        this.fileExtName = fileExtName;
        this.metaData = metaData;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public long getSize() {
        return size;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public Set<MetaInfo> getMetaData() {
        return metaData;
    }

    public String getGroupName() {
        return groupName;
    }
}
