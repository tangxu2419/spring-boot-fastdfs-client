package com.vcredit.framework.fastdfs.proto.tracker.interactive;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsResponse;
import com.vcredit.framework.fastdfs.proto.StorageNode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.vcredit.framework.fastdfs.constants.Constants.*;

/**
 *  获取Storage节点响应
 * @author tangxu
 * @date 2019/5/818:04
 */
public class TrackerGetStorageResponse extends AbstractFdfsResponse<StorageNode> {

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
            String groupName = new String(body, 0, FDFS_GROUP_NAME_MAX_LEN, charset).trim();
            String ip = new String(body, FDFS_GROUP_NAME_MAX_LEN, FDFS_IPADDR_SIZE - 1, charset).trim();
            int port = (int) ProtoPackageUtil.buff2long(body, FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1);
            byte storePath = body[TRACKER_QUERY_STORAGE_STORE_BODY_LEN - 1];
            return new StorageNode(groupName, ip, port, storePath);
        }
        return null;
    }
}
