package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.FdfsResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/918:13
 */
public class StorageDownloadResponse extends FdfsResponse<DownLoadResult> {

    /**
     * 解析反馈内容
     *
     * @param in
     * @param charset
     * @return
     */
    @Override
    public DownLoadResult decodeContent(InputStream in, Charset charset) throws IOException {
        // 如果有内容
        if (getContentLength() > 0) {
            byte[] body = new byte[(int) head.getContentLength()];
            int totalBytes = 0;
            int remainBytes = (int) head.getContentLength();
            int bytes;
            while (totalBytes < head.getContentLength()) {
                if ((bytes = in.read(body, totalBytes, remainBytes)) < 0) {
                    break;
                }
                totalBytes += bytes;
                remainBytes -= bytes;
            }
            if (totalBytes != head.getContentLength()) {
                throw new IOException("recv package size " + totalBytes + " != " + head.getContentLength());
            }
            return new DownLoadResult(body);
        }
        return new DownLoadResult(null);
    }
}
