package com.vcredit.framework.fastdfs.proto;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.util.Map;

/**
 * 文件上传对象
 * @author Dong Zhuming
 */
@Data
@Builder
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
    private String extension;
    /**
     * 文件元数据
     * TODO 评估Set<ImmutablePair<String, String>>
     */
    private Map<String, String> metaData;

    /**
     * 上传文件分组
     */
    private String groupName;
}
