package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import static com.vcredit.framework.fastdfs.constants.Constants.*;
import static com.vcredit.framework.fastdfs.constants.ProtocolCommand.TRACKER_PROTO_CMD_RESP;

/**
 * @author Dong Zhuming
 */
public class TrackerClient {

    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(TrackerClient.class);

    private final TrackerConnectionPool trackerConnectionPool;

    public TrackerClient(TrackerConnectionPool trackerConnectionPool) {
        this.trackerConnectionPool = trackerConnectionPool;
    }

    /**
     *
     * @param groupName
     * @return
     * @throws IOException
     */
    public StorageLocation getStorageLocation(String groupName) throws IOException {
        final TrackerConnection trackerConnection = trackerConnectionPool.borrow();
        Socket trackerSocket = trackerConnection.getSocket();
        StorageLocation storageLocation;
        try (OutputStream out = trackerSocket.getOutputStream()) {
            // write header to socket
            byte cmd;
            int outLen;
            if (StringUtils.isBlank(groupName)) {
                cmd = ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE;
                outLen = 0;
            } else {
                cmd = ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE;
                outLen = Constants.FDFS_GROUP_NAME_MAX_LEN;
            }
            byte[] header = ProtoPackageResult.packHeader(cmd, outLen, (byte) 0);
            out.write(header);
            // write groupName to socket
            if (StringUtils.isNotBlank(groupName)) {
                byte[] bGroupName = new byte[Constants.FDFS_GROUP_NAME_MAX_LEN];
                byte[] bs = groupName.getBytes(DEFAULT_CHARSET_NAME);
                int groupLen;
                if (bs.length <= FDFS_GROUP_NAME_MAX_LEN) {
                    groupLen = bs.length;
                } else {
                    groupLen = FDFS_GROUP_NAME_MAX_LEN;
                }
                Arrays.fill(bGroupName, (byte) 0);
                System.arraycopy(bs, 0, bGroupName, 0, groupLen);
                out.write(bGroupName);
            }
            // read package from socket
            ProtoPackageResult.RecvPackageInfo pkgInfo = ProtoPackageResult.recvPackage(trackerSocket.getInputStream(), TRACKER_PROTO_CMD_RESP, TRACKER_QUERY_STORAGE_STORE_BODY_LEN);
            // check error code and throw
            if (pkgInfo.errno != 0) {
                log.warn("getStoreStorage fail, errno code: " + pkgInfo.errno);
                return null;
            }
            // parse ip address, port, storePath from package
            String ip = new String(pkgInfo.body, FDFS_GROUP_NAME_MAX_LEN, FDFS_IPADDR_SIZE - 1).trim();
            int port = (int) ProtoPackageResult.buff2long(pkgInfo.body, FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1);
            int storePath = pkgInfo.body[TRACKER_QUERY_STORAGE_STORE_BODY_LEN - 1];
            // create storage connection and return
            storageLocation = new StorageLocation(ip, port, storePath);
        }
        trackerConnectionPool.release(trackerConnection);
        log.debug(storageLocation.toString());
        return storageLocation;
    }

    /**
     *
     * @param groupName
     * @param fileExtName
     * @return
     * @throws IOException
     */
    private StorageLocation[] getStorageLocations(String groupName, String fileExtName) throws IOException {
        final TrackerConnection trackerConnection = trackerConnectionPool.borrow();
        Socket trackerSocket = trackerConnection.getSocket();
        StorageLocation[] storageLocations;
        try (OutputStream out = trackerSocket.getOutputStream()) {
            // 上传支持断点续传的文件
            byte cmd= ProtocolCommand.STORAGE_PROTO_CMD_UPLOAD_FILE;
            // 继续上载文件
//            byte cmd= ProtocolCommand.STORAGE_PROTO_CMD_APPEND_FILE;


            // 报文三部分
            // 报文头
            // 报文参数
            // 文件流



        }
        trackerConnectionPool.release(trackerConnection);
        return storageLocations;
    }

    private StorageLocation[] getStorageLocations(String groupName) throws IOException {

        return null;
    }


}
