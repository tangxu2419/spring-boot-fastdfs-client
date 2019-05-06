package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * @author Dong Zhuming
 */
public class StorageClient {

    private final StorageLocation storageLocation;

    public StorageClient(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Future<UploadResult> uploadFile(InputStream inputStream, String extension, MetaInfo[] metaInfo) throws IOException {
        return uploadFile(null, inputStream, extension, metaInfo);
    }

    public Future<UploadResult> uploadFile(String groupName, InputStream inputStream, String extension, MetaInfo[] metaInfo) throws IOException {
        return uploadFile(null, inputStream, null, 0, extension, metaInfo);
    }


    public Future<UploadResult> uploadFile(String groupName, InputStream inputStream, String fileExtName, long fileSize, String extension, MetaInfo[] metaInfo) throws IOException {
        boolean isAppenderFile = StringUtils.isBlank(fileExtName);
        byte cmd;
        if( isAppenderFile ){
            cmd = ProtocolCommand.STORAGE_PROTO_CMD_UPLOAD_APPENDER_FILE;
        }else{
            cmd = ProtocolCommand.STORAGE_PROTO_CMD_UPLOAD_FILE;
        }
        //TODO
        return null;
    }
}
