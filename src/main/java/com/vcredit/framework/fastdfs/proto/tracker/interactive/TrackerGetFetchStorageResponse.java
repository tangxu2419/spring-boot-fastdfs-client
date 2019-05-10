package com.vcredit.framework.fastdfs.proto.tracker.interactive;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.FdfsResponse;
import com.vcredit.framework.fastdfs.proto.StorageNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/917:52
 */
public class TrackerGetFetchStorageResponse extends FdfsResponse<StorageNode> {

    /**
     * 解析反馈内容
     *
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    @Override
    public StorageNode decodeContent(InputStream in, Charset charset) throws Exception {
        // 如果有内容
        if (getContentLength() > 0) {
            byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
            String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            String ip = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, Constants.FDFS_IPADDR_SIZE - 1, charset).trim();
            int port = (int) ProtoPackageUtil.buff2long(body, Constants.FDFS_GROUP_NAME_MAX_LEN + Constants.FDFS_IPADDR_SIZE - 1);
            return new StorageNode(groupName, ip, port);
        }
        return null;
    }
}
