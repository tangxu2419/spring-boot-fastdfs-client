package com.vcredit.framework.fastdfs.service;

import com.vcredit.framework.fastdfs.MetaInfo;
import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.StorageLocation;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.exception.StorageException;
import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author Dong Zhuming
 */
public class StorageClient {
    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(StorageClient.class);

    private final StorageLocation storageLocation;

    public StorageClient(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Future<UploadResult> uploadFile(InputStream inputStream, long fileSize, String fileExtName, String extension, MetaInfo[] metaInfo) throws IOException {
        return uploadFile(null, inputStream, fileSize, fileExtName, extension, metaInfo);
    }

    public Future<UploadResult> uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName, String extension, MetaInfo[] metaInfo) throws IOException {
        byte cmd = ProtocolCommand.STORAGE_PROTO_CMD_UPLOAD_FILE;
        byte[] sizeBytes;
        byte[] hexLenBytes;
        long bodyLen;
        int offset;

        byte[] extNameBs;
        // 设置文件后缀名转byte数组，最多转6位
        extNameBs = new byte[Constants.FDFS_FILE_EXT_NAME_MAX_LEN];
        Arrays.fill(extNameBs, (byte) 0);
        if (fileExtName != null && fileExtName.length() > 0) {
            byte[] bs = fileExtName.getBytes(DEFAULT_CHARSET_NAME);
            int extNameLen = bs.length;
            if (extNameLen > Constants.FDFS_FILE_EXT_NAME_MAX_LEN) {
                extNameLen = Constants.FDFS_FILE_EXT_NAME_MAX_LEN;
            }
            System.arraycopy(bs, 0, extNameBs, 0, extNameLen);
        }

        sizeBytes = new byte[1 + Constants.FDFS_PROTO_PKG_LEN_SIZE];
        bodyLen = sizeBytes.length + Constants.FDFS_FILE_EXT_NAME_MAX_LEN + fileSize;
        sizeBytes[0] = (byte) storageLocation.getStorePathIndex();
        offset = 1;
        hexLenBytes = ProtoPackageUtil.long2buff(fileSize);
        System.arraycopy(hexLenBytes, 0, sizeBytes, offset, hexLenBytes.length);

        byte[] header = ProtoPackageUtil.packHeader(cmd, bodyLen, (byte) 0);
        byte[] wholePkg = new byte[(int) (header.length + bodyLen - fileSize)];
        System.arraycopy(header, 0, wholePkg, 0, header.length);
        System.arraycopy(sizeBytes, 0, wholePkg, header.length, sizeBytes.length);
        offset = header.length + sizeBytes.length;

        System.arraycopy(extNameBs, 0, wholePkg, offset, extNameBs.length);

        OutputStream out = storageLocation.getSocket().getOutputStream();
        out.write(wholePkg);

        //写入数据流
        if (null != inputStream) {
            sendFileContent(inputStream, fileSize, out);
        }

        ProtoPackageUtil.RecvPackageInfo pkgInfo = ProtoPackageUtil.recvPackage(storageLocation.getSocket().getInputStream(), ProtocolCommand.STORAGE_PROTO_CMD_RESP, -1);
        if (pkgInfo.errno != 0) {
            return null;
        }
        if (pkgInfo.body.length <= Constants.FDFS_GROUP_NAME_MAX_LEN) {
            throw new IOException("body length: " + pkgInfo.body.length + " <= " + Constants.FDFS_GROUP_NAME_MAX_LEN);
        }

        String fileGroupName = new String(pkgInfo.body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN).trim();
        String filename = new String(pkgInfo.body, Constants.FDFS_GROUP_NAME_MAX_LEN, pkgInfo.body.length - Constants.FDFS_GROUP_NAME_MAX_LEN);

        UploadResult result = new UploadResult(fileGroupName, filename);
        CompletableFuture<UploadResult> future = new CompletableFuture<>();
        future.complete(result);
        return future;
    }

    public Future<DeleteResult> deleteFile(String groupName, String filename) throws IOException {
        byte cmd = ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE;
        byte[] header;
        byte[] groupBytes;
        byte[] filenameBytes;
        byte[] bs;
        int groupLen;

        groupBytes = new byte[Constants.FDFS_GROUP_NAME_MAX_LEN];
        bs = groupName.getBytes(DEFAULT_CHARSET_NAME);
        filenameBytes = filename.getBytes(DEFAULT_CHARSET_NAME);

        Arrays.fill(groupBytes, (byte) 0);
        if (bs.length <= groupBytes.length) {
            groupLen = bs.length;
        } else {
            groupLen = groupBytes.length;
        }
        System.arraycopy(bs, 0, groupBytes, 0, groupLen);

        header = ProtoPackageUtil.packHeader(cmd, groupBytes.length + filenameBytes.length, (byte) 0);
        byte[] wholePkg = new byte[header.length + groupBytes.length + filenameBytes.length];
        System.arraycopy(header, 0, wholePkg, 0, header.length);
        System.arraycopy(groupBytes, 0, wholePkg, header.length, groupBytes.length);
        System.arraycopy(filenameBytes, 0, wholePkg, header.length + groupBytes.length, filenameBytes.length);

        OutputStream out = storageLocation.getSocket().getOutputStream();
        out.write(wholePkg);

        ProtoPackageUtil.RecvPackageInfo pkgInfo = ProtoPackageUtil.recvPackage(storageLocation.getSocket().getInputStream(), ProtocolCommand.STORAGE_PROTO_CMD_RESP, 0);

        DeleteResult result = new DeleteResult(pkgInfo.errno);
        CompletableFuture<DeleteResult> future = new CompletableFuture<>();
        future.complete(result);
        return future;
    }

    public OutputStream download(String groupName, String fileName) throws Exception {
        byte[] header;
        byte[] bsOffset;
        byte[] bsDownBytes;
        byte[] groupBytes;
        byte[] filenameBytes;
        byte[] bs;
        int groupLen;

        bsOffset = ProtoPackageUtil.long2buff(0);
        bsDownBytes = ProtoPackageUtil.long2buff(0);
        groupBytes = new byte[Constants.FDFS_GROUP_NAME_MAX_LEN];
        bs = groupName.getBytes(DEFAULT_CHARSET_NAME);
        filenameBytes = fileName.getBytes(DEFAULT_CHARSET_NAME);

        Arrays.fill(groupBytes, (byte) 0);
        if (bs.length <= groupBytes.length) {
            groupLen = bs.length;
        } else {
            groupLen = groupBytes.length;
        }
        System.arraycopy(bs, 0, groupBytes, 0, groupLen);

        header = ProtoPackageUtil.packHeader(ProtocolCommand.STORAGE_PROTO_CMD_DOWNLOAD_FILE,
                bsOffset.length + bsDownBytes.length + groupBytes.length + filenameBytes.length, (byte) 0);
        byte[] wholePkg = new byte[header.length + bsOffset.length + bsDownBytes.length + groupBytes.length + filenameBytes.length];
        System.arraycopy(header, 0, wholePkg, 0, header.length);
        System.arraycopy(bsOffset, 0, wholePkg, header.length, bsOffset.length);
        System.arraycopy(bsDownBytes, 0, wholePkg, header.length + bsOffset.length, bsDownBytes.length);
        System.arraycopy(groupBytes, 0, wholePkg, header.length + bsOffset.length + bsDownBytes.length, groupBytes.length);
        System.arraycopy(filenameBytes, 0, wholePkg, header.length + bsOffset.length + bsDownBytes.length + groupBytes.length, filenameBytes.length);

        Socket socket = storageLocation.getSocket();
        OutputStream out = socket.getOutputStream();
        out.write(wholePkg);

        ProtoPackageUtil.RecvHeaderInfo headerInfo = ProtoPackageUtil.recvHeader(socket.getInputStream(), ProtocolCommand.STORAGE_PROTO_CMD_RESP, -1);
        if (headerInfo.errno != 0) {
            log.warn("download file error. errorCode : {}", headerInfo.errno);
            throw new StorageException("download file error. errorCode : {}" + headerInfo.errno);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[256 * 1024];
        long remainBytes = headerInfo.bodyLen;
        int bytes;
        while (remainBytes > 0) {
            if ((bytes = socket.getInputStream().read(buff, 0, remainBytes > buff.length ? buff.length : (int) remainBytes)) < 0) {
                throw new IOException("recv package size " + (headerInfo.bodyLen - remainBytes) + " != " + headerInfo.bodyLen);
            }
            outputStream.write(buff, 0, bytes);
            remainBytes -= bytes;
        }
        return outputStream;
    }


    /**
     * 发送文件
     *
     * @param ins
     * @param size
     * @param ous
     * @throws IOException
     */
    protected void sendFileContent(InputStream ins, long size, OutputStream ous) throws IOException {
        log.debug("开始上传文件流大小为{}", size);
        long remainBytes = size;
        byte[] buff = new byte[256 * 1024];
        int bytes;
        while (remainBytes > 0) {
            if ((bytes = ins.read(buff, 0, remainBytes > buff.length ? buff.length : (int) remainBytes)) < 0) {
                throw new IOException("the end of the stream has been reached. not match the expected size ");
            }
            ous.write(buff, 0, bytes);
            remainBytes -= bytes;
            log.debug("剩余数据量{}", remainBytes);
        }
    }

}
