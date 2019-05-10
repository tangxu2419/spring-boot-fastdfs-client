package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsResponse;
import com.vcredit.framework.fastdfs.proto.UploadResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/915:00
 */
public class StorageUploadFileResponse extends AbstractFdfsResponse<UploadResult> {

    /**
     * 解析反馈内容
     *
     * @param in
     * @param charset
     * @return
     */
    @Override
    public UploadResult decodeContent(InputStream in, Charset charset) throws IOException {
        // 如果有内容
        if (getContentLength() > 0) {
            byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
            String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            String filename = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, body.length - Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            return new UploadResult(groupName, filename);
        }
        return null;
    }
}
