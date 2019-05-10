package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsResponse;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/917:38
 */
public class StorageDeleteFileResponse extends AbstractFdfsResponse<DeleteResult> {
    /**
     * 解析反馈内容
     *
     * @param in
     * @param charset
     * @return
     */
    @Override
    public DeleteResult decodeContent(InputStream in, Charset charset) {
        //TODO 描述
        return new DeleteResult(head.getStatus());
    }
}
